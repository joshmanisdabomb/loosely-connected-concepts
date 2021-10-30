package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.ExplosionDecayLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.LootNumberProvider

open class SilkBlockLootFactory(val or: ItemConvertible? = null, val count: LootNumberProvider = ConstantLootNumberProvider.create(1.0f)) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (or != null) {
            data.lootTables.addBlock(entry, LootTable.builder().pool(
                LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(entry)
                        .conditionally(DataUtils.silk_touch)
                        .alternatively(ItemEntry.builder(or)
                            .apply(SetCountLootFunction.builder(count))
                            .apply(ExplosionDecayLootFunction.builder())))))
        } else {
            data.lootTables.addBlock(entry, LootTable.builder().pool(
                LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(entry)
                        .conditionally(DataUtils.silk_touch))))
        }
    }

    companion object : SilkBlockLootFactory()

}