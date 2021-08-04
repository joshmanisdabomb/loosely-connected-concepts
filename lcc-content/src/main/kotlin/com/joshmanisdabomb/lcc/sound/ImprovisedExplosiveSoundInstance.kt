package com.joshmanisdabomb.lcc.sound

import com.joshmanisdabomb.lcc.block.ImprovisedExplosiveBlock
import com.joshmanisdabomb.lcc.block.ImprovisedExplosiveBlock.Companion.ie_state
import com.joshmanisdabomb.lcc.block.entity.ImprovisedExplosiveBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.client.MinecraftClient
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.client.sound.TickableSoundInstance
import net.minecraft.sound.SoundCategory

class ImprovisedExplosiveSoundInstance(val entity: ImprovisedExplosiveBlockEntity, val constant: Boolean = false) : PositionedSoundInstance(constant.transform(LCCSounds.improvised_explosive_constant, LCCSounds.improvised_explosive_beep), SoundCategory.BLOCKS, 2f, 1f, entity.pos), TickableSoundInstance {

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

    override fun canPlay() = valid && !entity.isRemoved && entity.cachedState[ie_state] != ImprovisedExplosiveBlock.ImprovisedExplosiveState.INACTIVE

}
