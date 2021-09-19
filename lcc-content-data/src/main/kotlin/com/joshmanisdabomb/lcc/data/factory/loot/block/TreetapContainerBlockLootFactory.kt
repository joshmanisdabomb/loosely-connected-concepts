package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider

class TreetapContainerBlockLootFactory(val treetap: ItemConvertible, val container: ItemConvertible) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.lootTables.register(entry, LootTable.builder()
            .pool(
                LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(
                    ItemEntry.builder(treetap)
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
            )
            .pool(
                LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(
                    ItemEntry.builder(container)
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
            )
        )
    }

}