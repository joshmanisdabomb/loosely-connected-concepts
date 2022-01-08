package com.joshmanisdabomb.lcc.abstracts.computing.info

import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.getUuidOrNull
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import java.util.*

class DiskPartition(val nbt: NbtCompound) {

    var id: UUID?
        get() = nbt.getUuidOrNull("id")
        set(value) = if (value != null) nbt.putUuid("id", value) else nbt.remove("id")

    var label: String
        get() = nbt.getString("name")
        set(value) = nbt.putString("name", value)

    var type: PartitionType
        get() = LCCRegistries.computer_partitions[Identifier(nbt.getString("type"))]!!
        set(value) = nbt.putString("type", value.id.toString())

    var size: Int
        get() = nbt.getInt("size")
        set(value) = nbt.putInt("size", value)

    var usedCache: Int
        get() = nbt.getInt("used_cache")
        set(value) = nbt.putInt("used_cache", value)

    val usedSpace get() = type.noFreeSpace.transformInt(size, usedCache)
    val freeSpace get() = size - usedSpace

    constructor(id: UUID?, name: String, type: PartitionType, size: Int) : this(NbtCompound()) {
        this.id = id
        this.label = name
        this.type = type
        this.size = size
    }

    fun writeNbt(nbt: NbtCompound) {
        if (id != null) nbt.putUuid("id", id)
        nbt.putString("name", label)
        nbt.putString("type", type.id.toString())
        nbt.putInt("size", size)
    }

    fun getShortId(disks: Iterable<DiskInfo>): String? {
        val me = id?.toString()?.replace("-", "") ?: return null
        val others = disks.flatMap { it.partitions }.mapNotNull { val id = it.id?.toString()?.replace("-", ""); if (id != me) id else null }.toSet()
        for (i in me.indices) {
            val meShort = me.substring(0, i + 1)
            if (others.none { it.startsWith(meShort) }) {
                return meShort
            }
        }
        return me
    }

}