package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks.traitDirectionalPlacement
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FacingBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager

class DirectionalBlock(settings: Settings) : FacingBlock(settings) {

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(FACING).let { Unit }

    override fun getPlacementState(context: ItemPlacementContext) = traitDirectionalPlacement(context)

}