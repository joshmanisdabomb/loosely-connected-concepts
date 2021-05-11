package com.joshmanisdabomb.lcc.data.factory.asset

interface ModelAccess {

    val mb get() = ModelProvider.block
    val idb get() = mb.idh
    val mi get() = ModelProvider.item
    val idi get() = mi.idh

}