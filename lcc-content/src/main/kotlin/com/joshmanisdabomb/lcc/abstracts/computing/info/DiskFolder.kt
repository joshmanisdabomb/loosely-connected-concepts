package com.joshmanisdabomb.lcc.abstracts.computing.info

import com.joshmanisdabomb.lcc.extensions.getStringUuidList
import com.joshmanisdabomb.lcc.extensions.putStringUuidList
import net.minecraft.nbt.NbtCompound
import java.util.*

data class DiskFolder(val nbt: NbtCompound) {

    var id: UUID
        get() = nbt.getUuid("id")
        set(value) = nbt.putUuid("id", value)

    var path: List<UUID>
        get() = nbt.getStringUuidList("path")
        set(value) = nbt.putStringUuidList("path", value).let {}

    var name: String
        get() = nbt.getString("name")
        set(value) = nbt.putString("name", value)

    var folders: List<UUID>
        get() = nbt.getStringUuidList("folders")
        set(value) = nbt.putStringUuidList("folders", value).let {}

    var files: List<UUID>
        get() = nbt.getStringUuidList("files")
        set(value) = nbt.putStringUuidList("files", value).let {}

    var usedCache: Int
        get() = nbt.getInt("used_cache")
        set(value) = nbt.putInt("used_cache", value)

    constructor(id: UUID, path: List<UUID>, name: String) : this(NbtCompound()) {
        this.id = id
        this.path = path
        this.name = name
    }

    fun writeNbt(nbt: NbtCompound) {
        nbt.putUuid("id", id)
        nbt.putString("name", name)
        nbt.putStringUuidList("path", path)
    }

}
