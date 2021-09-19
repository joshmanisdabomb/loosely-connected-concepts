package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext

class DeadwoodLogsFeature(codec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(codec) {

    override fun generate(context: FeatureContext<DefaultFeatureConfig>): Boolean {
        var success = false
        with (context) {
            val bp = BlockPos.Mutable()
            repeat (world.random.nextInt(7).plus(2)) {
                val x = origin.x.plus(world.random.nextInt(16).minus(world.random.nextInt(16)))
                val z = origin.z.plus(world.random.nextInt(16).minus(world.random.nextInt(16)))
                val y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z)
                if (world.isOutOfHeightLimit(y)) return@repeat
                if (world.random.nextBoolean()) {
                    val h = world.random.nextInt(7).plus(3)
                    if (!GenUtils.areaMatches(world::getBlockState, x, y-1, z, ex = 1, ez = 1) { state, pos -> state.isOf(LCCBlocks.cracked_mud) }) return@repeat
                    if (!GenUtils.areaMatches(world::getBlockState, x, y, z, ex = 1, ez = 1, height = h)) return@repeat
                    for (i in 0..h) {
                        world.setBlockState(bp.set(x, y+i, z), LCCBlocks.deadwood_log.defaultState.with(Properties.AXIS, Direction.Axis.Y), 18)
                    }
                    success = true
                } else {
                    val l = world.random.nextInt(9).plus(3)
                    val d = horizontalDirections[world.random.nextInt(4)]
                    if (!GenUtils.areaMatches(world::getBlockState, x, y-1, z, ex = 1, ez = 1) { state, pos -> state.isOf(LCCBlocks.cracked_mud) }) return@repeat
                    if (!GenUtils.areaMatches(world::getBlockState, x + d.offsetX.times(l), y-1, z + d.offsetZ.times(l), ex = 1, ez = 1) { state, pos -> state.isOf(LCCBlocks.cracked_mud) }) return@repeat
                    if (!GenUtils.areaMatches(world::getBlockState, x, y, z, ex = 1, ez = 1, height = 1)) return@repeat
                    if (!GenUtils.areaMatches(world::getBlockState, x + d.offsetX.times(l), y, z + d.offsetZ.times(l), ex = 1, ez = 1, height = 1)) return@repeat

                    var last = y
                    for (i in 1..l) {
                        val next = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x + d.offsetX.times(i), z + d.offsetZ.times(i))
                        if (next > last+1) return@repeat
                        last = next
                    }

                    val drop = world.random.nextBoolean()
                    for (i in 0..l) {
                        val y2 = if (i == 0) y else world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x + d.offsetX.times(i), z + d.offsetZ.times(i))
                        if (world.isOutOfHeightLimit(y2)) continue
                        world.setBlockState(bp.set(x + d.offsetX.times(i), if (y2 < y && !drop) y else y2, z + d.offsetZ.times(i)), LCCBlocks.deadwood_log.defaultState.with(Properties.AXIS, d.axis), 18)
                    }

                    success = true
                }
            }
        }
        return success
    }

}
