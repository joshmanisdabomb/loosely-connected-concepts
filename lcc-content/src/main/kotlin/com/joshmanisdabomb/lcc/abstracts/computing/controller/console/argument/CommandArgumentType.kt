package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.program.LCCConsolePrograms
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import com.mojang.brigadier.tree.CommandNode
import net.minecraft.command.CommandSource
import net.minecraft.text.Text
import java.util.concurrent.CompletableFuture

class CommandArgumentType(val type: StringArgumentType.StringType) : ArgumentType<CommandNode<ConsoleCommandSource>> {

    override fun parse(reader: StringReader): CommandNode<ConsoleCommandSource> {
        val string = when (type) {
            StringArgumentType.StringType.SINGLE_WORD -> reader.readUnquotedString()
            StringArgumentType.StringType.GREEDY_PHRASE -> reader.remaining.also { reader.cursor = reader.totalLength }
            else -> reader.readString()
        }
        val command = string.split(" ").firstOrNull() ?: ""
        return LCCConsolePrograms.dispatcher.root.getChild(command) ?: throw commandInvalid.createWithContext(reader, command)
    }

    override fun <S> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        val children: Iterable<String>
        if (builder.remaining.isEmpty()) {
            children = LCCConsolePrograms.all.keys
        } else {
            children = LCCConsolePrograms.dispatcher.root.children.map { it.name }
        }
        return CommandSource.suggestMatching(children, builder)
    }

    companion object {
        private val commandInvalid = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.command.invalid", it) }

        fun get(context: CommandContext<ConsoleCommandSource>, argument: String): CommandNode<ConsoleCommandSource> = context.getArgument(argument, CommandNode::class.java) as CommandNode<ConsoleCommandSource>
    }

}