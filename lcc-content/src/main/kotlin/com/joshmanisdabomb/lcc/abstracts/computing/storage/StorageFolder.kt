package com.joshmanisdabomb.lcc.abstracts.computing.storage

import com.joshmanisdabomb.lcc.extensions.getStringUuidList
import com.joshmanisdabomb.lcc.extensions.putStringUuidList
import net.minecraft.nbt.NbtCompound
import java.util.*

data class StorageFolder(val nbt: NbtCompound) : StorageDivision {

    override val division = StorageDivision.StorageDivisionType.FOLDER

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

    constructor(id: UUID, name: String) : this(NbtCompound()) {
        this.id = id
        this.name = name
    }

    fun writeNbt(nbt: NbtCompound) {
        nbt.putUuid("id", id)
        nbt.putString("name", name)
        nbt.putStringUuidList("path", path)
        nbt.putStringUuidList("folders", folders)
        nbt.putStringUuidList("files", files)
        nbt.putInt("used_cache", usedCache)
    }

}
