package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext

class SpikeTrapFeature(configCodec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(configCodec) {

    override fun generate(context: FeatureContext<DefaultFeatureConfig>): Boolean {
        with (context) {
            val w = world.random.nextInt(2).plus(world.random.nextInt(2)).plus(2)
            val h = world.random.nextInt(7).plus(4)

            if (!GenUtils.areaMatches(world::getBlockState, origin.x, origin.y - 1, origin.z, x2 = origin.x + w-1, y2 = origin.y - h - 1, z2 = origin.z + w-1) { state, pos -> !world.isOutOfHeightLimit(pos.y) }) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x - 1, origin.y - 1, origin.z - 1, x2 = origin.x + w, z2 = origin.z + w) { state, pos -> world.getBlockState(pos).isOf(LCCBlocks.cracked_mud) }) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x, origin.y, origin.z, x2 = origin.x + w - 1, z2 = origin.z + w - 1)) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x - 1, origin.y - 2, origin.z - 1, x2 = origin.x + w, y2 = origin.y - h - 1) { state, pos -> state.isSolidBlock(world, pos) }) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x - 1, origin.y - 2, origin.z + w, x2 = origin.x + w, y2 = origin.y - h - 1) { state, pos -> state.isSolidBlock(world, pos) }) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x - 1, origin.y - 2, origin.z - 1, y2 = origin.y - h - 1, z2 = origin.z + w) { state, pos -> state.isSolidBlock(world, pos) }) return false
            if (!GenUtils.areaMatches(world::getBlockState, origin.x + w, origin.y - 2, origin.z - 1, y2 = origin.y - h - 1, z2 = origin.z + w) { state, pos -> state.isSolidBlock(world, pos) }) return false

            val bp = BlockPos.Mutable()
            val spikes = when (random.nextInt(6)) {
                0 -> LCCBlocks.bleeding_spikes
                1 -> LCCBlocks.poison_spikes
                else -> LCCBlocks.spikes
            }
            for (x in 0 until w) {
                for (z in 0 until w) {
                    for (y in -h..-1) {
                        world.setBlockState(bp.set(origin.x + x, origin.y + y, origin.z + z), if (y == -h) spikes.defaultState else Blocks.AIR.defaultState, 18)
                    }
                }
            }
        }
        return true
    }

}