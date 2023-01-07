package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDivision
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StoragePath
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.text.Text
import java.util.concurrent.CompletableFuture

class StoragePathArgumentType(vararg val allowed: StorageDivision.StorageDivisionType) : ArgumentType<StoragePath> {

    override fun parse(reader: StringReader): StoragePath {
        val cursor = reader.cursor
        val input = StringBuilder()
        var token: String? = null
        val availableTokens = mutableSetOf("::", ":", "/")
        var quote: Char? = null
        while (reader.canRead()) {
            val next = reader.peek()
            if (quote == null && token == ":" && next == ':' && reader.cursor == cursor + 1) {
                token += next
            } else if (quote == null && (next == ':' || next == '/')) {
                if (token == null) {
                    token = next.toString()
                } else {
                    throw tokenInvalid.createWithContext(reader, token + next)
                }
            } else if (Character.isWhitespace(next) && quote == null) {
                break
            } else {
                if (next == '"' || next == '\'') {
                    if (quote == null && reader.cursor != cursor && token == null) {
                        throw quoteLate.createWithContext(reader)
                    }
                    if (quote == null) quote = next
                    else if (quote == next) quote = null
                }
                if (token != null) {
                    if (token != "/" && !availableTokens.remove(token)) {
                        throw tokenDuplicated.createWithContext(reader, token)
                    }
                    availableTokens.remove("::")
                    if (token == "/") availableTokens.remove(":")
                }
                token = null
            }
            input.append(next)
            reader.skip()
        }
        if (quote != null) throw quoteMissing.createWithContext(reader, quote)
        if (token?.startsWith(":") == true) throw tokenEnd.createWithContext(reader, token)

        val path = StoragePath(input.toString())
        if (path.type != null && allowed.isNotEmpty() && !allowed.contains(path.type)) {
            throw invalidType.createWithContext(reader, allowed.sorted().joinToString("") { it.char.toString() }, path.type.char)
        }
        return path
    }

    override fun <S> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return Suggestions.empty()
    }

    companion object {
        fun get(context: CommandContext<ConsoleCommandSource>, argument: String) = context.getArgument(argument, StoragePath::class.java)

        val tokenInvalid = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.path.token.invalid", it) }
        val tokenDuplicated = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.path.token.duplicated", it) }
        val tokenEnd = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.path.token.end", it) }
        val quoteLate = SimpleCommandExceptionType(Text.translatable("terminal.lcc.console.argument.path.quote.late"))
        val quoteMissing = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.path.quote.missing", it) }
        val invalidType = Dynamic2CommandExceptionType { a, b -> Text.translatable("terminal.lcc.console.argument.path.invalid.$a.$b") }
    }

}