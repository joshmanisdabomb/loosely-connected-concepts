package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import net.minecraft.item.Item
import net.minecraft.util.Identifier

class CustomItemAssetFactory(val model: CustomItemAssetFactory.(data: DataAccessor, entry: Item) -> Identifier) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        model(data, entry)
    }

}