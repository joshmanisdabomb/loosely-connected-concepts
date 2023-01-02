package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StoragePath
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.concurrent.CompletableFuture

class StoragePathArgumentType : ArgumentType<StoragePath> {

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
                    //token finished but tried to continue
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(reader, 1)
                }
            } else if (Character.isWhitespace(next) && quote == null) {
                break
            } else {
                if (next == '"' || next == '\'') {
                    if (quote == null && reader.cursor != cursor && token == null) {
                        //started quote in the middle of a label
                        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(reader, 2)
                    }
                    if (quote == null) quote = next
                    else if (quote == next) quote = null
                }
                if (token != null) {
                    if (token != "/" && !availableTokens.remove(token)) {
                        //duplicate disk or partition token
                        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(reader, 3)
                    }
                    availableTokens.remove("::")
                    if (token == "/") availableTokens.remove(":")
                }
                token = null
            }
            input.append(next)
            reader.skip()
        }
        if (quote != null) {
            //unfinished quote
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(reader, 4)
        }
        if (token?.startsWith(":") == true) {
            //unfinished disk or partition
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(reader, 5)
        }
        return StoragePath(input.toString())
    }

    override fun <S> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return Suggestions.empty()
    }

    companion object {
        fun get(context: CommandContext<ConsoleCommandSource>, argument: String) = context.getArgument(argument, StoragePath::class.java)
    }

}