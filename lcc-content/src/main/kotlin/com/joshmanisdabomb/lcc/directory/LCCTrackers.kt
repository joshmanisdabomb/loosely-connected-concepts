package com.joshmanisdabomb.lcc.directory

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.player.PlayerEntity

//TODO replace with cardinal components
object LCCTrackers : BasicDirectory<TrackedData<*>, Class<out Entity>>() {

    val heartsLastType by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.BYTE) }
        .setProperties(LivingEntity::class.java)
    val heartsIron by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.FLOAT) }
        .setProperties(LivingEntity::class.java)
    val heartsIronMax by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.FLOAT) }
        .setProperties(LivingEntity::class.java)
    val heartsCrystal by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.FLOAT) }
        .setProperties(LivingEntity::class.java)
    val heartsCrystalRegen by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.TAG_COMPOUND) }
        .setProperties(LivingEntity::class.java)
    val heartsCrystalMax by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.FLOAT) }
        .setProperties(LivingEntity::class.java)
    val heartsTemporary by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.FLOAT) }
        .setProperties(LivingEntity::class.java)

    val gauntletFallHandler by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.BYTE) }
        .setProperties(LivingEntity::class.java)
    val gauntletUppercut by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.TAG_COMPOUND) }
        .setProperties(PlayerEntity::class.java)
    val gauntletUppercutTarget by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.TAG_COMPOUND) }
        .setProperties(Entity::class.java)
    val gauntletPunch by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.TAG_COMPOUND) }
        .setProperties(PlayerEntity::class.java)
    val gauntletPunchTarget by entry(::initialiser) { DataTracker.registerData(properties, TrackedDataHandlerRegistry.TAG_COMPOUND) }
        .setProperties(Entity::class.java)

    fun <T> initialiser(input: TrackedData<T>, context: DirectoryContext<Class<out Entity>>, parameters: Unit) = input

    override fun defaultProperties(name: String) = error("No default properties available for this directory.")

}