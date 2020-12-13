package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.BouncePadBlock.Companion.SETTING
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.block.FacingBlock.FACING
import net.minecraft.block.entity.BlockEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.util.math.BlockPos

class BouncePadBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.bounce_pad, pos, state) {

    var extension = 0f

    val setting = state.get(SETTING)
    val facing = state.get(FACING)

    fun extend() {
        extension = setting.div(5f)
    }

    @Environment(EnvType.CLIENT)
    fun effects() {
        world?.addParticle(SoakingSoulSandJumpParticleEffect(facing, 0.875f, setting.plus(1).times(4).minus(2).toFloat(), 0.5f + (setting + 1) * 0.2f), false, pos.x + 0.5, pos.y + 0.4375, pos.z + 0.5, 0.0, 0.0, 0.0)
        world?.playSound(pos.x + 0.5 - 0.0625.times(facing.offsetX), pos.y + 0.5 - 0.0625.times(facing.offsetY), pos.z + 0.5 - 0.0625.times(facing.offsetZ), LCCSounds.bounce_pad_jump, SoundCategory.BLOCKS, 0.4f, 0.95f + (4 - setting) * 0.05f, false)
    }

}