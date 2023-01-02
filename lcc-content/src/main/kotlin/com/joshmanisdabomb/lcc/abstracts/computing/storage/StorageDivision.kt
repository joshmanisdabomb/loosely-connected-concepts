package com.joshmanisdabomb.lcc.abstracts.computing.storage

interface StorageDivision {

    val division: StorageDivisionType

    enum class StorageDivisionType(val filesystem: Boolean) {
        DISK(false),
        PARTITION(false),
        FOLDER(true),
        FILE(true)
    }

}