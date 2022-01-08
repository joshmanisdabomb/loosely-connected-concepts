package com.joshmanisdabomb.lcc.abstracts.computing.controller.console

class ClearConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .executes {
            clear(it.source)
        }

    fun clear(source: ConsoleCommandSource): Int {
        source.controller.clear(source.session, source.view)
        return 1
    }

}