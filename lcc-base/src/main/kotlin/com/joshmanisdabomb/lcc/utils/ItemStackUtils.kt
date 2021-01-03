package com.joshmanisdabomb.lcc.utils

import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag

object ItemStackUtils {

    fun fromTagIntCount(tag: CompoundTag) = ItemStack.fromTag(tag).apply { count = tag.getInt("Count") }

    fun toTagIntCount(stack: ItemStack, tag: CompoundTag) = stack.toTag(tag).apply { tag.apply { remove("Count"); putInt("Count", stack.count) } }

}