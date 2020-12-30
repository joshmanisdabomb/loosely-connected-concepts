package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item
import net.minecraft.util.Identifier

open class ParentBlockItemAssetFactory(val parent: Identifier? = null) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        modelParent(data, entry, parent ?: loc(entry, "block"))
    }

    companion object : ParentBlockItemAssetFactory()

}
