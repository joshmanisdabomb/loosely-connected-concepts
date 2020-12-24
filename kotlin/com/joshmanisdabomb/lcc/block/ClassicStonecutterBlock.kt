package com.joshmanisdabomb.lcc.block

import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.block.StonecutterBlock
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView

class ClassicStonecutterBlock(settings: Settings) : StonecutterBlock(settings) {

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.fullCube()

    override fun hasSidedTransparency(state: BlockState) = false

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType) = false

}