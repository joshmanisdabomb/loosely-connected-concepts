package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item
import net.minecraft.util.Identifier

open class GeneratedBlockItemAssetFactory(val texture: Identifier? = null) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        models.generated { texture ?: idh.loc(entry, "block") }.create(data, entry)
    }

    companion object : GeneratedBlockItemAssetFactory()

}