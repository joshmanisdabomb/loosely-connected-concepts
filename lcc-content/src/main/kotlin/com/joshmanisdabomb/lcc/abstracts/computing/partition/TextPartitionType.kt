package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskFolder
import com.joshmanisdabomb.lcc.component.ComputingStorageComponent
import net.minecraft.util.Formatting

class TextPartitionType : DataPartitionType<String>() {

    override val nameColor = Formatting.WHITE

    override fun readFile(storage: ComputingStorageComponent, filename: String, folder: DiskFolder): String? {
        return null
    }

    override fun writeFile(storage: ComputingStorageComponent, filename: String, folder: DiskFolder, contents: String): Boolean {
        return false
    }

}