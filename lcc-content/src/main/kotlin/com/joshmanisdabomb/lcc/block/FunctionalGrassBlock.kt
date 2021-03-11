package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.SnowBlock
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.chunk.light.ChunkLightProvider
import java.util.*

open class FunctionalGrassBlock(val change: (world: World, source: BlockState?, destination: BlockState, posFrom: BlockPos, posTo: BlockPos, it: FunctionalGrassBlock) -> BlockState?, settings: Settings, val lightRequired: Int = 9) : Block(settings) {

    open fun survives(state: BlockState, world: WorldAccess, pos: BlockPos, water: Boolean): Boolean {
        val pos2 = pos.up()
        val state2 = world.getBlockState(pos2)
        return if (state2.block == Blocks.SNOW && state2[SnowBlock.LAYERS] == 1) {
            true
        } else if (water && state2.fluidState.level == 8) {
            false
        } else {
            val i = ChunkLightProvider.getRealisticOpacity(world, state, pos, state2, pos2, Direction.UP, state2.getOpacity(world, pos2))
            return i < world.maxLightLevel
        }
    }

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (!survives(state, world, pos, false)) {
            world.setBlockState(pos, change(world, null, state, pos, pos, this))
        } else {
            if (world.getLightLevel(pos.up()) >= lightRequired) {
                for (i in 0..3) {
                    val pos2 = pos.method_34592(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1)
                    if (survives(state, world, pos2, true)) {
                        val param = world.getBlockState(pos2)
                        if (param.block != this) {
                            val set = this.change(world, state, param, pos, pos2, this)
                            if (set != null && set != param) {
                                world.setBlockState(pos2, set)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun defaultFunction(grass: BlockState, dirt: BlockState) = { world: World, source: BlockState?, destination: BlockState, from: BlockPos, to: BlockPos, it: Block ->
            when {
                source == null -> dirt
                destination.isOf(Blocks.DIRT) -> grass
                else -> null
            }
        }

        fun defaultFunction(dirt: BlockState) = { world: World, source: BlockState?, destination: BlockState, from: BlockPos, to: BlockPos, it: Block ->
            when {
                source == null -> dirt
                destination.isOf(Blocks.DIRT) -> it.defaultState
                else -> null
            }
        }

    }
}