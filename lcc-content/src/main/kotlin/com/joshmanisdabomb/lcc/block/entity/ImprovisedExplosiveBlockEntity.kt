package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.ImprovisedExplosiveBlock
import com.joshmanisdabomb.lcc.block.ImprovisedExplosiveBlock.Companion.ie_state
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.sound.ImprovisedExplosiveSoundInstance
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ImprovisedExplosiveBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.improvised_explosive, pos, state) {

    @Environment(EnvType.CLIENT)
    var sound: ImprovisedExplosiveSoundInstance? = null

    var fuse: Int = 0

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)
        fuse = tag.getShort("Fuse").toInt()
    }

    override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)
        tag.putShort("Fuse", fuse.toShort())
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt() = createNbt()

    @Environment(EnvType.CLIENT)
    fun refreshSound(constant: Boolean) {
        sound = ImprovisedExplosiveSoundInstance(this, constant)
    }

    companion object {
        fun clientTick(world: World, pos: BlockPos, state: BlockState, entity: ImprovisedExplosiveBlockEntity) {
            if (state[ie_state] == ImprovisedExplosiveBlock.ImprovisedExplosiveState.INACTIVE) return
            entity.fuse -= 1
            if (state[ie_state] == ImprovisedExplosiveBlock.ImprovisedExplosiveState.IMMINENT && entity.sound?.constant == false) {
                entity.sound?.valid = false
                entity.refreshSound(true)
            } else if (entity.sound == null) {
                entity.refreshSound(state[ie_state] == ImprovisedExplosiveBlock.ImprovisedExplosiveState.IMMINENT)
            } else if (!MinecraftClient.getInstance().soundManager.isPlaying(entity.sound)) {
                MinecraftClient.getInstance().soundManager.playNextTick(entity.sound)
            }
        }

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: ImprovisedExplosiveBlockEntity) {
            if (state[ie_state] == ImprovisedExplosiveBlock.ImprovisedExplosiveState.INACTIVE) return
            entity.fuse -= 1
            if (entity.fuse <= 0) {
                (state.block as? ImprovisedExplosiveBlock)?.explode(world, pos)
            } else if (entity.fuse <= 20) {
                world.setBlockState(pos, state.with(ie_state, ImprovisedExplosiveBlock.ImprovisedExplosiveState.IMMINENT))
            }
        }
    }

}