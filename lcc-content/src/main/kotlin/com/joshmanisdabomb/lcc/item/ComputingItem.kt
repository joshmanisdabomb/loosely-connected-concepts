package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.TooltipConstants
import com.joshmanisdabomb.lcc.abstracts.nuclear.NuclearUtil
import com.joshmanisdabomb.lcc.extensions.decimalFormat
import com.joshmanisdabomb.lcc.extensions.identifier
import com.joshmanisdabomb.lcc.extensions.transformInt
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.UnclampedModelPredicateProvider
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.world.World
import kotlin.math.log2

open class ComputingItem(val initialLevel: Int, val maxLevel: Int, settings: Settings, val upgrader: ((space: Int) -> Int?)? = null) : Item(settings) {

    fun getLevel(stack: ItemStack) = stack.getSubNbt("lcc-computing")?.getInt("level")?.coerceAtLeast(initialLevel) ?: initialLevel

    @Environment(EnvType.CLIENT)
    fun getDivPredicate() = UnclampedModelPredicateProvider { stack, _, _, _ ->
        val level = getLevel(stack)
        return@UnclampedModelPredicateProvider level.toFloat().minus(initialLevel).div(maxLevel-initialLevel)
    }

    @Environment(EnvType.CLIENT)
    fun getLog2Predicate() = UnclampedModelPredicateProvider { stack, _, _, _ ->
        val level = getLevel(stack)
        return@UnclampedModelPredicateProvider log2(level.toFloat().div(initialLevel)).div(log2(maxLevel.toFloat().div(initialLevel)))
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(Text.translatable("${this.translationKey}.level", getLevel(stack)).formatted(Formatting.AQUA))
    }

}