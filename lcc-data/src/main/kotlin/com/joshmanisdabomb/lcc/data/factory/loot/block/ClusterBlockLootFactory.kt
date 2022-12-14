package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.ApplyBonusLootFunction
import net.minecraft.loot.function.ExplosionDecayLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.util.registry.Registry

class ClusterBlockLootFactory(val crystal: ItemConvertible) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (Registry.BLOCK.getId(entry).path.endsWith("_cluster")) {
            data.lootTables.addBlock(entry, LootTable.builder().pool(
                LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(entry)
                        .conditionally(DataUtils.silk_touch)
                        .alternatively(ItemEntry.builder(crystal)
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4f)))
                            .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                            .apply(ExplosionDecayLootFunction.builder())))))
        } else {
            data.lootTables.addBlock(entry, LootTable.builder().pool(
                LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(entry)
                        .conditionally(DataUtils.silk_touch))))
        }
    }

}
