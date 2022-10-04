package com.joshmanisdabomb.lcc.world.carver

import com.mojang.serialization.Codec
import net.minecraft.world.gen.carver.CaveCarver
import net.minecraft.world.gen.carver.CaveCarverConfig

class WastelandCaveCarver(codec: Codec<CaveCarverConfig>) : CaveCarver(codec) {

    /*override fun getBranchFactor() = 8

    override fun getMaxCaveCount() = 32

    override fun getTunnelSystemWidth(random: Random) = random.nextFloat() * 0.5f

    override fun getTunnelSystemHeightWidthRatio() = 2.0

    override fun canAlwaysCarveBlock(state: BlockState) = super.canAlwaysCarveBlock(state) || state.isOf(LCCBlocks.cracked_mud)*/

}