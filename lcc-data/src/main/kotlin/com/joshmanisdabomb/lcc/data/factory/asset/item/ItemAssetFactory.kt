package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import com.joshmanisdabomb.lcc.data.factory.asset.AssetFactory
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

interface ItemAssetFactory : BlockDataFactory, ItemDataFactory, AssetFactory<Item> {

    override val defaultFolder get() = "item"

    override fun apply(data: DataAccessor, entry: Block) {
        apply(data, entry.asItem())
    }

    override fun apply(data: DataAccessor, entry: Item) {

    }

    override fun registry(obj: Item) = Registry.ITEM.getId(obj)

}