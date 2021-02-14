package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FallingBlock
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class NuclearWasteBlock(settings: Settings) : Block(settings), LCCExtendedBlockContent {

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        fall(world, pos, state, oldState)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        world.blockTickScheduler.schedule(pos, this, 1)
        return state
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random?) {
        fall(world, pos, state, state_air)
    }

    fun fall(world: World, pos: BlockPos, state: BlockState, oldState: BlockState) {
        val bp = BlockPos.Mutable().set(pos).move(0, 1, 0)
        val fire = world.getBlockState(bp).isOf(LCCBlocks.nuclear_fire)
        bp.move(0, -1, 0)
        var flag = false
        while (FallingBlock.canFallThrough(world.getBlockState(bp.move(0, -1, 0))) && bp.y >= world.bottomY) {
            flag = true
        }
        if (flag) {
            world.setBlockState(pos, oldState)
            world.setBlockState(bp.move(0, 1, 0), state)
            if (fire) world.setBlockState(bp.move(0, 1, 0), state_fire)
        }
    }

    override fun lcc_content_nukeIgnore() = true

    companion object {
        val state_air by lazy { Blocks.AIR.defaultState }
        val state_fire by lazy { LCCBlocks.nuclear_fire.defaultState }
    }

}