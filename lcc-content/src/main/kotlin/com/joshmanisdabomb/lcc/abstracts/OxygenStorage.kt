package com.joshmanisdabomb.lcc.abstracts

import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper

interface OxygenStorage {

    fun getOxygen(stack: ItemStack) = stack.tag?.getInt("lcc-oxygen") ?: 0
    fun setOxygen(stack: ItemStack, value: Int) = stack.orCreateTag.putInt("lcc-oxygen", value.coerceIn(0, getMaxOxygen(stack)))
    fun addOxygen(stack: ItemStack, value: Int) = setOxygen(stack, getOxygen(stack) + value)

    fun getMaxOxygen(stack: ItemStack): Int
    fun getOxygenFill(stack: ItemStack) = (getOxygen(stack).toFloat() / getMaxOxygen(stack)).coerceAtMost(1f)

    fun isFull(stack: ItemStack) = getOxygen(stack) >= getMaxOxygen(stack)

    fun getOxygenBarStep(stack: ItemStack): Int {
        return MathHelper.ceil((getOxygenFill(stack)).times(13f))
    }

    fun getOxygenBarColor(stack: ItemStack): Int {
        val fill = getOxygenFill(stack)
        return MathHelper.hsvToRgb(1.0f - fill.times(0.45f), 0.3f, fill.times(0.2f).plus(0.8f))
    }

}
