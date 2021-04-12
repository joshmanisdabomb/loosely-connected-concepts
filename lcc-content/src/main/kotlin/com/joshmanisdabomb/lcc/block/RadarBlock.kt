package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.RadarBlockEntity
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class RadarBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.TRIGGERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(Properties.HORIZONTAL_FACING, Properties.TRIGGERED).let {}

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = RadarBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape[state[Properties.HORIZONTAL_FACING]]

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos) = world.getBlockState(pos.down()).isSideSolid(world, pos, Direction.UP, SideShapeType.CENTER)

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (direction != Direction.DOWN) return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
        return if (!state.canPlaceAt(world, pos)) Blocks.AIR.defaultState else super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.radar, RadarBlockEntity::serverTick) else null

    companion object {
        val shape_base = createCuboidShape(1.0, 0.0, 1.0, 15.0, 3.0, 15.0)

        val shape = VoxelShapes.union(shape_base).rotatable(BlockRotation.COUNTERCLOCKWISE_90, BlockRotation.NONE, BlockRotation.CLOCKWISE_90)
    }

}