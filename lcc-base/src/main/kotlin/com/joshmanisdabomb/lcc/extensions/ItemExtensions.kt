package com.joshmanisdabomb.lcc.extensions

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

fun Item.stack(count: Int = 1) = ItemStack(this, count)