package com.joshmanisdabomb.lcc.abstracts.computing

import com.joshmanisdabomb.lcc.abstracts.computing.partition.PartitionType
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.NBT_INT
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import java.util.*

data class DiskPartition(var id: UUID?, var name: String, var type: PartitionType, var size: Int, var sector: Int? = null) {

    var usedCache = 0

    constructor(nbt: NbtCompound) : this(if (nbt.containsUuid("id")) nbt.getUuid("id") else null, nbt.getString("name"), LCCRegistries.computer_partitions[Identifier(nbt.getString("type"))]!!, nbt.getInt("size"), if (nbt.contains("sector", NBT_INT)) nbt.getInt("sector") else null) {
        usedCache = nbt.getInt("used_cache")
    }

    fun writeNbt(nbt: NbtCompound) {
        if (id != null) nbt.putUuid("id", id)
        nbt.putString("name", name)
        nbt.putString("type", type.id.toString())
        nbt.putInt("size", size)
        if (sector != null) nbt.putInt("sector", sector!!)
    }

    val usedSpace = type.useAllSpace.transformInt(size, usedCache)
    val freeSpace = size - usedSpace

}