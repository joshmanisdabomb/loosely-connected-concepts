package com.joshmanisdabomb.lcc.data.factory.asset.item

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.data.factory.ItemDataFactory
import com.joshmanisdabomb.lcc.data.factory.asset.AssetFactory
import com.joshmanisdabomb.lcc.data.factory.asset.ModelProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.Items

interface ItemAssetFactory : BlockDataFactory, ItemDataFactory, AssetFactory<Item> {

    override val models get() = ModelProvider.item

    override fun apply(data: DataAccessor, entry: Block) {
        val item = entry.asItem()
        if (item == Items.AIR) return
        apply(data, item)
    }

}