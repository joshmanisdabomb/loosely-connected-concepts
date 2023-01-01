package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.directory.RegistryDirectory
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder

object LCCConsolePrograms : BasicDirectory<ConsoleProgram, Unit>(), RegistryDirectory<ConsoleProgram, Unit, Unit> {

    override val registry = LCCRegistries.computer_console_programs

    override fun regId(name: String) = LCC.id(name)

    val dispatcher = CommandDispatcher<ConsoleCommandSource>()

    private val _aliases = mutableMapOf<String, String>()
    val aliases by lazy { _aliases.toMap() }

    val clear by entry(::initialiser) { ClearConsoleProgram(name, "cls", "clr", "clean", "wipe") }
    val echo by entry(::initialiser) { EchoConsoleProgram(name, ConsoleCommandSource::view, "out", "output", "print", "write", "println", "writeln") }
    val shout by entry(::initialiser) { EchoConsoleProgram(name, { null }, "say", "speak", "announce", "toall", "everyone", "loud", *arrayOf("echoa", "outa", "outputa", "printa", "writea", "printlna", "writelna").flatMap { listOf(it, it.plus("ll")) }.toTypedArray()) }
    val help by entry(::initialiser) { HelpConsoleProgram(name, "?", "man", "manual", "usage", "describe", "explain", "how", "howto") }
    val time by entry(::initialiser) { TimeConsoleProgram(name, "day", "days", "hour", "hours", "minute", "minutes", "clock", "ntp") }
    val map by entry(::initialiser) { MapConsoleProgram(name, "usable", "usables", "parts", "partitions", "disks", "drives", "tree", "m") }
    val use by entry(::initialiser) { UseConsoleProgram(name, "using", "load", "loaded", "part", "partition", "disk", "drive", "mount", "up", "switch", "usepart") }
    val install by entry(::initialiser) { InstallConsoleProgram(name, "image", "setup", "os", "console", "consoleos") }
    val mkpart by entry(::initialiser) { MakePartitionConsoleProgram(name, "createpart", "makepart", "newpart", "mp", "mkp", "np") }
    val rmpart by entry(::initialiser) { RemovePartitionConsoleProgram(name, "removepart", "rempart", "deletepart", "delpart", "rp", "rmp", "dp") }
    val label by entry(::initialiser) { LabelConsoleProgram(name, "labelpart", "rnpart", "mvpart", "renamepart", "movepart", "lp", "mvp", "rnp") }
    val resize by entry(::initialiser) { ResizePartitionConsoleProgram(name, "resizepart", "sizepart", "sp", "rep") }
    val scroll by entry(::initialiser) { ScrollConsoleProgram(name, "more", "less", "see", "read", "view", "page") }
    val reboot by entry(::initialiser) { PowerConsoleProgram(name, ComputingSessionExecuteContext::reboot, "restart", "reset", "powercycle") }
    val shutdown by entry(::initialiser) { PowerConsoleProgram(name, ComputingSessionExecuteContext::shutdown, "end", "exit", "close", "off", "poweroff") }

    override fun defaultProperties(name: String) = Unit

    override fun afterInitAll(initialised: List<DirectoryEntry<out ConsoleProgram, out ConsoleProgram>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        initialised.forEach {
            val command = it.entry.command ?: return@forEach
            val destination = dispatcher.register(command)
            val aliases = it.entry.aliases
            if (aliases != null) {
                for (alias in aliases) {
                    val redirect = literal(alias)
                        .requires(destination.requirement)
                        .forward(destination.redirect, destination.redirectModifier, destination.isFork)
                        .executes(destination.command)
                    for (child in destination.children) redirect.then(child)
                    dispatcher.register(redirect)
                    _aliases[alias] = command.literal
                }
            }
        }
    }

    fun literal(name: String) = LiteralArgumentBuilder.literal<ConsoleCommandSource>(name)

    fun <T> required(name: String, type: ArgumentType<T>) = RequiredArgumentBuilder.argument<ConsoleCommandSource, T>(name, type)

}