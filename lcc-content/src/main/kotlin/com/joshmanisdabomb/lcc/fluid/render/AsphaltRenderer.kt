package com.joshmanisdabomb.lcc.fluid.render

import com.joshmanisdabomb.lcc.directory.LCCFluids
import net.minecraft.fluid.FluidState
import net.minecraft.state.property.Properties.AGE_7
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockRenderView

class AsphaltRenderer : FluidRenderer(LCCFluids.asphalt_still, LCCFluids.asphalt_flowing) {

    override fun getFluidColor(view: BlockRenderView?, pos: BlockPos?, state: FluidState): Int {
        val col = 0xFF - 0x12.times(view?.getBlockState(pos)?.get(AGE_7) ?: return color)
        return (col shl 16) or (col shl 8) or col
    }

}
