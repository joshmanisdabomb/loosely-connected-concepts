package com.joshmanisdabomb.lcc.sound

import com.joshmanisdabomb.lcc.block.AlarmBlock
import com.joshmanisdabomb.lcc.block.entity.AlarmBlockEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.client.sound.TickableSoundInstance
import net.minecraft.sound.SoundCategory
import net.minecraft.state.property.Properties

@Environment(EnvType.CLIENT)
class AlarmSoundInstance(private val entity: AlarmBlockEntity, private val ringer: AlarmBlock.Ringer, volume: Float) : PositionedSoundInstance(ringer.sound, SoundCategory.BLOCKS, volume, 1f, entity.pos), TickableSoundInstance {

    var valid = true

    init {
        repeat = true
        repeatDelay = 0
        looping = true
    }

    override fun isDone(): Boolean {
        if (!valid) return true
        val cworld = MinecraftClient.getInstance().world ?: return true
        val eworld = entity.world ?: return true
        if (cworld != eworld) return true
        return entity != eworld.getBlockEntity(entity.pos)
    }

    override fun tick() {
        if (!valid) return
        if (entity.cachedState[AlarmBlock.ringer] != ringer) {
            entity.refreshSound()
            valid = false
        }
    }

    override fun shouldAlwaysPlay() = true

    override fun canPlay() = valid && entity.cachedState[Properties.POWERED]

}