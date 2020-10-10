package com.joshmanisdabomb.lcc

import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag

fun Boolean.toInt(t: Int = 1, f: Int = 0) = if (this) t else f

fun Item.stack(count: Int = 1) = ItemStack(this, count)

fun Entity.replaceVelocity(x: Double? = null, y: Double? = null, z: Double? = null) {
    val v = this.velocity
    this.setVelocity(x ?: v.x, y ?: v.y, z ?: v.z);
}

const val NBT_INT = 3