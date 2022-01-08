package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import net.minecraft.text.TranslatableText

class PowerConsoleCommand(val name: String, val func: ComputingSessionExecuteContext.() -> Unit) {

    val command = LCCConsoleCommands.literal(name)
        .executes {
            it.source.context.func()
            1
        }

}