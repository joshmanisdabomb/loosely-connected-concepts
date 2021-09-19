package com.joshmanisdabomb.lcc.extensions

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

fun Item.stack(count: Int = 1, alter: ItemStack.() -> Unit = {}) = ItemStack(this, count).apply(alter)