package com.joshmanisdabomb.lcc.abstracts.computing.info

import net.minecraft.nbt.NbtCompound
import java.util.*

data class DiskFile(val nbt: NbtCompound) {

    var id: UUID
        get() = nbt.getUuid("id")
        set(value) = nbt.putUuid("id", value)

    var folder: UUID
        get() = nbt.getUuid("folder")
        set(value) = nbt.putUuid("folder", value)

    var name: String
        get() = nbt.getString("name")
        set(value) = nbt.putString("name", value)

    var contents: NbtCompound
        get() = nbt.getCompound("contents")
        set(value) = nbt.put("contents", value).let {}

    var size: Int
        get() = nbt.getInt("size")
        set(value) = nbt.putInt("size", value)

    constructor(id: UUID, folder: UUID, name: String, contents: NbtCompound, size: Int) : this(NbtCompound()) {
        this.id = id
        this.folder = folder
        this.name = name
        this.contents = contents
        this.size = size
    }

    fun writeNbt(nbt: NbtCompound) {
        nbt.putUuid("id", id)
        nbt.putString("name", name)
        nbt.putUuid("folder", folder)
        nbt.put("contents", contents)
        nbt.putInt("size", size)
    }

}
