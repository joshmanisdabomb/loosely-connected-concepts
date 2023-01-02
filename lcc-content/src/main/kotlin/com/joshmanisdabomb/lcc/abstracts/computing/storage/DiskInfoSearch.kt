package com.joshmanisdabomb.lcc.abstracts.computing.storage

class DiskInfoSearch(private val input: String) {

    private val diskFilters = mutableListOf<(StorageDisk) -> Boolean>()
    private val partitionFilters = mutableListOf<(StoragePartition) -> Boolean>()

    fun search(disks: Iterable<StorageDisk>) = (if (diskFilters.isNotEmpty()) searchDisks(disks) else null) to (if (partitionFilters.isNotEmpty()) searchPartitions(StorageDisk.getPartitions(disks)) else null)

    fun searchDisks(disks: Iterable<StorageDisk>) = diskFilters.fold(disks, Iterable<StorageDisk>::filter).toSet()

    fun searchPartitions(partitions: Iterable<StoragePartition>) = partitionFilters.fold(partitions, Iterable<StoragePartition>::filter).toSet()

    fun addDiskFilter(filter: (StorageDisk) -> Boolean): DiskInfoSearch {
        diskFilters.add(filter)
        return this
    }

    fun addPartitionFilter(filter: (StoragePartition) -> Boolean): DiskInfoSearch {
        partitionFilters.add(filter)
        return this
    }

    fun diskDefaultInclusion() = addDiskFilter { true }

    fun partitionDefaultInclusion() = addPartitionFilter { true }

    fun diskIdStartsWith(term: String): DiskInfoSearch {
        val id = term.replace("-", "").lowercase()
        return addDiskFilter { it.id?.toString()?.replace("-", "")?.lowercase()?.startsWith(id) == true }
    }

    fun partitionIdStartsWith(term: String): DiskInfoSearch {
        val id = term.replace("-", "").lowercase()
        return addPartitionFilter { it.id?.toString()?.replace("-", "")?.lowercase()?.startsWith(id) == true }
    }

    fun diskLabelStartsWith(term: String): DiskInfoSearch {
        val label = term.lowercase()
        return addDiskFilter { it.label?.lowercase()?.startsWith(label) == true }
    }

    fun partitionLabelStartsWith(term: String): DiskInfoSearch {
        val label = term.lowercase()
        return addPartitionFilter { it.label.lowercase().startsWith(label) }
    }

    override fun toString() = input

}