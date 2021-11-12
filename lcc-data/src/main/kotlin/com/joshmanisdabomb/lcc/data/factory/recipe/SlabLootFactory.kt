package com.joshmanisdabomb.lcc.data.factory.recipe

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.asset.block.BlockAssetFactory
import net.minecraft.block.Block
import net.minecraft.block.SlabBlock
import net.minecraft.block.enums.SlabType
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate

object SlabLootFactory : BlockAssetFactory {

    override fun apply(data: DataAccessor, entry: Block) {
<<<<<<< HEAD
        data.acceptLootTable(entry, LootTable.builder().pool(
=======
        data.lootTables.addBlock(entry, LootTable.builder().pool(
>>>>>>> fabric-1.18
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(
                    ItemEntry.builder(entry)
                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F)).conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))))
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
        ))
    }

}
