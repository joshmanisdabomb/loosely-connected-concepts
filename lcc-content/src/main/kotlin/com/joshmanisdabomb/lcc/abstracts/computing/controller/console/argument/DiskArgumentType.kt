package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument

import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandSource
import net.minecraft.text.TranslatableText
import java.util.*
import java.util.concurrent.CompletableFuture

class DiskArgumentType : ArgumentType<DiskInfoSearch> {

    override fun parse(reader: StringReader): DiskInfoSearch {
        if (reader.canRead() && reader.peek() == ':') {
            reader.expect(':')
            if (!reader.canRead() || reader.peek() != ':') throw partitionPrefix.createWithContext(reader)
            reader.expect(':')
        }
        if (reader.canRead() && reader.peek() == '#') {
            reader.expect('#')
            val id = reader.readString().replace("-", "").lowercase()
            if (!id.matches(diskIdRegex)) throw diskIdInvalid.createWithContext(reader, id)
            return DiskInfoSearch().diskIdStartsWith(id).apply { string = "#$id" }
        } else {
            val label = reader.readString()
            return DiskInfoSearch().diskLabelStartsWith(label).apply { string = label }
        }
    }

    override fun <S> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        val source = context.source as? ConsoleCommandSource ?: return Suggestions.empty()
        val disks = source.context.getAccessibleDisks()
        val ids = disks.mapNotNull { it.getShortId(disks)?.let { "#$it" } }
        val labels = disks.mapNotNull { it.label?.let { "\"$it\"" } }

        var suggestions = labels + ids
        if (builder.remaining.startsWith(':')) {
            suggestions = suggestions.map { "::$it" }
        }
        return CommandSource.suggestMatching(suggestions, builder)
    }

    companion object {
        private val diskIdRegex = Regex("^([-A-Fa-f0-9]+)$")

        val partitionPrefix = SimpleCommandExceptionType(TranslatableText("terminal.lcc.console.argument.disk.prefix.partition"))
        val diskIdInvalid = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.id.invalid", it) }

        val noDisks = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.results.none", it) }
        val multipleDisks = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.results.multiple", it) }

        fun get(context: CommandContext<ConsoleCommandSource>, argument: String): DiskInfoSearch = context.getArgument(argument, DiskInfoSearch::class.java)
    }

}
