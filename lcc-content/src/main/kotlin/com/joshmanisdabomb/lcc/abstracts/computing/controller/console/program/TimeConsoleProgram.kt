package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text

class TimeConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            startTask(it.source)
        }

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        val time = source.context.getWorldFromContext().timeOfDay
        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name", time.div(24000), *formatTime(time)), source.view)
        return null
    }

    private fun formatTime(time: Long): Array<String> {
        val timeInDay = time % 24000
        val hours = timeInDay.div(1000).plus(6)
        val minutes = timeInDay.rem(1000).times(60).div(1000)
        return arrayOf(String.format("%02d", hours), String.format("%02d", minutes))
    }

}