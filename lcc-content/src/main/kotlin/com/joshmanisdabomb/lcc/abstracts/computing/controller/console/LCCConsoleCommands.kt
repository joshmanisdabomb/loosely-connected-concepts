package com.joshmanisdabomb.lcc.abstracts.computing.controller.console

import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder

object LCCConsoleCommands : BasicDirectory<LiteralArgumentBuilder<ConsoleCommandSource>, Unit>() {

    val dispatcher = CommandDispatcher<ConsoleCommandSource>()

    val clear by entry(::initialiser) { ClearConsoleCommand(name).command }

    fun initialiser(input: LiteralArgumentBuilder<ConsoleCommandSource>, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out LiteralArgumentBuilder<ConsoleCommandSource>, out LiteralArgumentBuilder<ConsoleCommandSource>>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach {
            dispatcher.register(it.entry)
        }
    }

}