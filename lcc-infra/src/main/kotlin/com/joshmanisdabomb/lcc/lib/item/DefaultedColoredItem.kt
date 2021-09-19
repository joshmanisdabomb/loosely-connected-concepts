package com.joshmanisdabomb.lcc.lib.item

import net.minecraft.item.ItemStack

interface DefaultedColoredItem {

    fun defaultColor(stack: ItemStack): Int

    fun getTintColor(stack: ItemStack): Int {
        val nbtCompound = stack.getSubTag("display")
        return if (nbtCompound != null && nbtCompound.contains("color", 99)) nbtCompound.getInt("color") else defaultColor(stack)
    }

    companion object {
        fun getTintColor(stack: ItemStack, tint: Int, provider: (stack: ItemStack) -> Int = { (stack.item as DefaultedColoredItem).getTintColor(stack) }) = if (tint > 0) -1 else provider(stack)
    }

}