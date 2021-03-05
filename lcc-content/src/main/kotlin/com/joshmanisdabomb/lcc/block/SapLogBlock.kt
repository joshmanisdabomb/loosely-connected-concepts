package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.booleanProperty
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.*
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class SapLogBlock(val liquid: AbstractTreetapBlock.TreetapLiquid, settings: Settings, override val stripTransform: (BlockState) -> BlockState?) : Block(settings), StrippableBlock {

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

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult) = stripUse(state, world, pos, player, hand, hit)

}