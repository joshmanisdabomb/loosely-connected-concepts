package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.partition.LCCPartitionTypes
import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandSource
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.InvalidIdentifierException
import java.util.concurrent.CompletableFuture

class PartitionArgumentType(val predicate: (PartitionType) -> Boolean) : ArgumentType<PartitionType> {

    override fun parse(reader: StringReader): PartitionType {
        val cursor = reader.cursor
        while (reader.canRead() && Identifier.isCharValid(reader.peek())) reader.skip()
        val string = reader.string.substring(cursor, reader.cursor)
        val id = try {
            LCC.idDefaultNamespace(string)
        } catch (e: InvalidIdentifierException) {
            throw typeInvalid.createWithContext(reader, string)
        }
        return LCCPartitionTypes.registry[id]?.takeIf(predicate) ?: throw typeInvalid.createWithContext(reader, string)
    }

    override fun <S> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> = CommandSource.suggestMatching(LCCPartitionTypes.registry.filter(predicate).map { it.id.toString() }, builder)

    companion object {
        private val typeInvalid = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.partition_type.invalid", it) }

        fun get(context: CommandContext<ConsoleCommandSource>, argument: String) = context.getArgument(argument, PartitionType::class.java)
    }

}