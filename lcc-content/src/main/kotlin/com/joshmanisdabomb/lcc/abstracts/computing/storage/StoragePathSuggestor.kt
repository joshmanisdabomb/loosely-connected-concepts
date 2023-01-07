package com.joshmanisdabomb.lcc.abstracts.computing.storage

import com.joshmanisdabomb.lcc.component.ComputingStorageComponent
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import java.util.*

class StoragePathSuggestor(vararg val shown: StorageDivision.StorageDivisionType) {

    private var using: UUID? = null
    private var cd: UUID? = null

    fun withCurrentPartition(using: UUID?): StoragePathSuggestor {
        this.using = using
        return this
    }

    fun withCurrentDirectory(directory: UUID?): StoragePathSuggestor {
        cd = directory
        return this
    }

    fun suggest(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent, builder: SuggestionsBuilder): List<String> {
        val path = StoragePath(builder.remaining)
        val order = if (shown.isNotEmpty()) shown else default
        val fs = if (order.contains(StorageDivision.StorageDivisionType.FOLDER) || order.contains(StorageDivision.StorageDivisionType.FILE)) suggestFilesystem(disks, storage, path, order.contains(StorageDivision.StorageDivisionType.FILE)) else emptyMap()
        return order.flatMap {
            when (it) {
                StorageDivision.StorageDivisionType.DISK -> suggestDisks(disks, path)
                StorageDivision.StorageDivisionType.PARTITION -> suggestPartitions(disks, path)
                StorageDivision.StorageDivisionType.FOLDER -> fs[StorageDivision.StorageDivisionType.FOLDER] ?: emptyList()
                StorageDivision.StorageDivisionType.FILE -> fs[StorageDivision.StorageDivisionType.FILE] ?: emptyList()
            }
        }
    }

    private fun suggestDisks(disks: Iterable<StorageDisk>, path: StoragePath): List<String> {
        if (path.type != null && path.type > StorageDivision.StorageDivisionType.DISK) return emptyList()
        val quoted = path.result.raw.startsWith('"') || path.result.raw.startsWith("'")

        var suggestions = if (path.result.input.startsWith('#')) {
            disks.mapNotNull { it.getShortId(disks)?.let { "#$it" } }
        } else {
            disks.mapNotNull { it.label?.let { wrapQuotes(it, quoted) } ?: it.getShortId(disks)?.let { "#$it" } }
        }

        val token = path.parts.firstOrNull { it.token && it.type == StorageDivision.StorageDivisionType.DISK }
        if (token != null) {
            suggestions = suggestions.map { "${token.input}$it" }
        }

        return suggestions
    }

    private fun suggestPartitions(disks: Iterable<StorageDisk>, path: StoragePath): List<String> {
        if (path.type != null && path.type > StorageDivision.StorageDivisionType.PARTITION) return emptyList()
        val disk = path.disk

        val partitions = disks.filter { filterDisks(it, disk) }.flatMap { it.partitions }
        val quoted = path.result.raw.startsWith('"') || path.result.raw.startsWith("'")

        var suggestions = if (path.result.input.startsWith('#')) {
            partitions.mapNotNull { it.getShortId(disks)?.let { "#$it" } }
        } else {
            partitions.map { wrapQuotes(it.label, quoted) }
        }

        val token = path.parts.firstOrNull { it.token && it.type == StorageDivision.StorageDivisionType.PARTITION }
        if (token != null) {
            suggestions = suggestions.map { "${token.input}$it" }

            val prevTokens = path.parts.filter { it.type == StorageDivision.StorageDivisionType.DISK }
            if (prevTokens.isNotEmpty()) {
                val before = prevTokens.joinToString("") { it.raw }
                suggestions = suggestions.map { "$before$it" }
            }
        }

        return suggestions
    }

    private fun suggestFilesystem(disks: Iterable<StorageDisk>, storage: ComputingStorageComponent, path: StoragePath, suffixFolders: Boolean = true): Map<StorageDivision.StorageDivisionType, List<String>> {
        val disk = path.disk
        val partition = path.partition
        val filepath = path.filepath?.toMutableList() ?: mutableListOf()

        var partitions = disks.filter { filterDisks(it, disk) }.flatMap { it.partitions }.filter { filterPartitions(it, partition) }
        if (disk == null && partition == null && using != null) {
            partitions = partitions.filter { it.id == using }
        }

        val lastToken = path.parts.lastOrNull()
        if (lastToken?.token == false) filepath.removeLastOrNull()
        println(lastToken)
        if (lastToken?.token == true && lastToken.type?.filesystem == false) return emptyMap()
        val prevTokens = path.parts.filter { it.type?.filesystem == false }

        val suggestions = mutableMapOf<StorageDivision.StorageDivisionType, MutableList<String>>()
        partition@for (partition in partitions) {
            var current = if (path.absolute) {
                val pid = partition.id ?: continue
                storage.getRootFolder(pid)
            } else {
                val cd = cd ?: continue
                storage.getFolder(cd)
            } ?: continue
            for (next in filepath) {
                if (next.input == ".") continue
                if (next.input == "..") {
                    val up = current.path.lastOrNull() ?: continue
                    current = storage.getFolder(up) ?: continue
                    continue
                }
                val folders = storage.getFolders(*current.folders.toTypedArray())
                current = folders.firstOrNull { it.name == next.input } ?: continue@partition
            }
            
            var folders = storage.getFolders(*current.folders.toTypedArray()).map { it.name }
            var files = storage.getFiles(*current.files.toTypedArray()).map { it.name }
            if (suffixFolders) folders = folders.map { "$it/" }

            var prefix = prevTokens.joinToString("") { it.raw }
            if (path.absolute) prefix += "/"
            folders = folders.map { "$prefix$it" }
            files = files.map { "$prefix$it" }

            suggestions.computeIfAbsent(StorageDivision.StorageDivisionType.FOLDER) { mutableListOf() }.addAll(folders)
            suggestions.computeIfAbsent(StorageDivision.StorageDivisionType.FILE) { mutableListOf() }.addAll(files)
        }

        return suggestions
    }

    private fun filterDisks(disk: StorageDisk, label: StoragePath.StoragePathPart?): Boolean {
        if (label == null) return true
        if (label.input.startsWith('#')) return disk.printId?.startsWith(label.input.substring(1)) == true
        return disk.label?.startsWith(label.input) == true
    }

    private fun filterPartitions(partition: StoragePartition, label: StoragePath.StoragePathPart?): Boolean {
        if (label == null) return true
        if (label.input.startsWith('#')) return partition.printId?.startsWith(label.input.substring(1)) == true
        return partition.label.startsWith(label.input)
    }

    private fun wrapQuotes(label: String, force: Boolean): String {
        if (!force && label.none(Character::isWhitespace)) return label
        if (label.contains('"') && !label.contains("'")) return "'$label'"
        return "\"$label\""
    }

    companion object {
        private val default = StorageDivision.StorageDivisionType.values()
    }

}