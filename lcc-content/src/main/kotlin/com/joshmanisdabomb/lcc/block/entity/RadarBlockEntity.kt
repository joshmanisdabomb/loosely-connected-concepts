package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity
import com.joshmanisdabomb.lcc.extensions.NBT_BYTE
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.TntEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties.TRIGGERED
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.max
import kotlin.math.sqrt

class RadarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.radar, pos, state) {

    val radius by lazy { 256 + pos.y.times(2) }
    val radius_sq by lazy { radius.times(radius) }

    var level = 0
    var type: RadarDetection? = null

    override fun readNbt(nbt: NbtCompound) {
        level = nbt.getByte("Distance").toInt()
        if (nbt.contains("Type", NBT_BYTE)) {
            type = RadarDetection.values()[nbt.getByte("Type").toInt()]
        }
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt(): NbtCompound {
        val tag = NbtCompound()
        tag.putByte("Distance", level.toByte())
        type?.also { tag.putByte("Type", it.ordinal.toByte()) }
        return tag
    }

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RadarBlockEntity) {
            val sworld = world as? ServerWorld ?: return
            val level = entity.level
            val type = entity.type
            entity.level = 0
            entity.type = null
            for (e in sworld.iterateEntities()) {
                val newType = when (e) {
                    is TntEntity -> RadarDetection.TNT
                    is AtomicBombEntity -> if (e.active) RadarDetection.ATOMIC_BOMB else continue
                    else -> continue
                }
                val distance = e.squaredDistanceTo(pos.x.plus(0.5), pos.y.plus(0.5), pos.z.plus(0.5))
                if (distance <= entity.radius_sq) {
                    entity.level = max(entity.level, (1.minus(sqrt(distance).div(entity.radius)).times(15)).toInt().coerceAtLeast(1))
                    entity.type = (entity.type ?: RadarDetection.TNT).coerceAtLeast(newType)
                }
            }
            if (level != entity.level || type != entity.type) {
                sworld.chunkManager.markForUpdate(pos)
                world.updateNeighbors(pos, state.block)
            }
            if (state[TRIGGERED] == (entity.level == 0)) {
                world.setBlockState(pos, state.with(TRIGGERED, entity.level > 0))
            }
        }
    }

    enum class RadarDetection {
        TNT,
        ATOMIC_BOMB;

        val redstone = ordinal.plus(1)
    }

}