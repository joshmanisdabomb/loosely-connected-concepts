package com.joshmanisdabomb.lcc.abstracts.computing.controller.console

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.LCCConsoleCommands.dispatcher
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.CommandSource
import java.util.*

object LCCConsoleCommands : BasicDirectory<LiteralArgumentBuilder<ConsoleCommandSource>, Array<String>>() {

    val dispatcher = CommandDispatcher<ConsoleCommandSource>()

    val clear by entry(::initialiser) { ClearConsoleCommand(name).command }
        .setProperties(arrayOf("cls", "clr", "clean"))
    val echo by entry(::initialiser) { EchoConsoleCommand(name, ConsoleCommandSource::view).command }
        .setProperties(arrayOf("out", "output", "print", "write", "println", "writeln"))
    val shout by entry(::initialiser) { EchoConsoleCommand(name) { null }.command }
        .setProperties(arrayOf("say", "speak", "announce", "toall", "everyone", "loud").plus(arrayOf("echoa", "outa", "outputa", "printa", "writea", "printlna", "writelna").flatMap { listOf(it, it.plus("ll")) }))
    val help by entry(::initialiser) { HelpConsoleCommand(name).command }
        .setProperties(arrayOf("?", "man", "manual", "usage", "describe", "explain", "how", "howto"))

    fun initialiser(input: LiteralArgumentBuilder<ConsoleCommandSource>, context: DirectoryContext<Array<String>>, parameters: Unit) = input

    override fun defaultProperties(name: String) = emptyArray<String>()

    override fun afterInitAll(initialised: List<DirectoryEntry<out LiteralArgumentBuilder<ConsoleCommandSource>, out LiteralArgumentBuilder<ConsoleCommandSource>>>, filter: (context: DirectoryContext<Array<String>>) -> Boolean) {
        initialised.forEach {
            val destination = dispatcher.register(it.entry)
            for (alias in it.properties) {
                val redirect = literal(alias)
                    .requires(destination.requirement)
                    .forward(destination.redirect, destination.redirectModifier, destination.isFork)
                    .executes(destination.command)
                for (child in destination.children) redirect.then(child)
                dispatcher.register(redirect)
            }
        }
    }

    fun literal(name: String) = LiteralArgumentBuilder.literal<ConsoleCommandSource>(name)

    fun <T> required(name: String, type: ArgumentType<T>) = RequiredArgumentBuilder.argument<ConsoleCommandSource, T>(name, type)

}