package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.state.property.Properties

object DungeonTableBlockLootFactory : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.acceptLootTable(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(
                    ItemEntry.builder(entry)
                    .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(Properties.BOTTOM, false)))
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
        ))
    }

}