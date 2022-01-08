package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.command

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.CommandArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.tree.CommandNode
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting

class HelpConsoleCommand(val name: String) {

    val command = LCCConsoleCommands.literal(name)
        .executes {
            help(it.source)
        }.then(LCCConsoleCommands.required("command", CommandArgumentType(StringArgumentType.StringType.GREEDY_PHRASE)).executes {
            usage(it.source, CommandArgumentType.get(it, "command"))
        })

    fun help(source: ConsoleCommandSource): Int {
        val usage = LCCConsoleCommands.dispatcher.getSmartUsage(LCCConsoleCommands.dispatcher.root, source)
        source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.commands").formatted(Formatting.AQUA), source.view)
        usage.filterKeys { LCCConsoleCommands.all.keys.contains(it.name) }.forEach { (k, v) ->
            source.controller.write(source.session, LiteralText(v).formatted(Formatting.WHITE), source.view)
        }
        source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.info").formatted(Formatting.BLUE), source.view)
        return 1
    }

    fun usage(source: ConsoleCommandSource, node: CommandNode<ConsoleCommandSource>): Int {
        val original = LCCConsoleCommands.aliases[node.name] ?: node.name
        val usage = LCCConsoleCommands.dispatcher.getSmartUsage(node, source)
        if (usage.isEmpty()) {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.usage", node.name).formatted(Formatting.AQUA), source.view)
        } else {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.usage", node.name + " " + usage.values.first()).formatted(Formatting.AQUA), source.view)
        }
        if (original == node.name) {
            val aliases = LCCConsoleCommands.entries[node.name]?.properties?.joinToString(",")
            if (aliases?.isNotEmpty() == true) {
                source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.aliases", aliases).formatted(Formatting.GOLD), source.view)
            }
        } else {
            source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.alias_of", original).formatted(Formatting.GOLD), source.view)
        }
        source.controller.write(source.session, TranslatableText("terminal.lcc.console.$name.$original", node.name).formatted(Formatting.BLUE), source.view)
        return 1
    }

    private val failedUsageCommandUnknown = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.$name.unknown", it) }

}