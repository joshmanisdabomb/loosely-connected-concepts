package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class DepositBlock(settings: Settings) : Block(settings) {

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (context.isAbove(collider, pos, true)) collider else VoxelShapes.empty()

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val pos2 = pos.down()
        val state2 = world.getBlockState(pos2)
        val shape = state2.getCollisionShape(world, pos2, ShapeContext.absent()).getFace(Direction.UP).boundingBoxes.firstOrNull() ?: return false
        return shape.minX <= 0.3125 && shape.maxX >= 0.6875 && shape.minZ <= 0.3125 && shape.maxZ >= 0.6875
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState = if (direction == Direction.DOWN && !canPlaceAt(state, world, pos)) Blocks.AIR.defaultState else super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)

    override fun getPistonBehavior(state: BlockState) = PistonBehavior.DESTROY

    companion object {
        val shape = createCuboidShape(4.0, 0.0, 4.0, 12.0, 9.0, 12.0)
        val collider = createCuboidShape(4.0, 0.0, 4.0, 12.0, 3.0, 12.0)
    }

}