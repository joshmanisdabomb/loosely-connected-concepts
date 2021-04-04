package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.LEVEL_3
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView

class SaltBlock(settings: Settings) : Block(settings) {

    init {
        defaultState = stateManager.defaultState.with(LEVEL_3, 1)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(LEVEL_3).let {}

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shapes[state[LEVEL_3].minus(1)]

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.empty()

    companion object {
        val shapes = arrayOf(
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0),
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 5.0, 16.0)
        )
    }

}
