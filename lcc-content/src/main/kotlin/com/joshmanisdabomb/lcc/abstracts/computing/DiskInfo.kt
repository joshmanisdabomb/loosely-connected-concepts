package com.joshmanisdabomb.lcc.abstracts.computing

import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.NBT_INT
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.extensions.getCompoundList
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier
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

    inner class DiskPartition(val nbt: NbtCompound) {

        var id: UUID?
            get() = if (nbt.containsUuid("id")) nbt.getUuid("id") else null
            set(value) = if (value != null) nbt.putUuid("id", value) else Unit

        var label: String
            get() = nbt.getString("name")
            set(value) = nbt.putString("name", value)

        var type: PartitionType
            get() = LCCRegistries.computer_partitions[Identifier(nbt.getString("type"))]!!
            set(value) = nbt.putString("type", value.id.toString())

        var size: Int
            get() = nbt.getInt("size")
            set(value) = nbt.putInt("size", value)

        var sector: Int?
            get() = if (nbt.contains("sector", NBT_INT)) nbt.getInt("sector") else null
            set(value) = if (value == null) nbt.remove("sector") else nbt.putInt("sector", value)

        var usedCache: Int
            get() = nbt.getInt("used_cache")
            set(value) = nbt.putInt("used_cache", value)

        val usedSpace get() = type.useAllSpace.transformInt(size, usedCache)
        val freeSpace get() = size - usedSpace

        constructor(id: UUID?, name: String, type: PartitionType, size: Int, sector: Int? = null) : this(NbtCompound()) {
            this.id = id
            this.label = name
            this.type = type
            this.size = size
            this.sector = sector
        }

        fun writeNbt(nbt: NbtCompound) {
            if (id != null) nbt.putUuid("id", id)
            nbt.putString("name", label)
            nbt.putString("type", type.id.toString())
            nbt.putInt("size", size)
            if (sector != null) nbt.putInt("sector", sector!!)
        }

        fun append() { partitions = partitions.plus(this) }

    }

}