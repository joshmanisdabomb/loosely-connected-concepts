package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.storage.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.abstracts.computing.storage.StoragePartition
import com.joshmanisdabomb.lcc.extensions.putText
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import java.util.concurrent.CompletableFuture

class DiskInfoArgumentType(vararg val allowed: DiskInfoArgumentResult, val preference: DiskInfoArgumentResult? = null) : ArgumentType<DiskInfoSearch> {

    override fun parse(reader: StringReader): DiskInfoSearch {
        val cursor = reader.cursor
        var input: String? = null
        outer@ for (i in 0..2) {
            for (j in 0 until i) {
                if (reader.peek(j) != ':') {
                    continue@outer
                }
            }
            if (reader.canRead() && (reader.peek(i) == '"' || reader.peek(i) == '\'')) {
                reader.cursor += i
                input = reader.readQuotedString()
                break
            }
        }
        if (input == null) {
            var string = ""
            while (reader.canRead() && reader.peek() != ' ') string += reader.read()
            input = string
        }

        val search = DiskInfoSearch(input)

        val diskToken = input.indexOf("::")
        val partitionToken = if (diskToken >= 0) {
            val pos = input.removeRange(diskToken, diskToken+2).indexOf(':')
            if (pos >= diskToken) pos.plus(2) else pos
        } else {
            input.indexOf(':')
        }
        if (diskToken >= 0 && partitionToken < 0 && !allowed.contains(DiskInfoArgumentResult.DISK)) throw diskDisabled.createWithContext(reader, input)
        if (partitionToken >= 0 && !allowed.contains(DiskInfoArgumentResult.PARTITION)) throw partitionDisabled.createWithContext(reader, input)

        //TODO support dual id format e.g. #4#c
        var diskInput = if (diskToken > -1) input.substring(diskToken+2, if (partitionToken > diskToken) partitionToken else input.length) else if (preference == DiskInfoArgumentResult.DISK) input else null
        var partitionInput = if (partitionToken > -1) input.substring(partitionToken+1, if (diskToken > partitionToken) diskToken else input.length) else if (preference == DiskInfoArgumentResult.PARTITION) input else null
        if (diskInput == null && partitionInput == null) {
            if (preference == DiskInfoArgumentResult.DISK) processInput(input, search::diskIdStartsWith, search::diskLabelStartsWith, reader, input, diskIdInvalid, diskEmpty)
            else if (preference == DiskInfoArgumentResult.PARTITION) processInput(input, search::partitionIdStartsWith, search::partitionLabelStartsWith, reader, input, partitionIdInvalid, partitionEmpty)
            else {
                processInput(input, { search.diskIdStartsWith(it).partitionIdStartsWith(it) }, { search.diskLabelStartsWith(it).partitionLabelStartsWith(it) }, reader, input, genericIdInvalid, genericEmpty)
            }
        } else {
            if (diskInput != null) processInput(diskInput, search::diskIdStartsWith, search::diskLabelStartsWith, reader, input, diskIdInvalid, diskEmpty)
            if (partitionInput != null) processInput(partitionInput, search::partitionIdStartsWith, search::partitionLabelStartsWith, reader, input, partitionIdInvalid, partitionEmpty)
        }
        return search
    }

    private fun processInput(input: String, searchId: (id: String) -> Unit, searchLabel: (label: String) -> Unit, reader: StringReader, fullInput: String, invalid: DynamicCommandExceptionType, empty: DynamicCommandExceptionType) {
        if (input.isNotEmpty()) {
            if (input.startsWith("#")) {
                val id = input.drop(1).replace("-", "").lowercase()
                if (!id.matches(idRegex)) throw invalid.createWithContext(reader, id)
                searchId(id)
                return
            } else {
                searchLabel(input)
                return
            }
        }
        throw empty.createWithContext(reader, fullInput)
    }

    override fun <S> listSuggestions(context: CommandContext<S>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        return Suggestions.empty()
    }

    companion object {
        private val idRegex = Regex("^([-A-Fa-f0-9]+)$")
        private val idSuggestRegex = Regex("^:*#")
        private val quoteRegex = Regex("^:*\"")

        val diskDisabled = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.disk.disabled", it) }
        val partitionDisabled = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.partition.disabled", it) }
        val genericEmpty = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.diskpart.empty", it) }
        val diskEmpty = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.disk.empty", it) }
        val partitionEmpty = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.partition.empty", it) }
        val genericIdInvalid = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.diskpart.id.invalid", it) }
        val diskIdInvalid = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.disk.id.invalid", it) }
        val partitionIdInvalid = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.partition.id.invalid", it) }

        val noGeneric = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.diskpart.results.none", it) }
        val multipleGeneric = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.diskpart.results.multiple", it) }
        val noDisks = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.disk.results.none", it) }
        val multipleDisks = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.disk.results.multiple", it) }
        val noPartitions = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.partition.results.none", it) }
        val multiplePartitions = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.partition.results.multiple", it) }
        val conflict = DynamicCommandExceptionType { Text.translatable("terminal.lcc.console.argument.diskpart.results.conflict", it) }

        fun suggestDisks(disks: Iterable<StorageDisk>, builder: SuggestionsBuilder): List<String> {
            val input = builder.remaining
            val quotesRequired = input.matches(quoteRegex)
            var suggestions = if (input.matches(idSuggestRegex)) {
                disks.mapNotNull { it.getShortId(disks)?.let { "#$it" } }
            } else {
                disks.mapNotNull { it.label?.let { if (quotesRequired || it.contains(' ')) "\"$it\"" else it } ?: it.getShortId(disks)?.let { "#$it" } }
            }
            if (input.startsWith(':')) {
                suggestions = suggestions.map { "::$it" }
            }
            return suggestions
        }

        fun suggestPartitions(disks: Iterable<StorageDisk>, builder: SuggestionsBuilder): List<String> {
            val input = builder.remaining
            val quotesRequired = input.matches(quoteRegex)
            val partitions = StorageDisk.getPartitions(disks)
            var suggestions = if (input.matches(idSuggestRegex)) {
                partitions.mapNotNull { it.getShortId(disks)?.let { "#$it" } }
            } else {
                partitions.map { it.label.let { if (quotesRequired || it.contains(' ')) "\"$it\"" else it } }
            }
            if (input.startsWith(':')) {
                suggestions = suggestions.map { ":$it" }
            }
            return suggestions
        }

        fun suggestAll(disks: Iterable<StorageDisk>, builder: SuggestionsBuilder) = suggestDisks(disks, builder) + suggestPartitions(disks, builder)

        fun get(context: CommandContext<ConsoleCommandSource>, argument: String): DiskInfoSearch = context.getArgument(argument, DiskInfoSearch::class.java)

        fun getSingleDisk(diskResults: Set<StorageDisk>?, search: DiskInfoSearch) = when (diskResults?.size) {
            0 -> throw noDisks.create(search)
            1 -> diskResults.first()
            null -> null
            else -> throw multipleDisks.create(search)
        }

        fun getSinglePartition(partitionResults: Set<StoragePartition>?, search: DiskInfoSearch) = when (partitionResults?.size) {
            0 -> throw noPartitions.create(search)
            1 -> partitionResults.first()
            null -> null
            else -> throw multiplePartitions.create(search)
        }

        fun getSingleDiskOrPartition(results: Pair<Set<StorageDisk>?, Set<StoragePartition>?>, search: DiskInfoSearch): Pair<StorageDisk?, StoragePartition?> {
            val (diskResults, partitionResults) = results
            return when ((diskResults?.size ?: 0) + (partitionResults?.size ?: 0)) {
                0 -> throw noGeneric.create(search)
                1 -> diskResults?.firstOrNull()?.let { it to null } ?: partitionResults?.firstOrNull()?.let { null to it } ?: throw noGeneric.create(search)
                else -> throw multipleGeneric.create(search)
            }
        }

        fun attachResultsToNbt(disks: Iterable<StorageDisk>, results: Pair<StorageDisk?, StoragePartition?>, search: DiskInfoSearch, nbt: NbtCompound) {
            val (disk, partition) = results
            when {
                partition != null && disk != null -> throw conflict.create(search)
                disk != null -> {
                    nbt.putUuid("Disk", disk.id)
                    nbt.putString("DiskShort", disk.getShortId(disks))
                    nbt.putText("DiskLabel", disk.name)
                }
                partition != null -> {
                    nbt.putUuid("Partition", partition.id)
                    nbt.putString("PartitionShort", partition.getShortId(disks))
                    nbt.putString("PartitionLabel", partition.label)
                }
                else -> throw genericEmpty.create(search)
            }
        }
    }

    enum class DiskInfoArgumentResult {
        DISK,
        PARTITION;
    }

}
