package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.RadarBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.state.property.Properties.TRIGGERED
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class RadarBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(TRIGGERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING, TRIGGERED).let {}

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(HORIZONTAL_FACING, rot.rotate(state[HORIZONTAL_FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = RadarBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos) = world.getBlockState(pos.down()).isSideSolid(world, pos, Direction.UP, SideShapeType.CENTER)

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (direction != Direction.DOWN) return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
        return if (!state.canPlaceAt(world, pos)) Blocks.AIR.defaultState else super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.radar, RadarBlockEntity::serverTick) else null

    override fun emitsRedstonePower(state: BlockState) = state[TRIGGERED]

    override fun getWeakRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, direction: Direction): Int {
        if (!state[TRIGGERED]) return 0
        val entity = world.getBlockEntity(pos) as? RadarBlockEntity ?: return 0
        return entity.level
    }

    override fun hasComparatorOutput(state: BlockState) = state[TRIGGERED]

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        if (!state[TRIGGERED]) return 0
        val entity = world.getBlockEntity(pos) as? RadarBlockEntity ?: return 0
        return entity.type?.redstone ?: 0
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        player.sendMessage(Text.translatable("block.lcc.radar.range", (world.getBlockEntity(pos) as? RadarBlockEntity)?.radius ?: 0), true)
        return ActionResult.SUCCESS
    }

    companion object {
        val shape_base = createCuboidShape(1.0, 0.0, 1.0, 15.0, 3.0, 15.0)
        val shape_rod = createCuboidShape(7.0, 3.0, 7.0, 9.0, 15.0, 9.0)

        val shape = VoxelShapes.union(shape_base, shape_rod)
    }

}