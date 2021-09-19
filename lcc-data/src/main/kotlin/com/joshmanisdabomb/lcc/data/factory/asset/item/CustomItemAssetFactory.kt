package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.item.Item

class CustomItemAssetFactory(val model: ModelProvider.ModelFactory<Item>) : ItemAssetFactory {

    override fun apply(data: DataAccessor, entry: Item) {
        model.create(data, entry)
    }

}