package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item
import net.minecraft.util.Identifier

open class HandheldItemAssetFactory(val texture: Identifier? = null) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        models.handheld { texture }.create(data, entry)
    }

    companion object : HandheldItemAssetFactory()

}