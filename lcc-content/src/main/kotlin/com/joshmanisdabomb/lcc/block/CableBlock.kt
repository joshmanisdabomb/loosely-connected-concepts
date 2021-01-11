package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.booleanProperty
import com.joshmanisdabomb.lcc.extensions.setThrough
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

open class CableBlock(settings: Settings, val connector: (world: WorldAccess, state: BlockState, pos: BlockPos, direction: Direction, state2: BlockState, pos2: BlockPos) -> Boolean) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.run { cable_states.values.setThrough(this) { with(it, false) } }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.apply { cable_states.values.forEach { add(it) } }.let {}

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.union(cable_shape, *cable_states.filterValues { state[it] }.map { (k, v) -> cable_shapes[k] }.toTypedArray())

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val bp = BlockPos.Mutable()
        return super.getPlacementState(ctx)?.run { Direction.values().setThrough(this) {
            val pos2 = bp.set(ctx.blockPos).move(it)
            with(cable_states[it]!!, connector(ctx.world, this, ctx.blockPos, it, ctx.world.getBlockState(pos2), pos2))
        } }
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, state2: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos) = super.getStateForNeighborUpdate(state, direction, state2, world, pos, posFrom).with(cable_states[direction], connector(world, state, pos, direction, state2, posFrom))

    companion object {
        val cable_states = Direction.values().map { it to it.booleanProperty }.toMap()

        val cable_shape = createCuboidShape(6.0, 6.0, 6.0, 10.0, 10.0, 10.0)
        val cable_shapes = createCuboidShape(6.0, 10.0, 6.0, 10.0, 16.0, 10.0).rotatable
    }

}