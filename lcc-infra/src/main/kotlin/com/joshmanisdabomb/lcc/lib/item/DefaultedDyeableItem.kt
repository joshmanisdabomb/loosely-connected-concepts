package com.joshmanisdabomb.lcc.lib.item

import net.minecraft.item.DyeableItem
import net.minecraft.item.ItemStack

interface DefaultedDyeableItem : DyeableItem, DefaultedColoredItem {

    override fun getColor(stack: ItemStack) = getTintColor(stack)

}