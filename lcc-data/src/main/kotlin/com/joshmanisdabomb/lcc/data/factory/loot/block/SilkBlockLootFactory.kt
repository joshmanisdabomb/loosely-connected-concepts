package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import me.shedaniel.cloth.api.datagen.v1.LootTableData
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.LootNumberProvider

open class SilkBlockLootFactory(val or: ItemConvertible? = null, val count: LootNumberProvider = ConstantLootNumberProvider.create(1.0f)) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (or != null) data.lootTables.register(entry, LootTableData.dropsSilkBlockAndNormalItem(entry, or, count))
        else data.lootTables.register(entry, LootTableData.dropsBlockWithSilkTouch(entry))
    }

    companion object : SilkBlockLootFactory()

}