package com.joshmanisdabomb.lcc.abstracts.oxygen

import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper

interface OxygenStorage {

    fun getOxygen(stack: ItemStack) = stack.tag?.getFloat("lcc-oxygen") ?: 0f
    fun setOxygen(stack: ItemStack, value: Float) = stack.orCreateTag.putFloat("lcc-oxygen", value.coerceIn(0f, getMaxOxygen(stack)))
    fun addOxygen(stack: ItemStack, value: Float) = setOxygen(stack, getOxygen(stack) + value)

    fun getMaxOxygen(stack: ItemStack): Float
    fun getOxygenFill(stack: ItemStack) = (getOxygen(stack) / getMaxOxygen(stack)).coerceAtMost(1f)

    fun isFull(stack: ItemStack) = getOxygen(stack) >= getMaxOxygen(stack)

    fun getOxygenBarStep(stack: ItemStack): Int {
        return MathHelper.ceil((getOxygenFill(stack)).times(13f))
    }

    fun getOxygenBarColor(stack: ItemStack): Int {
        val fill = getOxygenFill(stack)
        return MathHelper.hsvToRgb(1.0f - fill.times(0.45f), 0.3f, fill.times(0.2f).plus(0.8f))
    }

}
