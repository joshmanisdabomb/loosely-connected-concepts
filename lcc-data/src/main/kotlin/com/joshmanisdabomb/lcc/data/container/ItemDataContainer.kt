package com.joshmanisdabomb.lcc.data.container

import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import net.minecraft.item.Item

class ItemDataContainer : DataContainer<Item, ItemDataFactory>() {

    override fun add(factory: ItemDataFactory) = super.add(factory).let { this }

    override fun apply(factory: ItemDataFactory, entry: Item) {
        factory.apply(LCCData.accessor, entry)
    }

}
