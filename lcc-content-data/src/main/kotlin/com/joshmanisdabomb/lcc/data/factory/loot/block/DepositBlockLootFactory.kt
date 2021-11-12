package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.condition.TableBonusLootCondition
import net.minecraft.loot.entry.ItemEntry

object DepositBlockLootFactory : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        when (entry) {
            LCCBlocks.deposit -> {
                data.lootTables.addBlock(entry, LootTable.builder().pool(
                    LootPool.builder()
                        .with(ItemEntry.builder(Items.GOLD_NUGGET).weight(40))
                        .with(ItemEntry.builder(Items.IRON_NUGGET).weight(20))
                        .with(ItemEntry.builder(Items.RAW_GOLD).weight(8))
                        .with(ItemEntry.builder(Items.RAW_IRON).weight(6))
                        .with(ItemEntry.builder(LCCItems.heavy_uranium_nugget).weight(8))
                        .with(ItemEntry.builder(LCCItems.heart_half[HeartType.RED]).weight(25))
                        .with(ItemEntry.builder(LCCItems.heart_full[HeartType.RED]).weight(15))
                        .with(ItemEntry.builder(LCCItems.heart_container[HeartType.RED]).weight(4))
                        .with(ItemEntry.builder(LCCItems.heart_half[HeartType.IRON]).weight(5))
                        .with(ItemEntry.builder(LCCItems.heart_full[HeartType.IRON]).weight(4))
                        .with(ItemEntry.builder(LCCItems.heart_container[HeartType.IRON]).weight(1))
                    .conditionally(SurvivesExplosionLootCondition.builder())
                    .conditionally(DataUtils.no_silk_touch)
                    .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.15F, 0.2F, 0.3F, 0.4F))
                ).pool(
                    LootPool.builder().with(ItemEntry.builder(entry).conditionally(DataUtils.silk_touch))
                ))
            }
            LCCBlocks.infested_deposit -> {

            }
        }
    }

}
