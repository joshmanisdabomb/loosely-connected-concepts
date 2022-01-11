package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import net.minecraft.nbt.NbtCompound

class PowerConsoleProgram(literal: String, val func: ComputingSessionExecuteContext.() -> Unit, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            startTask(it.source)
        }

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        source.context.func()
        return null
    }

}