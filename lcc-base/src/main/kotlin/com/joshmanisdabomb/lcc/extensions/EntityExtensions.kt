package com.joshmanisdabomb.lcc.extensions

import net.minecraft.entity.Entity

fun Entity.replaceVelocity(x: Double? = null, y: Double? = null, z: Double? = null) {
    val v = this.velocity
    this.setVelocity(x ?: v.x, y ?: v.y, z ?: v.z)
}

fun Entity.replacePosition(x: Double? = null, y: Double? = null, z: Double? = null) {
    val p = this.pos
    this.updatePosition(x ?: p.x, y ?: p.y, z ?: p.z)
}