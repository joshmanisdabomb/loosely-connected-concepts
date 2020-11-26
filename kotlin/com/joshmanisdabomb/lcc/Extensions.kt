package com.joshmanisdabomb.lcc

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.registry.Registry

fun Boolean.toInt(t: Int = 1, f: Int = 0) = if (this) t else f

fun Item.stack(count: Int = 1) = ItemStack(this, count)

val Block.identifier get() = Registry.BLOCK.getId(this)

val Item.identifier get() = Registry.ITEM.getId(this)

fun Entity.replaceVelocity(x: Double? = null, y: Double? = null, z: Double? = null) {
    val v = this.velocity
    this.setVelocity(x ?: v.x, y ?: v.y, z ?: v.z)
}

fun Entity.replacePosition(x: Double? = null, y: Double? = null, z: Double? = null) {
    val p = this.pos
    this.updatePosition(x ?: p.x, y ?: p.y, z ?: p.z)
}

const val NBT_BYTE = 1
const val NBT_SHORT = 2
const val NBT_INT = 3
const val NBT_LONG = 4
const val NBT_FLOAT = 5
const val NBT_DOUBLE = 6
const val NBT_COMPOUND = 10
const val NBT_NUMERIC = 99