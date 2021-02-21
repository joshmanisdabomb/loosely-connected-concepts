package com.joshmanisdabomb.lcc.abstracts.gauntlet

import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import kotlin.properties.Delegates

abstract class GauntletTargetInstance<A : GauntletActorInstance>(val entity: Entity) {

    var ticks = 0
    var maxTicks by Delegates.notNull<Int>()

    abstract val action: GauntletAction<A>
    var actor: PlayerEntity? = null

    fun init(actor: PlayerEntity, info: A) {
        maxTicks = getTime(actor, info)
    }

    abstract fun initial(actor: PlayerEntity, info: A): Boolean

    abstract fun tick(): Boolean

    fun baseTick(): Boolean {
        if (tick()) return true
        else return ticks++ >= maxTicks
    }

    abstract fun getTime(actor: PlayerEntity, info: A): Int

    val percent get() = ticks.toDouble().div(maxTicks)

    fun read(tag: CompoundTag) {
        ticks = tag.getInt("Ticks")
        maxTicks = tag.getInt("MaxTicks")
        readFromNbt(tag)
    }

    protected open fun readFromNbt(tag: CompoundTag) = Unit

    fun write(tag: CompoundTag) {
        tag.putInt("Ticks", ticks)
        tag.putInt("MaxTicks", maxTicks)
        writeToNbt(tag)
    }

    protected open fun writeToNbt(tag: CompoundTag) = Unit

}