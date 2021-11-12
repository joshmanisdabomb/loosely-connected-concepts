package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.FlowerPotBlock
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider

object PottedPlantBlockLootFactory : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        if (entry !is FlowerPotBlock) return
        data.lootTables.addBlock(entry, LootTable.builder()
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(Blocks.FLOWER_POT)
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
            )
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(entry.content)
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
            )
        )
    }

}
