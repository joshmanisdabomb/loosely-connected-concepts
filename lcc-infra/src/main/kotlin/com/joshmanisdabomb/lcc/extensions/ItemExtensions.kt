package com.joshmanisdabomb.lcc.extensions

import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack

fun ItemConvertible.stack(count: Int = 1, alter: ItemStack.() -> Unit = {}) = ItemStack(this, count).apply(alter)