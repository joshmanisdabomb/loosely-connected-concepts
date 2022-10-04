package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.square
import com.joshmanisdabomb.lcc.world.feature.config.FlowerPatchFeatureConfig
import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext
import kotlin.math.ceil

class FlowerPatchFeature(configCodec: Codec<FlowerPatchFeatureConfig>) : Feature<FlowerPatchFeatureConfig>(configCodec) {

    override fun generate(context: FeatureContext<FlowerPatchFeatureConfig>): Boolean {
        var success = false
        with (context) {
            val bp = BlockPos.Mutable()
            repeat (config.size+world.random.nextInt(config.size.square())) {
                val dx = world.random.nextInt(config.size).minus(world.random.nextInt(config.size))
                val dz = world.random.nextInt(config.size).minus(world.random.nextInt(config.size))
                if (dx+dz > 3 && ceil(world.random.nextInt(dx+dz).div(2f)).toInt() > 2) return@repeat
                val y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, origin.x+dx, origin.z+dz)
                if (world.isOutOfHeightLimit(y)) return@repeat

                bp.set(origin.x+dx, y-1, origin.z+dz)
                if (!world.getBlockState(bp).isOf(LCCBlocks.cracked_mud)) return@repeat
                world.setBlockState(bp, config.base.getBlockState(random, bp), 18)
                success = true
                bp.move(0, 1, 0)
                if (!world.getBlockState(bp).isAir) return@repeat
                if (world.random.nextBoolean()) world.setBlockState(bp, config.flower.getBlockState(random, bp), 18)
            }
        }
        return success
    }

}