package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource

class ClearConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .executes {
            it.source.controller.clear(it.source.session, it.source.view)
            1
        }

}