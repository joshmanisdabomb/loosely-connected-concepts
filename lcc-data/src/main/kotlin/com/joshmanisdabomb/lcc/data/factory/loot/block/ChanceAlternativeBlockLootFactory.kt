package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.DataUtils
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.condition.TableBonusLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider

class ChanceAlternativeBlockLootFactory(val other: ItemConvertible, vararg val chances: Float = floatArrayOf(0.1f, 0.14285715f, 0.25f, 1.0f)) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.lootTables.addBlock(entry, LootTable.builder().pool(
            LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0f))
                .with(ItemEntry.builder(entry)
                    .conditionally(DataUtils.silk_touch)
                    .alternatively(ItemEntry.builder(other)
                        .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, *chances))
                        .alternatively(ItemEntry.builder(entry))
                        .conditionally(SurvivesExplosionLootCondition.builder())))))
    }

}
