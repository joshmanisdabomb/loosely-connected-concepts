package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import com.joshmanisdabomb.lcc.directory.LCCPacketsToClient
import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.directory.LCCSounds
import io.netty.buffer.Unpooled
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.fabricmc.fabric.api.server.PlayerStream
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.sound.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import java.util.*

class SoakingSoulSandBlock(settings: Settings) : Block(settings), LCCBlockTrait {

    @Environment(EnvType.CLIENT)
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        with(Direction.random(random)) {
            world.addParticle(
                LCCParticles.soaking_soul_sand_bubble,
                pos.x.toDouble() + if (this.offsetX != 0) MathHelper.clamp(this.offsetX.toDouble(), -0.1, 1.0) + random.nextDouble() * 0.1 else random.nextDouble(),
                pos.y.toDouble() + if (this.offsetY != 0) MathHelper.clamp(this.offsetY.toDouble(), -0.1, 1.0) + random.nextDouble() * 0.1 else random.nextDouble(),
                pos.z.toDouble() + if (this.offsetZ != 0) MathHelper.clamp(this.offsetZ.toDouble(), -0.1, 1.0) + random.nextDouble() * 0.1 else random.nextDouble(),
                if (this.offsetX == 0) (random.nextDouble() - 0.5) * 0.1 else 0.0,
                if (this.offsetY == 0) (random.nextDouble() - 0.5) * 0.1 else 0.0,
                if (this.offsetZ == 0) (random.nextDouble() - 0.5) * 0.1 else 0.0
            )
        }
    }

    override fun lcc_onEntitySingleJumpOff(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: LivingEntity): Boolean {
        with (pos.minByOrNull { it.getSquaredDistance(entity.x, entity.y, entity.z, true) } ?: return false) {
            entity.addVelocity(0.0, 0.78, 0.0)
            entity.fallDistance = -1.0F
            if (!world.isClient) {
                PlayerStream.watching(world, this).forEach { ServerSidePacketRegistry.INSTANCE.sendToPlayer(it, LCCPacketsToClient[LCCPacketsToClient::soul_sand_jump_particle].first().id, PacketByteBuf(Unpooled.buffer()).also { it.writeBoolean(true).writeDouble(this.x + 0.5).writeDouble(this.y + 1.0).writeDouble(this.z + 0.5).writeDouble(0.0).writeDouble(0.0).writeDouble(0.0).writeByte(Direction.UP.ordinal).writeFloat(1.0f).writeFloat(1.0f).writeFloat(1.0f) }) }
                entity.world.playSound(null, this.x + 0.5, this.y + 0.5, this.z + 0.5, LCCSounds.soaking_soul_sand_jump, SoundCategory.BLOCKS, 0.4F, 1.3F)
            }
        }
        return true
    }

}