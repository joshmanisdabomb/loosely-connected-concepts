package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess

class HardeningBlock(settings: Settings, val harden: (state: BlockState) -> BlockState) : Block(settings) {

    override fun getPlacementState(ctx: ItemPlacementContext) = if (shouldHarden(ctx.world, ctx.blockPos, ctx.world.getBlockState(ctx.blockPos))) super.getPlacementState(ctx)?.let { harden(it) } else super.getPlacementState(ctx)

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos) = if (hardensOnAnySide(world, pos)) state.let { harden(it) } else super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)

    fun shouldHarden(world: BlockView, pos: BlockPos, state: BlockState) = hardensIn(state) || hardensOnAnySide(world, pos)

    fun hardensIn(state: BlockState) = state.fluidState.isIn(FluidTags.WATER)

    fun hardensOnAnySide(world: BlockView, pos: BlockPos): Boolean {
        val bp = pos.mutableCopy()
        return Direction.values().any {
            if (it == Direction.DOWN) return@any false
            val state = world.getBlockState(bp.set(pos, it))
            return@any hardensIn(state) && !state.isSideSolidFullSquare(world, pos, it.opposite)
        }
    }

}