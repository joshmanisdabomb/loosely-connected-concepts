package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder

object LCCConsoleCommands : BasicDirectory<LiteralArgumentBuilder<ConsoleCommandSource>, Array<String>>() {

    val dispatcher = CommandDispatcher<ConsoleCommandSource>()

    private val _aliases = mutableMapOf<String, String>()
    val aliases by lazy { _aliases.toMap() }

    val clear by entry(::initialiser) { ClearConsoleCommand(name).command }
        .setProperties(arrayOf("cls", "clr", "clean", "wipe"))
    val echo by entry(::initialiser) { EchoConsoleCommand(name, ConsoleCommandSource::view).command }
        .setProperties(arrayOf("out", "output", "print", "write", "println", "writeln"))
    val shout by entry(::initialiser) { EchoConsoleCommand(name) { null }.command }
        .setProperties(arrayOf("say", "speak", "announce", "toall", "everyone", "loud").plus(arrayOf("echoa", "outa", "outputa", "printa", "writea", "printlna", "writelna").flatMap { listOf(it, it.plus("ll")) }))
    val help by entry(::initialiser) { HelpConsoleCommand(name).command }
        .setProperties(arrayOf("?", "man", "manual", "usage", "describe", "explain", "how", "howto"))
    val time by entry(::initialiser) { TimeConsoleCommand(name).command }
        .setProperties(arrayOf("day", "days", "hour", "hours", "minute", "minutes", "clock", "ntp"))
    val install by entry(::initialiser) { InstallConsoleCommand(name).command }
        .setProperties(arrayOf("image", "setup", "os", "console", "consoleos"))
    val label by entry(::initialiser) { LabelConsoleCommand(name).command }
        .setProperties(arrayOf("labelpart", "rnpart", "mvpart", "renamepart", "movepart", "lp"))
    val reboot by entry(::initialiser) { PowerConsoleCommand(name, ComputingSessionExecuteContext::reboot).command }
        .setProperties(arrayOf("restart", "reset", "powercycle"))
    val shutdown by entry(::initialiser) { PowerConsoleCommand(name, ComputingSessionExecuteContext::shutdown).command }
        .setProperties(arrayOf("end", "exit", "close", "off", "poweroff"))

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
                _aliases[alias] = it.entry.literal
            }
        }
    }

    fun literal(name: String) = LiteralArgumentBuilder.literal<ConsoleCommandSource>(name)

    fun <T> required(name: String, type: ArgumentType<T>) = RequiredArgumentBuilder.argument<ConsoleCommandSource, T>(name, type)

}