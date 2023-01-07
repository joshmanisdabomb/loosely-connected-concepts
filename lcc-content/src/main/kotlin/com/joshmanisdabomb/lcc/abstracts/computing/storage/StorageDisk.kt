package com.joshmanisdabomb.lcc.abstracts.computing.storage

import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.item.DigitalMediumItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import java.util.*

data class StorageDisk(val stack: ItemStack) : StorageHardDivision {

    override val division = StorageDivision.StorageDivisionType.DISK

    val tag get() = stack.getSubNbt("lcc-computing")
    private val _tag get() = stack.getOrCreateSubNbt("lcc-computing")

    val medium = stack.item as DigitalMediumItem
    val totalSpace = medium.getLevel(stack)

    override var id: UUID?
        get() = tag?.getUuidOrNull("id")
        set(value) = _tag.putUuidOrRemove("id", value)

    var label: String?
        get() = tag?.getStringOrNull("name")
        set(value) = _tag.putStringOrRemove("name", value)

    var partitions: List<StoragePartition>
        get() = tag?.getCompoundObjectList("partitions") { StoragePartition(it, this) } ?: emptyList()
        set(value) {
            _tag.putCompoundObjectList("partitions", value) {
                val nbt = NbtCompound()
                it.writeNbt(nbt)
                nbt
            }
        }

    val name get() = label?.let { Text.literal(it) } ?: stack.name

    val usedSpace get() = partitions.sumOf { it.usedSpace }
    val freeSpace get() = totalSpace - usedSpace
    val allocatedSpace get() = partitions.sumOf { it.size }
    val allocableSpace get() = totalSpace - allocatedSpace

    fun initialise(): StorageDisk {
        id = id ?: UUID.randomUUID()
        val parts = partitions
        for (partition in parts) {
            partition.id = partition.id ?: UUID.randomUUID()
        }
        return this
    }

    fun addPartition(partition: StoragePartition) {
        partition.disk = this
        partitions = partitions.plus(partition)
    }

    fun getPartition(id: UUID) = partitions.firstOrNull { it.id == id }

    fun removePartition(id: UUID) { partitions = partitions.filter { it.id != id } }

    fun removePartition(partition: StoragePartition) { removePartition(partition.id ?: return) }

    companion object {
        fun getPartitions(disks: Iterable<StorageDisk>) = disks.flatMap { it.partitions }.toSet()

        fun getDisk(disks: Iterable<StorageDisk>, id: UUID) = disks.firstOrNull { it.id == id }

        fun getDiskWithPartition(disks: Iterable<StorageDisk>, id: UUID) = disks.firstOrNull { it.partitions.any { it.id == id } }

        fun getPartition(partitions: Iterable<StoragePartition>, id: UUID) = partitions.firstOrNull { it.id == id }

        fun findPartition(disks: Iterable<StorageDisk>, id: UUID) = disks.firstNotNullOfOrNull { it.partitions.firstOrNull { it.id == id } }
    }

}