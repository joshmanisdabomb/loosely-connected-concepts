package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView

class IdolPedestalBlock(settings: Settings): Block(settings) {

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = shape

    override fun canReplace(state: BlockState, context: ItemPlacementContext): Boolean {
        val idol = (context.stack.item as? BlockItem)?.block as? IdolBlock ?: return super.canReplace(state, context)
        return !context.shouldCancelInteraction() || super.canReplace(state, context)
    }

    companion object {
        val shape = VoxelShapes.union(
            createCuboidShape(5.0, 0.0, 5.0, 11.0, 1.0, 11.0),
            createCuboidShape(6.0, 1.0, 6.0, 10.0, 6.0, 10.0),
            createCuboidShape(5.0, 6.0, 5.0, 11.0, 7.0, 11.0),
        )
    }

}
