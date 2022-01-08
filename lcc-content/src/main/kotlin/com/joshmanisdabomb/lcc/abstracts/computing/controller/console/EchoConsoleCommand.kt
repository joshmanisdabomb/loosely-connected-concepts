package com.joshmanisdabomb.lcc.abstracts.computing.controller.console

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.text.LiteralText
import java.util.*

class EchoConsoleCommand(val name: String, val view: ConsoleCommandSource.() -> UUID?) {

    val command = LCCConsoleCommands.literal(name)
        .then(LCCConsoleCommands.required("message", StringArgumentType.greedyString()).executes {
            echo(it.source, StringArgumentType.getString(it, "message"))
        })

    fun echo(source: ConsoleCommandSource, message: String): Int {
        source.controller.write(source.session, LiteralText(message), source.view())
        return 1
    }

}