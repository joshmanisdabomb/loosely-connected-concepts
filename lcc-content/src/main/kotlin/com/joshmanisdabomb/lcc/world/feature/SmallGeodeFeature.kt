package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.extensions.isHorizontal
import com.joshmanisdabomb.lcc.world.feature.config.SmallGeodeFeatureConfig
import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.state.property.Properties.*
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.random.Random
import net.minecraft.world.Heightmap
import net.minecraft.world.WorldAccess
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.util.FeatureContext
import java.util.*
import kotlin.math.max

class SmallGeodeFeature(configCodec: Codec<SmallGeodeFeatureConfig>) : Feature<SmallGeodeFeatureConfig>(configCodec) {

    override fun generate(context: FeatureContext<SmallGeodeFeatureConfig>): Boolean {
        with (context) {
            val f = random.nextFloat() * 3.1415927f
            val g = config.size.toFloat() / 8.0f
            val i = MathHelper.ceil((config.size.toFloat() / 16.0f * 2.0f + 1.0f) / 2.0f)
            val d = context.origin.x.toDouble() + Math.sin(f.toDouble()) * g.toDouble()
            val e = context.origin.x.toDouble() - Math.sin(f.toDouble()) * g.toDouble()
            val h = context.origin.z.toDouble() + Math.cos(f.toDouble()) * g.toDouble()
            val j = context.origin.z.toDouble() - Math.cos(f.toDouble()) * g.toDouble()
            val k = 1
            val l = (context.origin.y + random.nextInt(3) - 2).toDouble()
            val m = (context.origin.y + random.nextInt(3) - 2).toDouble()
            val n = context.origin.x - MathHelper.ceil(g) - i
            val o = context.origin.y - 2 - i
            val p = context.origin.z - MathHelper.ceil(g) - i
            val q = 2 * (MathHelper.ceil(g) + i)
            val r = 2 * (2 + i)
            for (s in n..n + q) {
                for (t in p..p + q) {
                    if (o <= world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) {
                        return generateVeinPart(world, random, config, d, e, h, j, l, m, n, o, p, q, r)
                    }
                }
            }
        }
        return false
    }

    protected fun generateVeinPart(world: WorldAccess, random: Random, config: SmallGeodeFeatureConfig, startX: Double, endX: Double, startZ: Double, endZ: Double, startY: Double, endY: Double, x: Int, y: Int, z: Int, size: Int, i: Int): Boolean {
        var j = 0
        val bitSet = BitSet(size * i * size)
        val mutable = BlockPos.Mutable()
        val k = config.size
        val ds = DoubleArray(k * 4)
        var n: Int
        var p: Double
        var q: Double
        var r: Double
        var s: Double
        val geodeMap = mutableMapOf<BlockPos, Int>()
        n = 0
        while (n < k) {
            val f = n.toFloat() / k.toFloat()
            p = MathHelper.lerp(f.toDouble(), startX, endX)
            q = MathHelper.lerp(f.toDouble(), startY, endY)
            r = MathHelper.lerp(f.toDouble(), startZ, endZ)
            s = random.nextDouble() * k.toDouble() / 16.0
            val m = ((MathHelper.sin(3.1415927f * f) + 1.0f).toDouble() * s + 1.0) / 2.0
            ds[n * 4 + 0] = p
            ds[n * 4 + 1] = q
            ds[n * 4 + 2] = r
            ds[n * 4 + 3] = m
            ++n
        }
        n = 0
        while (n < k - 1) {
            if (ds[n * 4 + 3] > 0.0) {
                for (o in n + 1 until k) {
                    if (ds[o * 4 + 3] > 0.0) {
                        p = ds[n * 4 + 0] - ds[o * 4 + 0]
                        q = ds[n * 4 + 1] - ds[o * 4 + 1]
                        r = ds[n * 4 + 2] - ds[o * 4 + 2]
                        s = ds[n * 4 + 3] - ds[o * 4 + 3]
                        if (s * s > p * p + q * q + r * r) {
                            if (s > 0.0) {
                                ds[o * 4 + 3] = -1.0
                            } else {
                                ds[n * 4 + 3] = -1.0
                            }
                        }
                    }
                }
            }
            ++n
        }
        n = 0
        while (n < k) {
            val u = ds[n * 4 + 3]
            if (u >= 0.0) {
                val v = ds[n * 4 + 0]
                val w = ds[n * 4 + 1]
                val aa = ds[n * 4 + 2]
                val ab = Math.max(MathHelper.floor(v - u), x)
                val ac = Math.max(MathHelper.floor(w - u), y)
                val ad = Math.max(MathHelper.floor(aa - u), z)
                val ae = Math.max(MathHelper.floor(v + u), ab)
                val af = Math.max(MathHelper.floor(w + u), ac)
                val ag = Math.max(MathHelper.floor(aa + u), ad)
                for (ah in ab..ae) {
                    val ai = (ah.toDouble() + 0.5 - v) / u
                    if (ai * ai < 1.0) {
                        for (aj in ac..af) {
                            val ak = (aj.toDouble() + 0.5 - w) / u
                            if (ai * ai + ak * ak < 1.0) {
                                for (al in ad..ag) {
                                    val am = (al.toDouble() + 0.5 - aa) / u
                                    if (ai * ai + ak * ak + am * am < 1.0) {
                                        val an = ah - x + (aj - y) * size + (al - z) * size * i
                                        if (!bitSet[an]) {
                                            bitSet.set(an)
                                            geodeSet(geodeMap, 3, mutable.set(ah, aj, al))
                                            for (d in Direction.values()) {
                                                geodeSet(geodeMap,  2, mutable.set(ah, aj, al).move(d))
                                                geodeSet(geodeMap,  1, mutable.move(d))
                                                if (d.isHorizontal) {
                                                    geodeSet(geodeMap,  1, mutable.set(ah, aj, al).move(d).move(d.rotateYClockwise()))
                                                    geodeSet(geodeMap,  1, mutable.set(ah, aj, al).move(d).move(Direction.UP))
                                                    geodeSet(geodeMap,  1, mutable.move(Direction.DOWN, 2))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ++n
        }

        for (entry in geodeMap) {
            j = set(world.getBlockState(entry.key), entry.value, random, world, config, entry.key, j)
        }

        return j > 0
    }

    private fun geodeSet(geodeMap: MutableMap<BlockPos, Int>, to: Int, pos: BlockPos.Mutable) {
        val p = pos.toImmutable()
        geodeMap[p] = max(to, geodeMap[p] ?: 0)
    }

    private fun set(state: BlockState, to: Int, random: Random, world: WorldAccess, config: SmallGeodeFeatureConfig, pos: BlockPos, changed: Int): Int {
        val to = when (to) {
            3 -> config.gem.defaultState
            2 -> if (random.nextInt(5) > 0) config.inner else return changed
            1 -> if (random.nextInt(4) > 0) config.outer else return changed
            else -> return changed
        }
        var c = changed
        if (state.isFullCube(world, pos) && (state.isIn(BlockTags.BASE_STONE_OVERWORLD) || state.isIn(BlockTags.ENDERMAN_HOLDABLE) || state.isOf(Blocks.SANDSTONE))) {
            if (to.isOf(config.gem) && random.nextInt(9) == 0) {
                world.setBlockState(pos, config.bud.defaultState, 2)
                for (d in Direction.values()) {
                    val pos2 = pos.offset(d)
                    val state2 = world.getBlockState(pos2)
                    if (random.nextInt(4) != 0 && state2.isAir) {
                        world.setBlockState(pos2, config.bud.crystals[random.nextInt(5).coerceAtMost(3)].defaultState.with(FACING, d).with(LIT, true).with(WATERLOGGED, false), 2)
                    }
                }
            } else {
                world.setBlockState(pos, to, 2)
            }
            c++
        }
        return c
    }

}