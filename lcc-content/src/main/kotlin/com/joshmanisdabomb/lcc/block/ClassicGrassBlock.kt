package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

open class ClassicGrassBlock(settings: Settings) : FunctionalSnowyGrassBlock(defaultSnowyFunction(Blocks.DIRT.defaultState), settings), LCCExtendedBlock {

    override fun lcc_isPlantable(state: BlockState, world: BlockView, pos: BlockPos, plant: Block) = true

    override fun lcc_isSoil(state: BlockState) = true

}