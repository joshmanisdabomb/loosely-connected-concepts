package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageFile
import com.joshmanisdabomb.lcc.component.ComputingStorageComponent
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Formatting

class TextPartitionType : DataPartitionType<String>() {

    override val nameColor = Formatting.WHITE

    override fun readFromFile(storage: ComputingStorageComponent, file: StorageFile): String? {
        return null
    }

    override fun writeToFile(storage: ComputingStorageComponent, file: StorageFile, contents: String): Boolean {
        val nbt = NbtCompound()
        nbt.putString("Contents", contents)
        file.contents = nbt
        file.size = contents.length
        return true
    }

}