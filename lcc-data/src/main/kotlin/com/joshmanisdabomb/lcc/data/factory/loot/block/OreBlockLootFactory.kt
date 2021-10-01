package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import me.shedaniel.cloth.api.datagen.v1.LootTableData
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible

class OreBlockLootFactory(val ingot: ItemConvertible) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.acceptLootTable(entry, LootTableData.dropsSingleOreGem(entry, ingot))
    }

}