package com.joshmanisdabomb.lcc.world.carver

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.world.gen.carver.RavineCarver
import net.minecraft.world.gen.carver.RavineCarverConfig

class WastelandRavineCarver(codec: Codec<RavineCarverConfig>) : RavineCarver(codec) {

    override fun getBranchFactor() = 8

    override fun canCarveBlock(state: BlockState, stateAbove: BlockState) = super.canCarveBlock(state, stateAbove) || state.isOf(LCCBlocks.cracked_mud)

}