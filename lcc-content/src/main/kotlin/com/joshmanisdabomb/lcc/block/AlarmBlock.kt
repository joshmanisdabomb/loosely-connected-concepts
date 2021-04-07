package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.AlarmBlockEntity
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import net.minecraft.block.*
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundEvent
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.state.property.Properties.POWERED
import net.minecraft.util.BlockRotation
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class AlarmBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, false).with(ringer, Ringer.BELL)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING, POWERED, ringer).let {}

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context).with(ringer, Ringer.getRinger(context.world.getBlockState(context.blockPos.down())))

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = AlarmBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape[state[HORIZONTAL_FACING]]

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (direction != Direction.DOWN) return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
        return state.with(ringer, Ringer.getRinger(neighborState))
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        val power = world.isReceivingRedstonePower(pos)
        if (power != state.get(NoteBlock.POWERED)) {
            world.setBlockState(pos, state.with(NoteBlock.POWERED, power), 3)
        }
    }

    companion object {
        val ringer = EnumProperty.of("ringer", Ringer::class.java)

        val shape_base = createCuboidShape(5.0, 0.0, 2.0, 11.0, 4.0, 14.0)
        val shape_ringer = createCuboidShape(7.5, 4.0, 6.0, 8.5, 9.0, 10.0)
        val shape_bell_1 = createCuboidShape(6.0, 4.0, 1.0, 10.0, 10.0, 6.0)
        val shape_bell_2 = createCuboidShape(6.0, 4.0, 10.0, 10.0, 10.0, 15.0)

        val shape = VoxelShapes.union(shape_base, shape_ringer, shape_bell_1, shape_bell_2).rotatable(BlockRotation.COUNTERCLOCKWISE_90, BlockRotation.NONE, BlockRotation.CLOCKWISE_90)
    }

    enum class Ringer(val sound: SoundEvent, val base: (below: BlockState) -> Boolean = { true }) : StringIdentifiable {
        BELL(LCCSounds.alarm_bell),
        NUCLEAR_SIREN(LCCSounds.alarm_nuclear_siren, { it.isOf(LCCBlocks.uranium_block) || it.isOf(LCCBlocks.enriched_uranium_block) || it.isOf(LCCBlocks.heavy_uranium_block) || it.isOf(LCCBlocks.heavy_uranium_shielding) || it.isOf(LCCBlocks.uranium_ore) || it.isOf(LCCBlocks.deepslate_uranium_ore) || it.isOf(LCCBlocks.nuclear_waste) });

        override fun asString() = name.toLowerCase()

        companion object {
            private val all = values().apply { reverse() }

            fun getRinger(below: BlockState) = all.first { it.base(below) }
        }
    }

}