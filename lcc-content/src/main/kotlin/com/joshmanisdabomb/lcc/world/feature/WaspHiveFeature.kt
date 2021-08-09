package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.block.entity.PapercombBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.mojang.serialization.Codec
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext
import kotlin.math.abs

class WaspHiveFeature(codec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(codec) {

    override fun generate(context: FeatureContext<DefaultFeatureConfig>): Boolean {
        var gen = false
        with (context) {
            val pos = BlockPos.Mutable()
            repeat(4) {
                attempts@ for (i in 0 until 20) {
                    val x = random.nextInt(5)
                    val z = random.nextInt(5)
                    val y = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, origin.x + x, origin.z + z) - random.nextInt(5)
                    for (y1 in -2..0) {
                        val state = world.getBlockState(pos.set(origin.x + x, y + y1, origin.z + z))
                        if (!state.isOf(LCCBlocks.papercomb_block) && state.isSolidBlock(world, pos)) {
                            for (x2 in -2..2) {
                                for (z2 in -2..2) {
                                    for (y2 in -2..2) {
                                        val dist = abs(x2) + abs(y2) + abs(z2)
                                        if (world.random.nextInt(dist.plus(1)) < 3 && world.random.nextInt(2) == 0 && world.isAir(pos.set(origin.x + x, y, origin.z + z).move(x2, y2, z2))) {
                                            fall@ for (j in 3 downTo 0) {
                                                for (d in horizontalDirections) {
                                                    if (world.getBlockState(pos.move(d)).isOf(LCCBlocks.papercomb_block)) {
                                                        world.setBlockState(pos.move(d.opposite), LCCBlocks.papercomb_block.defaultState, 18)
                                                        if (!gen || world.random.nextInt(5) == 0) (world.getBlockEntity(pos) as? PapercombBlockEntity)?.spawn()
                                                        break@fall
                                                    }
                                                }
                                                if (!world.isAir(pos.move(0, -1, 0))) {
                                                    world.setBlockState(pos.move(0, 1, 0), LCCBlocks.papercomb_block.defaultState, 18)
                                                    if (!gen || world.random.nextInt(5) == 0) (world.getBlockEntity(pos) as? PapercombBlockEntity)?.spawn()
                                                    break
                                                }
                                            }
                                            gen = true
                                        }
                                    }
                                }
                            }
                            break@attempts
                        }
                    }
                }
            }
        }
        return gen
    }

}
