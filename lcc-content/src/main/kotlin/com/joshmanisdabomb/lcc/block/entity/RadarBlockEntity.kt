package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity
import com.joshmanisdabomb.lcc.extensions.NBT_BYTE
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.TntEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties.TRIGGERED
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.max
import kotlin.math.sqrt

class RadarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.radar, pos, state), BlockEntityClientSerializable {

    val radius by lazy { 256 + pos.y.times(2) }
    val radius_sq by lazy { radius.times(radius) }

    var level = 0
    var type: RadarDetection? = null

    override fun fromClientTag(tag: NbtCompound) {
        level = tag.getByte("Distance").toInt()
        if (tag.contains("Type", NBT_BYTE)) {
            type = RadarDetection.values()[tag.getByte("Type").toInt()]
        }
    }

    override fun toClientTag(tag: NbtCompound): NbtCompound {
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
                entity.sync()
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