package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.block.NetherReactorBlock
import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.EnchantWithLevelsLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.util.Identifier

object NetherReactorBlockLootFactory : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.acceptLootTable(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1f))
                .with(
                    ItemEntry.builder(entry)
                    .conditionally(BlockStatePropertyLootCondition.builder(entry).properties(StatePredicate.Builder.create().exactMatch(NetherReactorBlock.reactor_state, NetherReactorBlock.NetherReactorState.READY)))
                    .conditionally(SurvivesExplosionLootCondition.builder())
                )
        ))
        data.acceptLootTable(
            LootContextTypes.EMPTY, Identifier("lcc", "gameplay/nether_reactor"), LootTable.builder().pool(
            LootPool.builder()
                .rolls(UniformLootNumberProvider.create(20.0f, 40.0f))
                .with(ItemEntry.builder(Items.GLOWSTONE_DUST).weight(100))
                .with(ItemEntry.builder(Items.QUARTZ).weight(70))
                .with(ItemEntry.builder(Blocks.SOUL_SAND).weight(30))
                .with(ItemEntry.builder(Blocks.BROWN_MUSHROOM).weight(18))
                .with(ItemEntry.builder(Blocks.RED_MUSHROOM).weight(18))
                .with(ItemEntry.builder(Items.SUGAR_CANE).weight(24))
                .with(ItemEntry.builder(Blocks.CACTUS).weight(24))
                .with(ItemEntry.builder(Items.WHEAT_SEEDS).weight(4))
                .with(ItemEntry.builder(Items.MELON_SEEDS).weight(4))
                .with(ItemEntry.builder(Items.PUMPKIN_SEEDS).weight(4))
                .with(ItemEntry.builder(Items.BEETROOT_SEEDS).weight(4))
                .with(ItemEntry.builder(Items.CARROT).weight(4))
                .with(ItemEntry.builder(Items.POTATO).weight(4))
                .with(ItemEntry.builder(Items.COCOA_BEANS).weight(2))
                .with(ItemEntry.builder(Items.BOOK).weight(6))
                .with(ItemEntry.builder(Items.ARROW).weight(8))
                .with(ItemEntry.builder(Items.OAK_SAPLING).weight(2))
                .with(ItemEntry.builder(Items.SPRUCE_SAPLING).weight(2))
                .with(ItemEntry.builder(Items.BIRCH_SAPLING).weight(2))
                .with(ItemEntry.builder(Items.JUNGLE_SAPLING).weight(2))
                .with(ItemEntry.builder(Items.ACACIA_SAPLING).weight(2))
                .with(ItemEntry.builder(Items.DARK_OAK_SAPLING).weight(2))
                .with(ItemEntry.builder(Items.OAK_DOOR).weight(1))
                .with(ItemEntry.builder(Items.SPRUCE_DOOR).weight(1))
                .with(ItemEntry.builder(Items.BIRCH_DOOR).weight(1))
                .with(ItemEntry.builder(Items.JUNGLE_DOOR).weight(1))
                .with(ItemEntry.builder(Items.ACACIA_DOOR).weight(1))
                .with(ItemEntry.builder(Items.DARK_OAK_DOOR).weight(1))
                .with(ItemEntry.builder(Items.RED_BED).weight(2))
                .with(ItemEntry.builder(Items.PAINTING).weight(2))
                .with(ItemEntry.builder(Items.MAP).weight(2))
                .with(ItemEntry.builder(Items.BOW).weight(1))
                .with(ItemEntry.builder(Items.BOW).apply(EnchantWithLevelsLootFunction.builder(UniformLootNumberProvider.create(7.0F, 30.0f))).weight(1))
        ))
    }

}