package com.joshmanisdabomb.lcc.utils

import net.minecraft.item.DyeableItem
import net.minecraft.item.ItemStack

interface DefaultedDyeableItem : DyeableItem {

    fun defaultColor(stack: ItemStack): Int

    override fun getColor(stack: ItemStack): Int {
        val compoundTag = stack.getSubTag("display")
        return if (compoundTag != null && compoundTag.contains("color", 99)) compoundTag.getInt("color") else defaultColor(stack)
    }

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int) = if (tint > 0) -1 else (stack.item as DyeableItem).getColor(stack)
    }

}