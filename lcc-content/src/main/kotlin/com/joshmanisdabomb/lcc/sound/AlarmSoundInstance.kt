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
class AlarmSoundInstance(private val entity: AlarmBlockEntity, val ringer: AlarmBlock.Ringer, val redstone: Int) : PositionedSoundInstance(ringer.sound, SoundCategory.BLOCKS, redstone.div(15f).times(ringer.volume), 1f, entity.world?.random, entity.pos), TickableSoundInstance {

    var valid = true

    init {
        repeat = true
        repeatDelay = 0
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
    }

    override fun shouldAlwaysPlay() = true

    override fun canPlay() = valid && !entity.isRemoved && entity.cachedState[Properties.POWERED]

}