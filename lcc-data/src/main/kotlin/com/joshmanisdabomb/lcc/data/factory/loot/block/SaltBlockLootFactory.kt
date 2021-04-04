package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.AlternativeEntry
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.StatePredicate
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.state.property.Properties

object SaltBlockLootFactory : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.lootTables.register(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(AlternativeEntry.builder(*getEntries(data, entry)))
        ))
    }

    private fun getEntries(data: DataAccessor, entry: Block) = Properties.LEVEL_3.values.map {
        ItemEntry.builder(entry)
            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(it.toFloat())))
            .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1)))))
            .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(Properties.LEVEL_3, it)))
            .conditionally(SurvivesExplosionLootCondition.builder())
    }.toTypedArray()

}
