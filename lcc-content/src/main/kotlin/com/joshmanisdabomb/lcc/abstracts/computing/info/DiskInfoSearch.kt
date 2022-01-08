package com.joshmanisdabomb.lcc.abstracts.computing.info

class DiskInfoSearch {

    lateinit var input: String
    var diskFilter = true

    private val diskFilters = mutableListOf<(DiskInfo) -> Boolean>()
    private val partitionFilters = mutableListOf<(DiskPartition) -> Boolean>()

    fun search(disks: Iterable<DiskInfo>) = (if (diskFilters.isNotEmpty() && (partitionFilters.isEmpty() || !diskFilter)) searchDisks(disks) else null) to (if (partitionFilters.isEmpty()) null else if (diskFilter) searchPartitionsIn(disks) else searchPartitions(disks.flatMap { it.partitions }))

    fun searchDisks(disks: Iterable<DiskInfo>) = diskFilters.fold(disks, Iterable<DiskInfo>::filter).toSet()

    fun searchPartitions(partitions: Iterable<DiskPartition>) = partitionFilters.fold(partitions, Iterable<DiskPartition>::filter).toSet()

    fun searchPartitionsIn(disks: Iterable<DiskInfo>) = searchPartitions(searchDisks(disks).flatMap { it.partitions })

    fun addDiskFilter(filter: (DiskInfo) -> Boolean): DiskInfoSearch {
        diskFilters.add(filter)
        return this
    }

    fun addPartitionFilter(filter: (DiskPartition) -> Boolean): DiskInfoSearch {
        partitionFilters.add(filter)
        return this
    }

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