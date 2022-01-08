package com.joshmanisdabomb.lcc.abstracts.computing.controller.console

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.LCCConsoleCommands.dispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import java.util.*

class HelpConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .executes {
            help(it.source)
        }.then(LCCConsoleCommands.required("command", StringArgumentType.greedyString()).executes {
            usage(it.source, StringArgumentType.getString(it, "command"))
        })

    fun help(source: ConsoleCommandSource): Int {
        val usage = dispatcher.getSmartUsage(dispatcher.root, source)
        source.controller.write(source.session, TranslatableText("terminal.lcc.console.help.commands"), source.view)
        usage.filterKeys { LCCConsoleCommands.all.keys.contains(it.name) }.forEach { (k, v) ->
            source.controller.write(source.session, LiteralText(v), source.view)
        }
        return 1
    }

    fun usage(source: ConsoleCommandSource, command: String): Int {
        println(command)
        //source.controller.write(source.session, , source.view)
        return 1
    }

}