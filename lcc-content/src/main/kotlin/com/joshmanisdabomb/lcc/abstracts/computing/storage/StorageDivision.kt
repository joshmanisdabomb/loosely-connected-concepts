package com.joshmanisdabomb.lcc.abstracts.computing.storage

interface StorageDivision {

    val division: StorageDivisionType

    enum class StorageDivisionType(val filesystem: Boolean, val char: Char) {
        DISK(false, 'd'),
        PARTITION(false, 'p'),
        FOLDER(true, 'l'),
        FILE(true, 'f');
    }

}