package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument.CommandArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.util.Formatting

class HelpConsoleProgram(literal: String, override vararg val aliases: String) : ConsoleProgram() {

    override val command = LCCConsolePrograms.literal(literal)
        .executes {
            val nbt = NbtCompound()
            nbt.putString("Operation", "get")

            startTask(it.source, nbt)
        }.then(LCCConsolePrograms.required("command", CommandArgumentType(StringArgumentType.StringType.GREEDY_PHRASE)).executes {
            val nbt = NbtCompound()
            nbt.putString("Operation", "usage")
            nbt.putString("Node", CommandArgumentType.get(it, "command").name)

            startTask(it.source, nbt)
        })

    override fun runTask(source: ConsoleCommandSource, data: NbtCompound): Boolean? {
        when (data.getString("Operation")) {
            "get" -> get(source)
            "usage" -> usage(source, data.getString("Node"))
        }
        return null
    }

    fun get(source: ConsoleCommandSource) {
        val usage = LCCConsolePrograms.dispatcher.getSmartUsage(LCCConsolePrograms.dispatcher.root, source)
        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.commands").formatted(Formatting.AQUA), source.view)
        usage.filterKeys { LCCConsolePrograms.all.keys.contains(it.name) }.forEach { (k, v) ->
            source.controller.write(source.session, Text.literal(v).formatted(Formatting.WHITE), source.view)
        }
        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.info").formatted(Formatting.BLUE), source.view)
    }

    fun usage(source: ConsoleCommandSource, node: String) {
        val original = LCCConsolePrograms.aliases[node] ?: node
        val child = LCCConsolePrograms.dispatcher.root.getChild(node)
        val usage = LCCConsolePrograms.dispatcher.getSmartUsage(child, source)
        if (usage.isEmpty()) {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.usage", node).formatted(Formatting.AQUA), source.view)
        } else {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.usage", node + " " + usage.values.first()).formatted(Formatting.AQUA), source.view)
        }
        if (original == node) {
            val aliases = LCCConsolePrograms.entries[node]?.entry?.aliases
            if (aliases?.isNotEmpty() == true) {
                source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.aliases", aliases.joinToString(", ")).formatted(Formatting.GOLD), source.view)
            }
        } else {
            source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.alias_of", original).formatted(Formatting.GOLD), source.view)
        }
        source.controller.write(source.session, Text.translatable("terminal.lcc.console.$name.$original", node).formatted(Formatting.BLUE), source.view)
    }

}