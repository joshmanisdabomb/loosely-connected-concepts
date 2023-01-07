package com.joshmanisdabomb.lcc.abstracts.computing.storage

import java.util.*

interface StorageSoftDivision : StorageDivision {

    var id: UUID
    var name: String
    var size: Int
    var partition: UUID

}