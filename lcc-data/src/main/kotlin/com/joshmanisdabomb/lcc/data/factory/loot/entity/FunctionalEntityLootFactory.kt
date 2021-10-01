package com.joshmanisdabomb.lcc.data.factory.loot.entity

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import net.minecraft.entity.EntityType
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.entry.LeafEntry
import net.minecraft.loot.function.LootingEnchantLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider

class FunctionalEntityLootFactory(val map: Map<ItemConvertible, (LeafEntry.Builder<*>.() -> Unit)?>) : EntityDataFactory {

    override fun apply(data: DataAccessor, entry: EntityType<*>) {
        data.acceptLootTable(entry, LootTable.builder().also {
            map.forEach { (k, v) ->
                val func = v ?: { apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))); apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))); Unit }
                it.pool(LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0f))
                    .with(ItemEntry.builder(k).apply(func))
                )
            }
        })
    }

}