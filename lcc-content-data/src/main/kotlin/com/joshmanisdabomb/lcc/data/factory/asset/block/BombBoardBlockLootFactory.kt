package com.joshmanisdabomb.lcc.data.factory.asset.block

import com.joshmanisdabomb.lcc.block.BombBoardBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate

object BombBoardBlockLootFactory : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.lootTables.register(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(ItemEntry.builder(entry)
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
        ).pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(ItemEntry.builder(LCCBlocks.explosive_paste)
                    .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(BombBoardBlock.mine_state, BombBoardBlock.mine)))
                    .conditionally(DataUtils.silk_touch)
                )
        ))
    }

}
