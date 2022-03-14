package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.directory.LCCBiomes
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.mojang.serialization.Codec
import net.minecraft.util.math.MathHelper
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext

class WastelandSpikesFeature(codec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(codec) {

    override fun generate(context: FeatureContext<DefaultFeatureConfig>): Boolean {
        val pos = context.origin.mutableCopy()
        val world = context.world
        val random = context.random
        if (world.getBiome(pos).value() == LCCBiomes.wasteland_spikes) return false

        while (world.isAir(pos) && pos.y > world.bottomY + 2) {
            pos.move(0, -1, 0)
        }

        if (!world.getBlockState(pos).isOf(LCCBlocks.cracked_mud)) {
            return false
        } else {
            pos.move(0, random.nextInt(16).minus(8), 0)
            val i = random.nextInt(12) + 3
            val j = (i.div(random.nextInt(4).plus(4)) + random.nextInt(2)).coerceAtLeast(1)

            var k = 0
            var l: Int
            while (k < i) {
                val f = (1.1f - k.toFloat() / i.toFloat()) * j.toFloat()
                l = MathHelper.ceil(f)
                for (m in -l..l) {
                    val g = MathHelper.abs(m).toFloat() - 0.25f
                    for (n in -l..l) {
                        val h = MathHelper.abs(n).toFloat() - 0.25f
                        if ((m == 0 && n == 0 || g * g + h * h <= f * f) && (m != -l && m != l && n != -l && n != l || random.nextFloat() <= 0.2f)) {
                            var blockState = world.getBlockState(pos.add(m, k, n))
                            if (blockState.isAir || blockState.isOf(LCCBlocks.cracked_mud)) {
                                setBlockState(world, pos.add(m, k, n), LCCBlocks.cracked_mud.defaultState)
                            }
                            if (k != 0 && l > 1) {
                                blockState = world.getBlockState(pos.add(m, -k, n))
                                if (blockState.isAir || blockState.isOf(LCCBlocks.cracked_mud)) {
                                    setBlockState(world, pos.add(m, -k, n), LCCBlocks.cracked_mud.defaultState)
                                }
                            }
                        }
                    }
                }
                ++k
            }

            k = j - 1
            if (k < 0) {
                k = 0
            } else if (k > 1) {
                k = 1
            }

            for (p in -k..k) {
                l = -k
                while (l <= k) {
                    var blockPos2 = pos.add(p, -1, l)
                    var r = 50
                    if (Math.abs(p) == 1 && Math.abs(l) == 1) {
                        r = random.nextInt(5)
                    }
                    while (blockPos2.y > 50) {
                        val blockState2 = world.getBlockState(blockPos2)
                        if (!blockState2.isAir && !blockState2.isOf(LCCBlocks.cracked_mud)) {
                            break
                        }
                        setBlockState(world, blockPos2, LCCBlocks.cracked_mud.defaultState)
                        blockPos2 = blockPos2.down()
                        --r
                        if (r <= 0) {
                            blockPos2 = blockPos2.down(random.nextInt(5) + 1)
                            r = random.nextInt(5)
                        }
                    }
                    ++l
                }
            }

            return true
        }
    }

}