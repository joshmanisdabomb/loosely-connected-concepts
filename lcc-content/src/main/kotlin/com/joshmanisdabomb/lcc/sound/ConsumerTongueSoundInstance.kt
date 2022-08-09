package com.joshmanisdabomb.lcc.sound

import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.entity.ConsumerTongueEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.sound.MovingSoundInstance
import net.minecraft.sound.SoundCategory

class ConsumerTongueSoundInstance(val entity: ConsumerTongueEntity) : MovingSoundInstance(LCCSounds.consumer_tongue_loop, SoundCategory.HOSTILE, entity.world.random) {

    var valid = true

    init {
        x = entity.x
        y = entity.y
        z = entity.z
        repeat = true
        repeatDelay = 0
        volume = 1.0f
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
    }

    override fun shouldAlwaysPlay() = true

    override fun canPlay() = valid && !entity.isRemoved && !entity.isSilent

}
