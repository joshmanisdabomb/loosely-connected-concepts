package com.joshmanisdabomb.lcc.abstracts.computing.info

import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.item.DigitalMediumItem
import net.minecraft.inventory.Inventories.writeNbt
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import java.util.*

data class DiskInfo(val stack: ItemStack) {

    val tag get() = stack.getSubNbt("lcc-computing")
    private val _tag get() = stack.getOrCreateSubNbt("lcc-computing")

    val medium = stack.item as DigitalMediumItem
    val totalSpace = medium.getLevel(stack)

    var id: UUID?
        get() = tag?.getUuidOrNull("id")
        set(value) = if (value != null) _tag.putUuid("id", value) else _tag.remove("id")

    var label: String?
        get() = tag?.getStringOrNull("name")
        set(value) = _tag.putString("name", value)

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

    fun addPartition(partition: DiskPartition) {
        partitions = partitions.plus(partition)
    }

    fun getShortId(disks: Iterable<DiskInfo>): String? {
        val me = id?.toString()?.replace("-", "") ?: return null
        val others = disks.mapNotNull { val id = it.id?.toString()?.replace("-", ""); if (id != me) id else null }.toSet()
        for (i in me.indices) {
            val meShort = me.substring(0, i + 1)
            if (others.none { it.startsWith(meShort) }) {
                return meShort
            }
        }
        return me
    }

}