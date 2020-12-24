package com.joshmanisdabomb.lcc.block

import net.minecraft.block.BlockState
import net.minecraft.block.LeavesBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class FunctionalLeavesBlock(settings: Settings, val trunk: (state: BlockState) -> Boolean) : LeavesBlock(settings) {

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        world.setBlockState(pos, updateDistance(state, world, pos), 3);
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        val i = getDistance(newState) + 1
        if (i != 1 || state.get(DISTANCE) != i) {
            world.blockTickScheduler.schedule(pos, this, 1)
        }
        return state
    }

    override fun getPlacementState(ctx: ItemPlacementContext) = updateDistance(this.defaultState.with(PERSISTENT, true), ctx.world, ctx.blockPos)

    fun updateDistance(state: BlockState, world: World, pos: BlockPos): BlockState {
        var i = 7
        val mutable = BlockPos.Mutable()

        for (d in Direction.values()) {
            mutable.set(pos, d)
            i = Math.min(i, getDistance(world.getBlockState(mutable)) + 1)
            if (i == 1) break
        }

        return state.with(DISTANCE, i)
    }

    fun getDistance(state: BlockState) = if (trunk(state)) 0 else (if (state.block is LeavesBlock) state.get(DISTANCE) else 7)

}