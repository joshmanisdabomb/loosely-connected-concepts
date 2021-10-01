package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider

object SelfBlockLootFactory : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.acceptLootTable(entry, LootTable.builder().pool(
            LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(entry))
                .conditionally(SurvivesExplosionLootCondition.builder()))
        )
    }

}