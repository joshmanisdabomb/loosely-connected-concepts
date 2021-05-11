package com.joshmanisdabomb.lcc.utils

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound

object ItemStackUtils {

    fun fromTagIntCount(tag: NbtCompound) = ItemStack.fromNbt(tag).apply { count = tag.getInt("Count") }

    fun toTagIntCount(stack: ItemStack, tag: NbtCompound) = stack.writeNbt(tag).apply { tag.apply { remove("Count"); putInt("Count", stack.count) } }

}