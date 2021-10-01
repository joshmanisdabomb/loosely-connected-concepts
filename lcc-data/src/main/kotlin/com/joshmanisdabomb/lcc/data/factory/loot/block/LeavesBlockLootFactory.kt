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
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider

class LeavesBlockLootFactory(val sapling: ItemConvertible? = null, val apple: ItemConvertible? = null, val stick: ItemConvertible? = null, val saplingChance: FloatArray = floatArrayOf(0.05F, 0.0625F, 0.0833F, 0.1F), val stickChance: FloatArray = floatArrayOf(0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f), val appleChance: FloatArray = floatArrayOf(0.005f, 0.0055555557f, 0.00625f, 0.008333334f, 0.025f)) : BlockDataFactory {

    override fun apply(data: DataAccessor, entry: Block) {
        val builder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1f))

        if (sapling != null) builder.with(ItemEntry.builder(entry)
            .conditionally(DataUtils.silk_touch_or_shears)
            .alternatively(ItemEntry.builder(sapling)
                .conditionally(SurvivesExplosionLootCondition.builder())
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, *saplingChance))
            )
        )

        if (stick != null) builder.with(ItemEntry.builder(entry)
            .conditionally(DataUtils.not_silk_touch_or_shears)
            .alternatively(ItemEntry.builder(stick)
                .conditionally(SurvivesExplosionLootCondition.builder())
                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)))
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, *stickChance))
            )
        )

        if (apple != null) builder.with(ItemEntry.builder(apple)
            .conditionally(DataUtils.not_silk_touch_or_shears)
            .alternatively(ItemEntry.builder(apple)
                .conditionally(SurvivesExplosionLootCondition.builder())
                .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, *appleChance))
            )
        )

        data.acceptLootTable(entry, LootTable.builder().pool(builder))
    }

}