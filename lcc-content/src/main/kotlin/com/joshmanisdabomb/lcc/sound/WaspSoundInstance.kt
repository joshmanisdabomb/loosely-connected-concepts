package com.joshmanisdabomb.lcc.sound

import com.joshmanisdabomb.lcc.entity.WaspEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.sound.MovingSoundInstance
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.MathHelper

class WaspSoundInstance(sound: SoundEvent, val entity: WaspEntity) : MovingSoundInstance(sound, SoundCategory.HOSTILE, entity.random) {

    var valid = true

    init {
        x = entity.x
        y = entity.y
        z = entity.z
        repeat = true
        repeatDelay = 0
        volume = 0.0f
    }

    override fun isDone(): Boolean {
        if (!valid) return true
        val cworld = MinecraftClient.getInstance().world ?: return true
        val eworld = entity.world ?: return true
        if (cworld != eworld) return true
        return entity.isRemoved
    }

    override fun tick() {
        if (!valid) return
        x = entity.x
        y = entity.y
        z = entity.z
        val speed = entity.velocity.horizontalLength().toFloat()
        if (entity.isBaby) {
            pitch = 0.0f
            volume = 0.0f
        } else {
            if (speed < 0.01f) {
                repeatDelay = 1000
            }
            pitch = MathHelper.lerp(MathHelper.clamp(speed, 0.2f, 1.0f), 0.9f, 1.2f)
            volume = MathHelper.lerp(MathHelper.clamp(speed, 0.0f, 0.5f), 0.0f, 2.4f)
        }
    }

    override fun shouldAlwaysPlay() = true

    override fun canPlay() = valid && !entity.isRemoved && !entity.isSilent

}
