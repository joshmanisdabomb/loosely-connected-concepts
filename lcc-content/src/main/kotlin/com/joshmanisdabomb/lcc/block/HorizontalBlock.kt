package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager

open class HorizontalBlock(settings: Settings) : HorizontalFacingBlock(settings) {

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

}