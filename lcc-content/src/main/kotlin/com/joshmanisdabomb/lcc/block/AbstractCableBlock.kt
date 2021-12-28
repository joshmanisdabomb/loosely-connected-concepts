package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.setThrough
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Property
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

abstract class AbstractCableBlock<T : Comparable<T>, S : Comparable<S>, B : Comparable<B>>(settings: Settings) : Block(settings) {

    abstract val topProperty: Property<T>
    abstract val sideProperties: Map<Direction, Property<S>>
    abstract val bottomProperty: Property<B>

    init {
        defaultState = stateManager.defaultState.with(this.topProperty, getTopDefault()).with(this.bottomProperty, getBottomDefault()).run { sideProperties.values.setThrough(this) { with(it, getSideDefault()) } }
    }

    abstract fun getTopConnection(world: WorldAccess, state: BlockState, pos: BlockPos, state2: BlockState, pos2: BlockPos): T
    abstract fun getSideConnection(world: WorldAccess, state: BlockState, pos: BlockPos, side: Direction, state2: BlockState, pos2: BlockPos): S
    abstract fun getBottomConnection(world: WorldAccess, state: BlockState, pos: BlockPos, state2: BlockState, pos2: BlockPos): B

    protected open fun getTopDefault() = this.topProperty.values.first()
    protected open fun getSideDefault() = this.sideProperties.values.first().values.first()
    protected open fun getBottomDefault() = this.bottomProperty.values.first()

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(topProperty, bottomProperty).apply { sideProperties.values.forEach { add(it) } }.let {}

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val bp = BlockPos.Mutable()
        val placementState = super.getPlacementState(ctx) ?: return null
        var state = placementState

        val up = ctx.blockPos.up()
        state = state.with(topProperty, getTopConnection(ctx.world, placementState, ctx.blockPos, ctx.world.getBlockState(up), up))
        val down = ctx.blockPos.down()
        state = state.with(bottomProperty, getBottomConnection(ctx.world, placementState, ctx.blockPos, ctx.world.getBlockState(down), down))

        return state?.run { horizontalDirections.setThrough(this) {
            val pos2 = bp.set(ctx.blockPos).move(it)
            with(sideProperties[it], getSideConnection(ctx.world, this, ctx.blockPos, it, ctx.world.getBlockState(pos2), pos2))
        } }
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, state2: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos) = when (direction) {
        Direction.UP -> super.getStateForNeighborUpdate(state, direction, state2, world, pos, posFrom).with(topProperty, getTopConnection(world, state, pos, state2, posFrom))
        Direction.DOWN -> super.getStateForNeighborUpdate(state, direction, state2, world, pos, posFrom).with(bottomProperty, getBottomConnection(world, state, pos, state2, posFrom))
        else -> super.getStateForNeighborUpdate(state, direction, state2, world, pos, posFrom).with(sideProperties[direction], getSideConnection(world, state, pos, direction, state2, posFrom))
    }

}