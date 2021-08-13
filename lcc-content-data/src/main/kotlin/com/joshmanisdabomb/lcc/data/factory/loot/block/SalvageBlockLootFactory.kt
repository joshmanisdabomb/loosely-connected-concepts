package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.item.CrowbarItem
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.condition.TableBonusLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.predicate.item.ItemPredicate

open class SalvageBlockLootFactory(val salvageChance: FloatArray = floatArrayOf(0.3f, 0.4f, 0.5f, 0.6f)) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.lootTables.register(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(
                    ItemEntry.builder(entry)
                        .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, *salvageChance))
                        .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(CrowbarItem.salvage, true)))
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().item(LCCItems.crowbar)).invert())
                )
                .with(
                    ItemEntry.builder(entry)
                        .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(CrowbarItem.salvage, true)))
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().item(LCCItems.crowbar)))
                )
                .with(
                    ItemEntry.builder(entry)
                        .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(CrowbarItem.salvage, false)))
                        .conditionally(SurvivesExplosionLootCondition.builder())
                )
        ))
    }

    companion object : SalvageBlockLootFactory()

}
