package com.joshmanisdabomb.lcc.extensions

import net.minecraft.entity.Entity
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import kotlin.reflect.KProperty

fun Entity.replaceVelocity(x: Double? = null, y: Double? = null, z: Double? = null) {
    val v = this.velocity
    this.setVelocity(x ?: v.x, y ?: v.y, z ?: v.z)
}

fun Entity.replacePosition(x: Double? = null, y: Double? = null, z: Double? = null) {
    val p = this.pos
    this.updatePosition(x ?: p.x, y ?: p.y, z ?: p.z)
}

operator fun <T, E : Entity> TrackedData<T>.getValue(entity: E, property: KProperty<*>): T = entity.dataTracker.get(this)
operator fun <T, E : Entity> TrackedData<T>.setValue(entity: E, property: KProperty<*>, value: T) = entity.dataTracker.set(this, value)

val PlayerEntity.isSurvival get() = !this.isCreative && !this.isSpectator
fun PlayerEntity.insertStack(stack: ItemStack) = if (!this.inventory.insertStack(stack)) this.dropItem(stack, false) else null