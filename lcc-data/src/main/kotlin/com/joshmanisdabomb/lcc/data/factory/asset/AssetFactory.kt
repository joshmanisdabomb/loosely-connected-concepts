package com.joshmanisdabomb.lcc.data.factory.asset

interface AssetFactory<T> {

    val models: ModelProvider<T>
    val idh get() = models.idh

}
