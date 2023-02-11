package com.joshmanisdabomb.lcc.block

import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

class HardLightFlatBlock(settings: Settings) : HardLightBlock(settings) {

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    companion object {
        val shape = createCuboidShape(0.0, 14.5, 0.0, 16.0, 16.0, 16.0)
    }

}