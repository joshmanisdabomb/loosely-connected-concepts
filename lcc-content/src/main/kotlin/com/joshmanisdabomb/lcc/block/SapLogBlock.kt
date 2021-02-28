package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.booleanProperty
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess

class SapLogBlock(val liquid: AbstractTreetapBlock.TreetapLiquid, settings: Settings, stripped: (BlockState) -> BlockState?) : StrippableBlock(settings, stripped) {

    init {
        defaultState = stateManager.defaultState.with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(NORTH, EAST, SOUTH, WEST).let {}

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        if (neighborState.block is AbstractTreetapBlock && neighborState[HORIZONTAL_FACING] == direction) {
            return state.with(direction.booleanProperty, true)
        }
        return state
    }

    override fun getPistonBehavior(state: BlockState) = PistonBehavior.BLOCK

}