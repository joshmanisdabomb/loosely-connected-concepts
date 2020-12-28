package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.SnowyBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.SNOWY
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

open class FunctionalSnowyGrassBlock(change: (world: World, source: BlockState?, destination: BlockState, posFrom: BlockPos, posTo: BlockPos, it: FunctionalGrassBlock) -> BlockState?, settings: Settings, lightRequired: Int = 9) : FunctionalGrassBlock(change, settings, lightRequired) {

    init {
        defaultState = stateManager.defaultState.with(SNOWY, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(SNOWY).let {}

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, newState: BlockState, world: WorldAccess, pos: BlockPos, posFrom: BlockPos): BlockState {
        return if (direction != Direction.UP) super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom) else state.with(SNOWY, newState.isOf(Blocks.SNOW_BLOCK) || newState.isOf(Blocks.SNOW))
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        val state2 = ctx.world.getBlockState(ctx.blockPos.up())
        return defaultState.with(SnowyBlock.SNOWY, state2.isOf(Blocks.SNOW_BLOCK) || state2.isOf(Blocks.SNOW))
    }

    companion object {

        fun defaultSnowyFunction(grass: BlockState, dirt: BlockState) = { world: World, source: BlockState?, destination: BlockState, from: BlockPos, to: BlockPos, it: FunctionalGrassBlock ->
            when {
                source == null -> dirt
                destination.isOf(Blocks.DIRT) -> {
                    val state2 = world.getBlockState(to.up())
                    grass.with(SNOWY, state2.isOf(Blocks.SNOW) || state2.isOf(Blocks.SNOW_BLOCK))
                }
                else -> null
            }
        }

        fun defaultSnowyFunction(dirt: BlockState) = { world: World, source: BlockState?, destination: BlockState, from: BlockPos, to: BlockPos, it: FunctionalGrassBlock ->
            when {
                source == null -> dirt
                destination.isOf(Blocks.DIRT) -> {
                    val state2 = world.getBlockState(to.up())
                    it.defaultState.with(SNOWY, state2.isOf(Blocks.SNOW) || state2.isOf(Blocks.SNOW_BLOCK))
                }
                else -> null
            }
        }

    }

}
