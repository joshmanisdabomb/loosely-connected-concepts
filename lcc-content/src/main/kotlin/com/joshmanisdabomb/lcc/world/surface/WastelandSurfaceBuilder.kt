package com.joshmanisdabomb.lcc.world.surface

import com.joshmanisdabomb.lcc.block.HardeningBlock
import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig
import java.util.*

open class WastelandSurfaceBuilder(codec: Codec<TernarySurfaceConfig>) : SurfaceBuilder<TernarySurfaceConfig>(codec) {

    override fun generate(random: Random, chunk: Chunk, biome: Biome, x: Int, z: Int, height: Int, noise: Double, defaultBlock: BlockState, defaultFluid: BlockState, seaLevel: Int, i: Int, l: Long, surfaceConfig: TernarySurfaceConfig) {
        val mutable = BlockPos.Mutable()
        val j = (noise / 3.0 + 3.0 + random.nextDouble() * 0.25).toInt()
        var k: Int
        var blockState5: BlockState
        if (j == 0) {
            var bl = false
            k = height
            while (k >= i) {
                mutable.set(x, k, z)
                val blockState = chunk.getBlockState(mutable)
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
                            blockState5 = surfaceConfig.underwaterMaterial
                        }
                        chunk.setBlockState(mutable, blockState5, false)
                    }
                    bl = true
                }
                --k
            }
        } else {
            k = -1
            var ground: BlockState = surfaceConfig.underMaterial
            val dip = random.nextInt(j)
            for (m in height downTo i) {
                mutable.set(x, m, z)
                blockState5 = chunk.getBlockState(mutable)
                if (blockState5.isAir) {
                    k = -1
                } else if (blockState5.isOf(defaultBlock.block)) {
                    if (k == -1) {
                        k = j
                        var top: BlockState
                        if (m >= seaLevel + 2) {
                            top = surfaceConfig.topMaterial
                        } else if (m >= seaLevel - 1) {
                            ground = surfaceConfig.underMaterial
                            top = surfaceConfig.topMaterial
                        } else if (m >= seaLevel - (7 + j)) {
                            ground = surfaceConfig.underMaterial
                            top = surfaceConfig.underwaterMaterial
                        } else {
                            ground = defaultBlock
                            top = surfaceConfig.underwaterMaterial
                        }

                        chunk.setBlockState(mutable, top, false)
                        if (m <= seaLevel && top.block is HardeningBlock) chunk.markBlockForPostProcessing(mutable)
                    } else if (k > 0) {
                        --k
                        chunk.setBlockState(mutable, ground, false)
                        if (m <= seaLevel && ground.block is HardeningBlock) chunk.markBlockForPostProcessing(mutable)
                        if (k == 0 && ground.isOf(Blocks.SAND) && j > 1) {
                            k = random.nextInt(4) + Math.max(0, m - seaLevel)
                            ground = Blocks.SANDSTONE.defaultState
                        }
                    }
                }
            }
        }
    }

}
