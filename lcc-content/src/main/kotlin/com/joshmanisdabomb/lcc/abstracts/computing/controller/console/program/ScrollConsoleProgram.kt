package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.ConsoleComputingController
import com.joshmanisdabomb.lcc.abstracts.computing.controller.LinedComputingController.Companion.style
import com.joshmanisdabomb.lcc.abstracts.computing.controller.LinedComputingController.Companion.total_columns
import com.joshmanisdabomb.lcc.abstracts.computing.controller.LinedComputingController.Companion.total_rows
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSession
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionViewContext
import com.joshmanisdabomb.lcc.extensions.getCompoundList
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import org.lwjgl.glfw.GLFW

class ScrollConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            startTask(it.source)
        }

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        if (data.getBoolean("Quit")) return null
        return true
    }

    override fun keyPressed(session: ComputingSession, data: NbtCompound, player: ServerPlayerEntity, view: ComputingSessionViewContext, keyCode: Int): Boolean {
        val scroll = data.getInt("Scroll")
        return when (keyCode) {
            GLFW.GLFW_KEY_UP -> {
                data.putInt("Scroll", scroll.plus(1))
                true
            }
            GLFW.GLFW_KEY_DOWN -> {
                data.putInt("Scroll", scroll.minus(1).coerceAtLeast(0))
                true
            }
            GLFW.GLFW_KEY_Q -> {
                data.putBoolean("Quit", true)
                true
            }
            else -> false
        }
    }

    @Environment(EnvType.CLIENT)
    override fun render(controller: ConsoleComputingController, session: ComputingSession, data: NbtCompound, view: ComputingSessionViewContext, matrices: MatrixStack, delta: Float, x: Int, y: Int): Boolean? {
        val scroll = data.getInt("Scroll")
        val vdata = session.getViewData(view.getViewToken()!!)

        val output = vdata.getCompoundList("Output")
        val lines = controller.formatOutput(output)
        val limit = lines.count().minus(total_rows-1).coerceAtLeast(0)
        controller.renderLines(controller.limitLines(lines, total_rows-1, scroll), matrices, x, y, 0)

        controller.renderLine(matrices, x, y, total_rows-1, Text.translatable("terminal.lcc.console.scroll.help", Text.literal("↑").formatted(Formatting.WHITE), Text.literal("↓").formatted(Formatting.WHITE), Text.literal("Q").formatted(Formatting.WHITE)).setStyle(style).formatted(Formatting.GREEN))
        controller.renderLine(matrices, x, y, total_rows-1, Text.translatable("terminal.lcc.console.scroll.lines", Text.literal(scroll.toString()).formatted(if (scroll > limit) Formatting.RED else Formatting.RESET), limit.toString()).setStyle(style), tx = total_columns - scroll.toString().length - limit.toString().length - 1)
        return true
    }

}
