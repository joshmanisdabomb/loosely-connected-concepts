package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item
import net.minecraft.util.Identifier

open class GeneratedItemAssetFactory(val texture: Identifier? = null) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        modelGenerated(data, entry, texture = texture ?: loc(entry))
    }

    companion object : GeneratedItemAssetFactory()

}