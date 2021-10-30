package com.joshmanisdabomb.lcc.data.factory.loot.entity

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.EntityDataFactory
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.suffix
import net.minecraft.entity.EntityType
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.KilledByPlayerLootCondition
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.LootingEnchantLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider

object ConsumerEntityLootFactory : EntityDataFactory {

    override fun apply(data: DataAccessor, entry: EntityType<*>) {
        data.lootTables.addEntity(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(ItemEntry.builder(Items.CHARCOAL)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                    .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                )
        ))
        data.lootTables.add(LootContextTypes.ENTITY, entry.lootTableId.suffix("tongue"), LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(ItemEntry.builder(LCCItems.tongue_tissue)
                    .conditionally(KilledByPlayerLootCondition.builder())
                    .conditionally(RandomChanceWithLootingLootCondition.builder(0.08F, 0.04F))
                )
        ))
    }

}
