package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.abstracts.computing.info.DiskFolder
import com.joshmanisdabomb.lcc.component.ComputingStorageComponent

abstract class DataPartitionType<F> : PartitionType() {

    abstract fun readFile(storage: ComputingStorageComponent, filename: String, folder: DiskFolder): F?

    abstract fun writeFile(storage: ComputingStorageComponent, filename: String, folder: DiskFolder, contents: F): Boolean

}