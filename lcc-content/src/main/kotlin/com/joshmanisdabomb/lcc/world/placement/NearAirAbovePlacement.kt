package com.joshmanisdabomb.lcc.world.placement

import com.joshmanisdabomb.lcc.directory.LCCPlacementModifierTypes
import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.gen.feature.FeaturePlacementContext
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier

class NearAirAbovePlacement() : AbstractConditionalPlacementModifier() {

    override fun getType() = LCCPlacementModifierTypes.near_air_above

    override fun shouldPlace(context: FeaturePlacementContext, random: Random, pos: BlockPos): Boolean {
        val mb = BlockPos.Mutable()
        for (i in -1..1) {
            for (k in -1..1) {
                if (!context.getBlockState(mb.set(pos).move(i, 1, k)).isAir && !context.getBlockState(mb.move(0, 1, 0)).isAir) return false
            }
        }
        return true
    }

    companion object {
        val instance = NearAirAbovePlacement()
        val codec = Codec.unit { instance }
    }

}