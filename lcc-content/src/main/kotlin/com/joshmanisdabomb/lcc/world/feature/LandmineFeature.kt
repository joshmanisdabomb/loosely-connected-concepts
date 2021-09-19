package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext

class LandmineFeature(configCodec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(configCodec) {

    override fun generate(context: FeatureContext<DefaultFeatureConfig>): Boolean {
        with (context) {
            if (!GenUtils.areaMatches(world::getBlockState, origin.x, origin.y - 3, origin.z, y2 = origin.y) { state, pos -> !world.isOutOfHeightLimit(pos.y) }) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x, origin.y - 1, origin.z, ex = 1, ez = 1) { state, pos -> world.getBlockState(pos).isOf(LCCBlocks.cracked_mud) }) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x, origin.y, origin.z, ex = 1, ez = 1)) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x, origin.y - 3, origin.z, ex = 1, y2 = origin.y - 2, ez = 1) { state, pos -> state.isSolidBlock(world, pos) }) return false

            world.setBlockState(origin, LCCBlocks.cracked_mud_pressure_plate.defaultState, 18)
            world.setBlockState(origin.add(0, -2, 0), LCCBlocks.improvised_explosive.defaultState, 18)

            if (random.nextBoolean() || !GenUtils.areaMatches(world::getBlockState, origin.x, origin.y - 4, origin.z, ex = 1, ez = 1) { state, pos -> state.isSolidBlock(world, pos) }) return true
            world.setBlockState(origin.add(0, -3, 0), LCCBlocks.improvised_explosive.defaultState, 18)
        }
        return true
    }

}