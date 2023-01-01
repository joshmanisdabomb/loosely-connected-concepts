package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.ConsoleComputingController
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.modifyCompoundList
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity

abstract class ConsoleProgram {

    abstract val command: LiteralArgumentBuilder<ConsoleCommandSource>?
    abstract val aliases: Array<out String>?

    val id get() = LCCRegistries.computer_console_programs.getKey(this).orElseThrow(::RuntimeException).value
    val name get() = command?.literal

    fun startTask(source: ConsoleCommandSource, data: NbtCompound = NbtCompound()): Int {
        val vdata = source.session.getViewData(source.view)
        vdata.modifyCompoundList("Tasks") {
            val nbt = NbtCompound()
            nbt.putString("Program", id.toString())
            nbt.put("Data", data)
            nbt.putUuid("Player", source.player.uuid)
            this.add(nbt)
            null
        }
        return 1
    }

    abstract fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean?

    open fun keyPressed(session: ComputingSession, data: NbtCompound, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int): Boolean = false

    @Environment(EnvType.CLIENT)
    open fun render(controller: ConsoleComputingController, session: ComputingSession, data: NbtCompound, view: ComputingSessionViewContext, matrices: MatrixStack, delta: Float, x: Int, y: Int): Boolean? = null

}