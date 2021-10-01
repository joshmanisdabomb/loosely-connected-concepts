package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import me.shedaniel.cloth.api.datagen.v1.LootTableData
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.condition.TableBonusLootCondition
import net.minecraft.loot.entry.ItemEntry

class ChanceAlternativeBlockLootFactory(val other: ItemConvertible, vararg val chances: Float) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.acceptLootTable(entry, LootTableData.dropsBlockWithSilkTouch(entry,
            ItemEntry.builder(other)
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.1f, 0.14285715f, 0.25f, 1.0f))
                .alternatively(ItemEntry.builder(entry))
                .conditionally(SurvivesExplosionLootCondition.builder()))
        )
    }

}
