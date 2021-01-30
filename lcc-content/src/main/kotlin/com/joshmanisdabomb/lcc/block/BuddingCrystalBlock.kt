package com.joshmanisdabomb.lcc.block

import net.minecraft.block.AmethystClusterBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BuddingAmethystBlock
import net.minecraft.fluid.Fluids
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties.FACING
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import java.util.*
import kotlin.random.asKotlinRandom

class BuddingCrystalBlock(val crystals: Array<AmethystClusterBlock>, settings: Settings) : BuddingAmethystBlock(settings) {

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        if (random.nextInt(5) == 0) {
            val direction = Direction.values().random(random.asKotlinRandom())
            val pos2 = pos.offset(direction)
            val state2 = world.getBlockState(pos2)
            var block: Block? = null
            if (canGrowIn(state2)) {
                block = crystals.first()
            } else {
                for (i in 1 until crystals.size) {
                    if (state2.isOf(crystals[i-1]) && state2[FACING] == direction) {
                        block = crystals[i]
                        break
                    }
                }
            }
            if (block == null) return
            val new = block.defaultState.with(FACING, direction).with(AmethystClusterBlock.WATERLOGGED, state2.fluidState.fluid === Fluids.WATER)
            world.setBlockState(pos2, new)
        }
    }

}