package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.StringIdentifiable

class TreetapBlockLootFactory<V1, V2>(val treetap: ItemConvertible, val container: EnumProperty<V1>, val containerGetter: (V1) -> ItemConvertible?, val liquid: EnumProperty<V2>? = null, val liquidGetter: ((V2) -> ItemConvertible?)? = null) : BlockDataFactory where V1 : Enum<V1>, V2 : Enum<V2>, V1 : StringIdentifiable, V2 : StringIdentifiable {

    override fun apply(data: DataAccessor, entry: Block) {
        data.lootTables.register(entry, LootTable.builder()
            .pool(
                LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(treetap).conditionally(SurvivesExplosionLootCondition.builder()))
            ).also {
                container.values.forEach { c ->
                    val item = containerGetter(c) ?: return@forEach
                    it.pool(
                        LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0f))
                            .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(container, c)))
                            .with(ItemEntry.builder(item).conditionally(SurvivesExplosionLootCondition.builder()))
                    )
                }
                liquid?.values?.forEach { l ->
                    if (liquidGetter == null) return@also
                    val item = liquidGetter.invoke(l) ?: return@forEach
                    it.pool(
                        LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0f))
                            .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(liquid, l)))
                            .with(ItemEntry.builder(item).conditionally(SurvivesExplosionLootCondition.builder()))
                    )
                }
            }
        )
    }

}