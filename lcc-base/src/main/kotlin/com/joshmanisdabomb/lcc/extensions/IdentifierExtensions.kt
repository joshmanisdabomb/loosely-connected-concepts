package com.joshmanisdabomb.lcc.extensions

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

val Block.identifier get() = Registry.BLOCK.getId(this)
val Item.identifier get() = Registry.ITEM.getId(this)