package com.joshmanisdabomb.lcc.abstracts.computing.storage

import java.util.*

interface StorageHardDivision : StorageDivision {

    var id: UUID?
    val printId get() = id?.toString()?.replace("-", "")

    fun getShortId(disks: Iterable<StorageDisk>): String? {
        return getShortId(disks, printId ?: return null)
    }

    companion object {
        fun getShortId(disks: Iterable<StorageDisk>, printId: String): String {
            val storages = disks + disks.flatMap { it.partitions }
            val others = storages.mapNotNull { it.printId.takeUnless { it == printId } }
            for (i in printId.indices) {
                val meShort = printId.substring(0, i + 1)
                if (others.none { it.startsWith(meShort) }) {
                    return meShort
                }
            }
            return printId
        }
    }

}