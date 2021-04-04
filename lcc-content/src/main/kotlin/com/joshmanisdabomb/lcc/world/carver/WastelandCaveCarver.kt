package com.joshmanisdabomb.lcc.world.carver

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.class_6108
import net.minecraft.world.gen.carver.CaveCarver
import java.util.*

class WastelandCaveCarver(codec: Codec<class_6108>) : CaveCarver(codec) {

    override fun getBranchFactor() = 8

    override fun getMaxCaveCount() = 32

    override fun getTunnelSystemWidth(random: Random) = random.nextFloat() * 0.5f

    override fun getTunnelSystemHeightWidthRatio() = 2.0

    override fun canCarveBlock(state: BlockState, stateAbove: BlockState) = super.canCarveBlock(state, stateAbove) || state.isOf(LCCBlocks.cracked_mud)

}