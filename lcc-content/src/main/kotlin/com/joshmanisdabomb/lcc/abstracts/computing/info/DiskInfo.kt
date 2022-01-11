package com.joshmanisdabomb.lcc.abstracts.computing.info

import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.item.DigitalMediumItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.LiteralText
import java.util.*

data class DiskInfo(val stack: ItemStack) {

    val tag get() = stack.getSubNbt("lcc-computing")
    private val _tag get() = stack.getOrCreateSubNbt("lcc-computing")

    val medium = stack.item as DigitalMediumItem
    val totalSpace = medium.getLevel(stack)

    var id: UUID?
        get() = tag?.getUuidOrNull("id")
        set(value) = _tag.putUuidOrRemove("id", value)

    var label: String?
        get() = tag?.getStringOrNull("name")
        set(value) = _tag.putStringOrRemove("name", value)

    var partitions: List<DiskPartition>
        get() = tag?.getCompoundObjectList("partitions", map = ::DiskPartition) ?: emptyList()
        set(value) {
            _tag.putCompoundObjectList("partitions", value) {
                val nbt = NbtCompound()
                it.writeNbt(nbt)
                nbt
            }
        }

    val name get() = label?.let { LiteralText(it) } ?: stack.name

    val usedSpace get() = partitions.sumOf { it.usedSpace }
    val freeSpace get() = totalSpace - usedSpace
    val allocatedSpace get() = partitions.sumOf { it.size }
    val allocableSpace get() = totalSpace - allocatedSpace

    fun initialise(): DiskInfo {
        id = id ?: UUID.randomUUID()
        val parts = partitions
        for (partition in parts) {
            partition.id = partition.id ?: UUID.randomUUID()
        }
        return this
    }

    fun addPartition(partition: DiskPartition) { partitions = partitions.plus(partition) }

    fun removePartition(id: UUID) { partitions = partitions.filter { it.id != id } }

    fun removePartition(partition: DiskPartition) { removePartition(partition.id ?: return) }

    fun getShortId(disks: Iterable<DiskInfo>): String? {
        return getShortId(disks, id ?: return null)
    }

    companion object {
        fun getPartitions(disks: Iterable<DiskInfo>) = disks.flatMap { it.partitions }.toSet()

        fun getDisk(disks: Iterable<DiskInfo>, id: UUID) = disks.firstOrNull { it.id == id }

        fun getDiskWithPartition(disks: Iterable<DiskInfo>, id: UUID) = disks.firstOrNull { it.partitions.any { it.id == id } }

        fun getPartition(partitions: Iterable<DiskPartition>, id: UUID) = partitions.firstOrNull { it.id == id }

        fun findPartition(disks: Iterable<DiskInfo>, id: UUID) = disks.firstNotNullOfOrNull { it.partitions.firstOrNull { it.id == id } }

        fun getShortId(disks: Iterable<DiskInfo>, id: UUID): String {
            val me = id.toString().replace("-", "")
            val others = disks.mapNotNull { val i = it.id?.toString()?.replace("-", ""); if (i != me) i else null }.toSet()
            for (i in me.indices) {
                val meShort = me.substring(0, i + 1)
                if (others.none { it.startsWith(meShort) }) {
                    return meShort
                }
            }
            return me
        }
    }

}