package com.joshmanisdabomb.lcc.world.placement

import com.joshmanisdabomb.lcc.directory.LCCPlacementModifierTypes
import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.gen.feature.FeaturePlacementContext
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier

class NearAirPlacement() : AbstractConditionalPlacementModifier() {

    override fun getType() = LCCPlacementModifierTypes.near_air

    override fun shouldPlace(context: FeaturePlacementContext, random: net.minecraft.util.math.random.Random, pos: BlockPos): Boolean {
        for (d in Direction.values()) {
            if (context.getBlockState(pos.offset(d)).isAir) return true
        }
        return false
    }

    companion object {
        val instance = NearAirPlacement()
        val codec = Codec.unit { instance }
    }

}