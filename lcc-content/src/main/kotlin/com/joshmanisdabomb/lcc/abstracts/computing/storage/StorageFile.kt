package com.joshmanisdabomb.lcc.abstracts.computing.storage

import net.minecraft.nbt.NbtCompound
import java.util.*

data class StorageFile(val nbt: NbtCompound) : StorageSoftDivision {

    override val division = StorageDivision.StorageDivisionType.FILE

    override var id: UUID
        get() = nbt.getUuid("id")
        set(value) = nbt.putUuid("id", value)

    var folder: UUID
        get() = nbt.getUuid("folder")
        set(value) = nbt.putUuid("folder", value)

    override var name: String
        get() = nbt.getString("name")
        set(value) = nbt.putString("name", value)

    var contents: NbtCompound
        get() = nbt.getCompound("contents")
        set(value) = nbt.put("contents", value).let {}

    override var size: Int
        get() = nbt.getInt("size")
        set(value) = nbt.putInt("size", value)

    override var partition: UUID
        get() = nbt.getUuid("partition")
        set(value) = nbt.putUuid("partition", value)

    constructor(id: UUID, name: String) : this(NbtCompound()) {
        this.id = id
        this.name = name
    }

    fun writeNbt(nbt: NbtCompound) {
        nbt.putUuid("id", id)
        nbt.putString("name", name)
        nbt.putUuid("folder", folder)
        nbt.put("contents", contents)
        nbt.putInt("size", size)
        nbt.putUuid("partition", partition)
    }

}
