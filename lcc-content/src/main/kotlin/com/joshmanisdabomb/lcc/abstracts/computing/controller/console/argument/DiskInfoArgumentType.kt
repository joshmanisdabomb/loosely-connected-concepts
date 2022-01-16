package com.joshmanisdabomb.lcc.abstracts.computing.controller.console.argument

import com.joshmanisdabomb.lcc.abstracts.computing.controller.console.ConsoleCommandSource
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskInfoSearch
import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskPartition
import com.joshmanisdabomb.lcc.extensions.putText
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.TranslatableText
import java.util.concurrent.CompletableFuture

class DiskInfoArgumentType(vararg val allowed: DiskInfoArgumentResult, val preference: DiskInfoArgumentResult? = null) : ArgumentType<DiskInfoSearch> {

    override fun parse(reader: StringReader): DiskInfoSearch {
        val cursor = reader.cursor
        val input = if (reader.canRead() && (reader.peek() == '"' || reader.peek() == '\'')) {
            reader.readQuotedString()
        } else {
            var string = ""
            while (reader.canRead() && reader.peek() != ' ') string += reader.read()
            string
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
                search.diskFilter = false
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

        val diskDisabled = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.disabled", it) }
        val partitionDisabled = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.partition.disabled", it) }
        val genericEmpty = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.diskpart.empty", it) }
        val diskEmpty = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.empty", it) }
        val partitionEmpty = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.partition.empty", it) }
        val genericIdInvalid = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.diskpart.id.invalid", it) }
        val diskIdInvalid = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.id.invalid", it) }
        val partitionIdInvalid = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.partition.id.invalid", it) }

        val noGeneric = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.diskpart.results.none", it) }
        val multipleGeneric = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.diskpart.results.multiple", it) }
        val noDisks = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.results.none", it) }
        val multipleDisks = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.disk.results.multiple", it) }
        val noPartitions = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.partition.results.none", it) }
        val multiplePartitions = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.partition.results.multiple", it) }
        val conflict = DynamicCommandExceptionType { TranslatableText("terminal.lcc.console.argument.diskpart.results.conflict", it) }

        fun suggestDisks(disks: Iterable<DiskInfo>, builder: SuggestionsBuilder): List<String> {
            val input = builder.remaining
            var suggestions = if (input.matches(idSuggestRegex)) {
                disks.mapNotNull { it.getShortId(disks)?.let { "#$it" } }
            } else {
                disks.mapNotNull { it.label?.let { "\"$it\"" } }
            }
            if (input.startsWith(':')) {
                suggestions = suggestions.map { "::$it" }
            }
            return suggestions
        }

        fun suggestPartitions(disks: Iterable<DiskInfo>, builder: SuggestionsBuilder): List<String> {
            val input = builder.remaining
            val partitions = DiskInfo.getPartitions(disks)
            var suggestions = if (input.matches(idSuggestRegex)) {
                partitions.mapNotNull { it.getShortId(disks)?.let { "#$it" } }
            } else {
                partitions.map { it.label.let { "\"$it\"" } }
            }
            if (input.startsWith(':')) {
                suggestions = suggestions.map { ":$it" }
            }
            return suggestions
        }

        fun suggestAll(disks: Iterable<DiskInfo>, builder: SuggestionsBuilder) = suggestDisks(disks, builder) + suggestPartitions(disks, builder)

        fun get(context: CommandContext<ConsoleCommandSource>, argument: String): DiskInfoSearch = context.getArgument(argument, DiskInfoSearch::class.java)

        fun getSingleDisk(diskResults: Set<DiskInfo>?, search: DiskInfoSearch) = when (diskResults?.size) {
            0 -> throw noDisks.create(search)
            1 -> diskResults.first()
            null -> null
            else -> throw multipleDisks.create(search)
        }

        fun getSinglePartition(partitionResults: Set<DiskPartition>?, search: DiskInfoSearch) = when (partitionResults?.size) {
            0 -> throw noPartitions.create(search)
            1 -> partitionResults.first()
            null -> null
            else -> throw multiplePartitions.create(search)
        }

        fun getSingleDiskOrPartition(results: Pair<Set<DiskInfo>?, Set<DiskPartition>?>, search: DiskInfoSearch): Pair<DiskInfo?, DiskPartition?> {
            val (diskResults, partitionResults) = results
            return when ((diskResults?.size ?: 0) + (partitionResults?.size ?: 0)) {
                0 -> throw noGeneric.create(search)
                1 -> diskResults?.firstOrNull()?.let { it to null } ?: partitionResults?.firstOrNull()?.let { null to it } ?: throw noGeneric.create(search)
                else -> throw multipleGeneric.create(search)
            }
        }

        fun attachResultsToNbt(disks: Iterable<DiskInfo>, results: Pair<DiskInfo?, DiskPartition?>, search: DiskInfoSearch, nbt: NbtCompound) {
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
