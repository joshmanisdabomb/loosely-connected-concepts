package com.joshmanisdabomb.lcc.utils

import net.minecraft.item.DyeableItem
import net.minecraft.item.ItemStack

interface DefaultedDyeableItem : DyeableItem {

    fun defaultColor(stack: ItemStack): Int

    override fun getColor(stack: ItemStack): Int {
        val nbtCompound = stack.getSubTag("display")
        return if (nbtCompound != null && nbtCompound.contains("color", 99)) nbtCompound.getInt("color") else defaultColor(stack)
    }

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int) = if (tint > 0) -1 else (stack.item as DyeableItem).getColor(stack)
    }

}