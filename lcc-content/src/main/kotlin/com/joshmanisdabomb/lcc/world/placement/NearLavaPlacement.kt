package com.joshmanisdabomb.lcc.world.placement

import com.joshmanisdabomb.lcc.directory.LCCPlacementModifierTypes
import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.feature.FeaturePlacementContext
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier

class NearLavaPlacement() : AbstractConditionalPlacementModifier() {

    override fun getType() = LCCPlacementModifierTypes.near_lava_lake

    override fun shouldPlace(context: FeaturePlacementContext, random: net.minecraft.util.math.random.Random, pos: BlockPos): Boolean {
        val pos3 = BlockPos.Mutable()
        return GenUtils.areaMatches(context::getBlockState, pos.x, pos.y, pos.z, expand = 5, test = GenUtils::any) { state, pos2 ->
            for (i in 0..1) {
                for (j in 0..1) {
                    for (k in 0..1) {
                        pos3.set(pos2).move(i, j, k)
                        if (!context.getBlockState(pos3).fluidState.isIn(FluidTags.LAVA)) return@areaMatches false
                    }
                }
            }
            true
        }
    }

    companion object {
        val instance = NearLavaPlacement()
        val codec = Codec.unit { instance }
    }

}