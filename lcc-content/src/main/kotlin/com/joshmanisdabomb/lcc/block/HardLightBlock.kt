package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import com.joshmanisdabomb.lcc.extensions.minus
import com.joshmanisdabomb.lcc.extensions.plus
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties.*
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess

open class HardLightBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(UP, false).with(left, false).with(DOWN, false).with(right, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING, UP, left, DOWN, right).let {}

    override fun getPlacementState(context: ItemPlacementContext): BlockState {
        var state = horizontalPlacement(context)
        for (d in horizontalDirections) {
            val property = properties[d]
            val offset = d + state[HORIZONTAL_FACING]
            val other = context.world.getBlockState(context.blockPos.offset(offset))
            state = state.with(property, other.block == this && other[HORIZONTAL_FACING] == state[HORIZONTAL_FACING])
        }
        return state
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (direction.axis.type == Direction.Type.HORIZONTAL) {
            val property = properties[direction - state[HORIZONTAL_FACING]]
            return state.with(property, neighborState.block == this && neighborState[HORIZONTAL_FACING] == state[HORIZONTAL_FACING])
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun rotate(state: BlockState, rot: BlockRotation): BlockState {
        val state = state.with(HORIZONTAL_FACING, rot.rotate(state[HORIZONTAL_FACING]))
        var state2 = state
        for (d in horizontalDirections) {
            state2 = state2.with(properties[d], state[properties[rot.directionTransformation.map(d)] ?: continue])
        }
        return state2
    }

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

    override fun isTranslucent(state: BlockState, world: BlockView, pos: BlockPos) = true

    override fun getCameraCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.empty()

    override fun getAmbientOcclusionLightLevel(state: BlockState, world: BlockView, pos: BlockPos) = 1.0f

    override fun isSideInvisible(state: BlockState, stateFrom: BlockState, direction: Direction): Boolean {
        val property = properties[direction - state[HORIZONTAL_FACING]] ?: return super.isSideInvisible(state, stateFrom, direction)
        return state[property]
    }

    companion object {
        val left: BooleanProperty = BooleanProperty.of("left")
        val right: BooleanProperty = BooleanProperty.of("right")
        val properties = mapOf(Direction.NORTH to UP, Direction.EAST to right, Direction.SOUTH to DOWN, Direction.WEST to left)
    }

}
