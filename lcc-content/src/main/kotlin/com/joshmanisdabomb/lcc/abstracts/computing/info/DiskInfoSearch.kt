package com.joshmanisdabomb.lcc.abstracts.computing.info

class DiskInfoSearch(private val input: String) {

    private val diskFilters = mutableListOf<(DiskInfo) -> Boolean>()
    private val partitionFilters = mutableListOf<(DiskPartition) -> Boolean>()

    fun search(disks: Iterable<DiskInfo>) = (if (diskFilters.isNotEmpty()) searchDisks(disks) else null) to (if (partitionFilters.isNotEmpty()) searchPartitions(DiskInfo.getPartitions(disks)) else null)

    fun searchDisks(disks: Iterable<DiskInfo>) = diskFilters.fold(disks, Iterable<DiskInfo>::filter).toSet()

    fun searchPartitions(partitions: Iterable<DiskPartition>) = partitionFilters.fold(partitions, Iterable<DiskPartition>::filter).toSet()

    fun addDiskFilter(filter: (DiskInfo) -> Boolean): DiskInfoSearch {
        diskFilters.add(filter)
        return this
    }

    fun addPartitionFilter(filter: (DiskPartition) -> Boolean): DiskInfoSearch {
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