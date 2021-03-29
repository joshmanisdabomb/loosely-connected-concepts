package com.joshmanisdabomb.lcc.abstracts.gauntlet

import com.joshmanisdabomb.lcc.abstracts.heart.HeartType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import kotlin.math.floor
import kotlin.math.pow

class TemporaryHealthCalculator(val power: Double) {

    private var health = 0.0
    private var healthCalc = 0.0

    fun add(actor: PlayerEntity, total: Double) {
        health += total
        val before = healthCalc
        healthCalc = floor(health.pow(power))
        HeartType.TEMPORARY.addHealth(actor, healthCalc.minus(before).toFloat())
    }

    fun readFromNbt(tag: NbtCompound) {
        health = tag.getDouble("TemporaryHealth")
        healthCalc = tag.getDouble("TemporaryHealthCalc")
    }

    fun writeToNbt(tag: NbtCompound) {
        tag.putDouble("TemporaryHealth", health)
        tag.putDouble("TemporaryHealthCalc", healthCalc)
    }

}