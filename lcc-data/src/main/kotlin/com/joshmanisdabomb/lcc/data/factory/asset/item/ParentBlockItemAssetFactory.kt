package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item
import net.minecraft.util.Identifier

open class ParentBlockItemAssetFactory(val parent: Identifier? = null) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        models.parent(parent ?: idh.loc(entry, "block")).create(data, entry)
    }

    companion object : ParentBlockItemAssetFactory()

}
