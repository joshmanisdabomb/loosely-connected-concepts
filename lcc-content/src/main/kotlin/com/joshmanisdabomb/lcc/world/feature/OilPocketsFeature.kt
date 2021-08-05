package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext

class OilPocketsFeature(configCodec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(configCodec) {

    override fun generate(context: FeatureContext<DefaultFeatureConfig>): Boolean {
        var success = false
        with (context) {
            val bp = BlockPos.Mutable()
            repeat (world.random.nextInt(4).plus(3)) {
                val x = origin.x.plus(world.random.nextInt(8).minus(world.random.nextInt(8)))
                val z = origin.z.plus(world.random.nextInt(8).minus(world.random.nextInt(8)))
                val y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z)
                if (!world.getBlockState(bp.set(x, y-1, z)).isOf(LCCBlocks.cracked_mud)) return@repeat
                val h = world.random.nextInt(3).plus(2)
                if (!GenUtils.areaMatches(world::getBlockState, x, y-h, z, ex = 1, ey = 1, ez = 1) { state, pos -> state.isSolidBlock(world, pos) }) return@repeat
                world.setBlockState(bp.setY(y-h), LCCBlocks.oil.defaultState, 18)
                success = true
            }
        }
        return success
    }

}