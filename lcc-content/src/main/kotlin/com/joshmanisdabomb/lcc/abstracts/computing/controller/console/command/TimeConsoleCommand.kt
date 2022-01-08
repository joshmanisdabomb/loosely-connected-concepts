package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import net.minecraft.text.TranslatableText

class TimeConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .executes {
            val time = it.source.context.getWorldFromContext().timeOfDay
            it.source.controller.write(it.source.session, TranslatableText("terminal.lcc.console.$name", time.div(24000), *formatTime(time)), it.source.view)
            1
        }

    private fun formatTime(time: Long): Array<String> {
        val timeInDay = time % 24000
        val hours = timeInDay.div(1000).plus(6)
        val minutes = timeInDay.rem(1000).times(60).div(1000)
        return arrayOf(String.format("%02d", hours), String.format("%02d", minutes))
    }

}