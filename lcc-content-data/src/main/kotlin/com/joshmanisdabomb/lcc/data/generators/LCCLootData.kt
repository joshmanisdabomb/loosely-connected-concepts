package com.joshmanisdabomb.lcc.data.generators

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCEnchants
import com.joshmanisdabomb.lcc.directory.LCCItems
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.EnchantRandomlyLootFunction
import net.minecraft.loot.function.EnchantWithLevelsLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.function.SetDamageLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider

object LCCLootData {

    val loot get() = LCCData.lootTables

    fun init() {
        loot.add(LootContextTypes.CHEST, LCC.id("chests/tent"), LootTable.builder()
            .pool(LootPool.builder()
                .rolls(UniformLootNumberProvider.create(2.0f, 8.0f))
                .with(ItemEntry.builder(Items.CHARCOAL).weight(12)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f))))
                .with(ItemEntry.builder(Items.TORCH).weight(12)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f))))
                .with(ItemEntry.builder(Items.STICK).weight(9)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 5.0f))))
                .with(ItemEntry.builder(LCCItems.heart_full[HeartType.RED]).weight(5)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))))
                .with(ItemEntry.builder(LCCBlocks.deadwood_planks).weight(5)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 5.0f))))
                .with(ItemEntry.builder(LCCItems.iron_oxide_nugget).weight(2)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 19.0f))))
                .with(ItemEntry.builder(LCCItems.dull_sapphire).weight(5)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
            )
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(LCCItems.heart_full[HeartType.RED])
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))))
            )
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(2.0f))
                .with(ItemEntry.builder(LCCItems.deadwood_sword).weight(2)
                    .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.6F)).conditionally(RandomChanceLootCondition.builder(0.9f)))
                    .apply(EnchantWithLevelsLootFunction.builder(UniformLootNumberProvider.create(10.0f, 20.0f)).conditionally(RandomChanceLootCondition.builder(0.3f)))
                    .apply(EnchantRandomlyLootFunction.Builder().add(LCCEnchants.infested).conditionally(RandomChanceLootCondition.builder(0.4f))))
                .with(ItemEntry.builder(LCCItems.deadwood_pickaxe).weight(2)
                    .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.6F)).conditionally(RandomChanceLootCondition.builder(0.9f)))
                    .apply(EnchantWithLevelsLootFunction.builder(UniformLootNumberProvider.create(10.0f, 20.0f)).conditionally(RandomChanceLootCondition.builder(0.3f))))
                .also { l ->
                    arrayOf(LCCItems.deadwood_shovel, LCCItems.deadwood_axe, LCCItems.deadwood_hoe).forEach {
                        l.with(ItemEntry.builder(it)
                            .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.6F)).conditionally(RandomChanceLootCondition.builder(0.9f)))
                            .apply(EnchantWithLevelsLootFunction.builder(UniformLootNumberProvider.create(10.0f, 20.0f)).conditionally(RandomChanceLootCondition.builder(0.3f))))
                    }
                }
            )
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(2.0f))
                .with(ItemEntry.builder(LCCItems.woodlouse_shell).weight(4)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                .also { l ->
                    arrayOf(LCCItems.woodlouse_helmet, LCCItems.woodlouse_chestplate, LCCItems.woodlouse_leggings, LCCItems.woodlouse_boots).forEach {
                        l.with(ItemEntry.builder(it)
                            .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.6F)).conditionally(RandomChanceLootCondition.builder(0.9f)))
                            .apply(EnchantWithLevelsLootFunction.builder(UniformLootNumberProvider.create(10.0f, 20.0f)).conditionally(RandomChanceLootCondition.builder(0.3f))))
                    }
                }
            )
            .pool(LootPool.builder()
                .rolls(UniformLootNumberProvider.create(0.0f, 1.0f))
                .with(ItemEntry.builder(LCCItems.heart_container[HeartType.RED]).weight(3))
                .with(ItemEntry.builder(LCCItems.heart_container[HeartType.IRON]).weight(1))
                .with(ItemEntry.builder(LCCItems.altar_challenge_key).weight(3))
                .with(ItemEntry.builder(LCCItems.crowbar).weight(2)
                    .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.6F))))
                .with(ItemEntry.builder(Items.BOOK).weight(3)
                    .apply(EnchantRandomlyLootFunction.Builder().add(LCCEnchants.infested)))
            )
        )
    }

}
