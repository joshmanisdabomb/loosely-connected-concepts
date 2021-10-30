package com.joshmanisdabomb.lcc.data.factory.loot.entity

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.LootingEnchantLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider

class ClassicEntityLootFactory(val drop: Item) : EntityDataFactory {

    override fun apply(data: DataAccessor, entry: EntityType<*>) {
        data.lootTables.addEntity(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(ItemEntry.builder(drop)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f)))
                    .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 0.5f)))
                )
        ))
    }

}
