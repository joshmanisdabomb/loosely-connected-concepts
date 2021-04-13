package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.AlarmBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.sound.AlarmSoundInstance
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class AlarmBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.alarm, pos, state), BlockEntityClientSerializable {

    @Environment(EnvType.CLIENT)
    var ringerValue = 0f
    @Environment(EnvType.CLIENT)
    var ringerVelocity = 0.0001f

    @Environment(EnvType.CLIENT)
    var sound: AlarmSoundInstance? = null

    var redstone: Int? = 0

    override fun fromClientTag(tag: NbtCompound) {
        redstone = tag.getByte("Redstone").toInt()
    }

    override fun toClientTag(tag: NbtCompound): NbtCompound {
        tag.putByte("Redstone", (redstone ?: 0).toByte())
        return tag
    }

    @Environment(EnvType.CLIENT)
    fun refreshSound(redstone: Int) {
        sound = AlarmSoundInstance(this, cachedState[AlarmBlock.ringer], redstone)
    }

    companion object {
        fun clientTick(world: World, pos: BlockPos, state: BlockState, entity: AlarmBlockEntity) {
            val redstone = entity.redstone
            if (redstone != null) {
                if (entity.sound == null) {
                    entity.refreshSound(redstone)
                } else if (entity.cachedState[AlarmBlock.ringer] != entity.sound?.ringer) {
                    entity.sound?.valid = false
                    entity.refreshSound(redstone)
                } else if (entity.redstone != entity.sound?.redstone) {
                    entity.sound?.valid = false
                    entity.refreshSound(redstone)
                } else if (!MinecraftClient.getInstance().soundManager.isPlaying(entity.sound)) {
                    MinecraftClient.getInstance().soundManager.playNextTick(entity.sound)
                }
            }

            if (state[Properties.POWERED]) {
                entity.ringerVelocity *= 100f
            }
        }

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: AlarmBlockEntity) {
            val redstone = entity.redstone
            entity.redstone = world.getReceivedRedstonePower(pos)
            if (redstone != entity.redstone) {
                entity.sync()
            }
        }
    }

}