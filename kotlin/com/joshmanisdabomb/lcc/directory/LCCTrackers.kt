package com.joshmanisdabomb.lcc.directory

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.player.PlayerEntity

object LCCTrackers : ThingDirectory<TrackedData<*>, Class<out Entity>>() {

    val heartsLastType by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.BYTE) }
    val heartsRedMax by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.FLOAT) }
    val heartsIron by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.FLOAT) }
    val heartsIronMax by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.FLOAT) }
    val heartsCrystal by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.FLOAT) }
    val heartsCrystalRegen by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.FLOAT) }
    val heartsCrystalMax by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.FLOAT) }
    val heartsTemporary by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.FLOAT) }

    val gauntletFallHandler by create(LivingEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.BYTE) }
    val gauntletUppercut by create(PlayerEntity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.TAG_COMPOUND) }
    val gauntletUppercutee by create(Entity::class.java) { DataTracker.registerData(it, TrackedDataHandlerRegistry.TAG_COMPOUND) }

}