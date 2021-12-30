package com.joshmanisdabomb.lcc.abstracts.computing

import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.extensions.getCompoundList
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import java.util.*

data class DiskInfo(val stack: ItemStack) {

    val tag get() = stack.getSubNbt("lcc-computing")
    private val _tag get() = stack.getOrCreateSubNbt("lcc-computing")

    var id: UUID?
        get() = if (tag?.containsUuid("id") == true) tag?.getUuid("id") else null
        set(value) = _tag.putUuid("id", value)

    var label: String?
        get() = if (tag?.contains("name", NBT_STRING) == true) tag?.getString("name") else null
        set(value) = _tag.putString("name", value)

    var partitions: List<DiskPartition>
        get() = tag?.getCompoundList("partitions")?.map(::DiskPartition) ?: emptyList()
        set(value) {
            val list = NbtList()
            value.forEach { val nbt = NbtCompound(); it.writeNbt(nbt); list.add(nbt) }
            stack.getOrCreateSubNbt("lcc-computing").put("partitions", list)
        }

    val usedSpace get() = partitions.sumOf { it.usedSpace }
    val allocatedSpace get() = partitions.sumOf { it.size }

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

}