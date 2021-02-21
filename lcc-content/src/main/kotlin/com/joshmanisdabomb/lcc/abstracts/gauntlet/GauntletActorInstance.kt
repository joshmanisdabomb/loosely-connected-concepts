package com.joshmanisdabomb.lcc.abstracts.gauntlet

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import kotlin.properties.Delegates

abstract class GauntletActorInstance(val actor: PlayerEntity) {

    abstract val action: GauntletAction2<*>

    var maxCooldown by Delegates.notNull<Int>()
    var maxCast by Delegates.notNull<Int>()
    var remaining by Delegates.notNull<Int>()

    var cooldown = 0
    var cast = 0

    fun init(remaining: Int, cancelled: Boolean) {
        this.remaining = remaining
        maxCooldown = getMaxCooldownTime(cancelled)
        maxCast = getMaxCastTime(cancelled)
    }

    abstract fun getMaxCooldownTime(cancelled: Boolean): Int
    abstract fun getMaxCastTime(cancelled: Boolean): Int

    abstract fun initial(remaining: Int, cancelled: Boolean): Boolean

    abstract fun tick(): Boolean

    fun baseTick(): Boolean {
        if (isCasting) {
            if (tick()) cast = maxCast + 1
            else cast++
            return false
        }
        return cooldown++ >= maxCooldown
    }

    val charge get() = action.maxChargeTime - remaining
    val canCharge get() = action.maxChargeTime >= 0

    val isCasting get() = cast < maxCast
    val isCooldown get() = !isCasting && cooldown <= maxCooldown
    val cooldownPercent get() = cooldown.toDouble().div(maxCooldown)
    val castPercent get() = cast.toDouble().div(maxCast)
    val chargePercent get() = action.chargePercent(remaining)

    fun read(tag: CompoundTag) {
        cooldown = tag.getInt("Cooldown")
        maxCooldown = tag.getInt("MaxCooldown")
        cast = tag.getInt("Cast")
        maxCast = tag.getInt("MaxCast")
        remaining = tag.getInt("Remaining")
        readFromNbt(tag)
    }

    protected open fun readFromNbt(tag: CompoundTag) = Unit

    fun write(tag: CompoundTag) {
        tag.putInt("Cooldown", cooldown)
        tag.putInt("MaxCooldown", maxCooldown)
        tag.putInt("Cast", cast)
        tag.putInt("MaxCast", maxCast)
        tag.putInt("Remaining", remaining)
        writeToNbt(tag)
    }

    protected open fun writeToNbt(tag: CompoundTag) = Unit

}
