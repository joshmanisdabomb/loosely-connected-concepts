package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.world.feature.config.SmallGeodeFeatureConfig
import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.state.property.Properties.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.world.Heightmap
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.WorldAccess
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import java.util.*
import kotlin.random.asKotlinRandom

class SmallGeodeFeature(configCodec: Codec<SmallGeodeFeatureConfig>) : Feature<SmallGeodeFeatureConfig>(configCodec) {

    override fun generate(structureWorldAccess: StructureWorldAccess, chunkGenerator: ChunkGenerator?, random: Random, blockPos: BlockPos, config: SmallGeodeFeatureConfig): Boolean {
        val f = random.nextFloat() * 3.1415927f
        val g = config.size.toFloat() / 8.0f
        val i = MathHelper.ceil((config.size.toFloat() / 16.0f * 2.0f + 1.0f) / 2.0f)
        val d = blockPos.x.toDouble() + Math.sin(f.toDouble()) * g.toDouble()
        val e = blockPos.x.toDouble() - Math.sin(f.toDouble()) * g.toDouble()
        val h = blockPos.z.toDouble() + Math.cos(f.toDouble()) * g.toDouble()
        val j = blockPos.z.toDouble() - Math.cos(f.toDouble()) * g.toDouble()
        val k = 1
        val l = (blockPos.y + random.nextInt(3) - 2).toDouble()
        val m = (blockPos.y + random.nextInt(3) - 2).toDouble()
        val n = blockPos.x - MathHelper.ceil(g) - i
        val o = blockPos.y - 2 - i
        val p = blockPos.z - MathHelper.ceil(g) - i
        val q = 2 * (MathHelper.ceil(g) + i)
        val r = 2 * (2 + i)
        for (s in n..n + q) {
            for (t in p..p + q) {
                if (o <= structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) {
                    return generateVeinPart(structureWorldAccess, random, config, d, e, h, j, l, m, n, o, p, q, r)
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
                                            mutable.set(ah, aj, al)
                                            j = set(world.getBlockState(mutable), config.gem.defaultState, random, world, config, mutable, j)
                                            for (d in Direction.values()) {
                                                mutable.move(d)
                                                j = set(world.getBlockState(mutable), config.inner, random, world, config, mutable, j)
                                                mutable.move(d)
                                                j = set(world.getBlockState(mutable), config.outer, random, world, config, mutable, j)
                                                if (d.horizontal != -1) {
                                                    mutable.set(ah, aj, al).move(d).move(d.rotateYClockwise())
                                                    j = set(world.getBlockState(mutable), config.outer, random, world, config, mutable, j)
                                                    mutable.set(ah, aj, al).move(d).move(Direction.UP)
                                                    j = set(world.getBlockState(mutable), config.outer, random, world, config, mutable, j)
                                                    mutable.move(Direction.DOWN, 2)
                                                    j = set(world.getBlockState(mutable), config.outer, random, world, config, mutable, j)
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
        return j > 0
    }

    private fun set(state: BlockState, to: BlockState, random: Random, world: WorldAccess, config: SmallGeodeFeatureConfig, mutable: BlockPos.Mutable, changed: Int): Int {
        var c = changed
        if (state.isOf(config.gem) || state.isOf(config.bud)) return changed
        if (state == config.inner && !to.isOf(config.gem)) return changed
        if (state == config.outer && !to.isOf(config.gem) && to != config.inner) return changed
        if (state.isFullCube(world, mutable)) {
            if (to.isOf(config.gem) && random.nextInt(8) == 0) {
                world.setBlockState(mutable, config.bud.defaultState, 2)
                for (d in Direction.values()) {
                    mutable.move(d)
                    val state2 = world.getBlockState(mutable)
                    if (random.nextInt(2) == 0 && state2.isAir) {
                        world.setBlockState(mutable, config.bud.crystals.random(random.asKotlinRandom()).defaultState.with(FACING, d).with(LIT, true).with(WATERLOGGED, false), 2)
                    }
                    mutable.move(d.opposite)
                }
            } else {
                world.setBlockState(mutable, to, 2)
            }
            c++
        }
        return c
    }

}