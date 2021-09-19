package com.joshmanisdabomb.lcc.data.factory.loot.block

import com.joshmanisdabomb.lcc.data.DataAccessor
import com.joshmanisdabomb.lcc.data.factory.BlockDataFactory
import me.shedaniel.cloth.api.datagen.v1.LootTableData
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.ItemConvertible
import net.minecraft.loot.condition.SurvivesExplosionLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.ApplyBonusLootFunction
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.LootNumberProvider

class GenerousOreBlockLootFactory(val ingot: ItemConvertible, val range: LootNumberProvider) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        data.lootTables.register(entry, LootTableData.dropsBlockWithSilkTouch(entry,
            ItemEntry.builder(ingot)
                .apply(SetCountLootFunction.builder(range))
                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                .conditionally(SurvivesExplosionLootCondition.builder()))
        )
    }

}