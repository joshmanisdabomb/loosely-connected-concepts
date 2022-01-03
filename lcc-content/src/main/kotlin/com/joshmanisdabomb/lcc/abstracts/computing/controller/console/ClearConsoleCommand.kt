package com.joshmanisdabomb.lcc.abstracts.computing.controller.console

import com.mojang.brigadier.builder.LiteralArgumentBuilder

class ClearConsoleCommand(val name: String) {

    val command = LiteralArgumentBuilder.literal<ConsoleCommandSource>(name)
        .executes {
            clear(it.source)
        }

    fun clear(source: ConsoleCommandSource): Int {
        source.controller.clear(source.session, source.view)
        return 1
    }

}