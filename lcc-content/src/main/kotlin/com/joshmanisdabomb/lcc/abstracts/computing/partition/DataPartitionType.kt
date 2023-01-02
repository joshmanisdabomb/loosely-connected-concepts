package com.joshmanisdabomb.lcc.abstracts.computing.partition

import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageFile
import com.joshmanisdabomb.lcc.component.ComputingStorageComponent

abstract class DataPartitionType<F> : PartitionType() {

    abstract fun readFromFile(storage: ComputingStorageComponent, file: StorageFile): F?

    abstract fun writeToFile(storage: ComputingStorageComponent, file: StorageFile, contents: F): Boolean

}