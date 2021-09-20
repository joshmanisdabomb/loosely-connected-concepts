package com.joshmanisdabomb.lcc.world.surface

import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.class_6557
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig
import java.util.*

class WastelandSpikesSurfaceBuilder(codec: Codec<TernarySurfaceConfig>) : WastelandSurfaceBuilder(codec) {

    override fun generate(random: Random, chunk: class_6557, biome: Biome, x: Int, z: Int, height: Int, noise: Double, defaultBlock: BlockState, defaultFluid: BlockState, seaLevel: Int, i: Int, seed: Long, config: TernarySurfaceConfig) {
        val mutable = BlockPos.Mutable()
        val j = (noise / 3.0 + 3.0 + random.nextDouble() * 0.25).toInt()
        var k: Int
        var blockState5: BlockState
        if (j == 0) {
            var bl = false
            k = height
            while (k >= i) {
                mutable.set(x, k, z)
                val blockState = chunk.getState(k)
                if (blockState.isAir) {
                    bl = false
                } else if (blockState.isOf(defaultBlock.block)) {
                    if (!bl) {
                        if (k >= seaLevel) {
                            blockState5 = Blocks.AIR.defaultState
                        } else if (k == seaLevel - 1) {
                            blockState5 = if (biome.getTemperature(mutable) < 0.15f) Blocks.ICE.defaultState else Blocks.AIR.defaultState
                        } else if (k >= seaLevel - (7 + j)) {
                            blockState5 = defaultBlock
                        } else {
                            blockState5 = config.underwaterMaterial
                        }
                        chunk.method_38092(k, blockState5)
                    }
                    bl = true
                }
                --k
            }
        } else {
            k = -1
            var ground: BlockState? = null
            val dip = random.nextInt(j)
            for (m in height downTo i) {
                mutable.set(x, m, z)
                blockState5 = chunk.getState(m)
                if (blockState5.isAir) {
                    k = -1
                } else if (blockState5.isOf(defaultBlock.block)) {
                    if (k == -1) {
                        k = j
                        var top: BlockState?
                        if (m >= seaLevel + 2) {
                            top = null//config.topMaterial
                        } else if (m >= seaLevel - 1) {
                            ground = config.underMaterial
                            top = config.topMaterial
                        } else if (m >= seaLevel - (7 + j)) {
                            ground = config.underMaterial
                            top = config.underwaterMaterial
                        } else {
                            ground = defaultBlock
                            top = config.underwaterMaterial
                        }
                        chunk.method_38092(m, top ?: if (dip == 0) config.topMaterial else Blocks.AIR.defaultState)
                    } else if (k > 0) {
                        --k
                        val ground2 = ground ?: if (j-k > dip) config.underMaterial else Blocks.AIR.defaultState
                        chunk.method_38092(m, ground2)
                        if (k == 0 && ground2.isOf(Blocks.SAND) && j > 1) {
                            k = random.nextInt(4) + Math.max(0, m - seaLevel)
                            ground = Blocks.SANDSTONE.defaultState
                        }
                    }
                }
            }
        }
    }

}
