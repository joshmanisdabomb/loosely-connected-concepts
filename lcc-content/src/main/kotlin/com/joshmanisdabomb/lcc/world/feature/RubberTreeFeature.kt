package com.joshmanisdabomb.lcc.world.feature

import com.google.common.collect.Sets
import com.joshmanisdabomb.lcc.extensions.horizontalDirections
import com.joshmanisdabomb.lcc.extensions.toInt
import com.mojang.serialization.Codec
import net.minecraft.block.Blocks
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.Heightmap
import net.minecraft.world.TestableWorld
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.TreeFeature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import java.util.*

class RubberTreeFeature(codec: Codec<TreeFeatureConfig>) : Feature<TreeFeatureConfig>(codec) {

    fun generateTree(context: FeatureContext<TreeFeatureConfig>, r: Int, branches: Map<Direction, IntRange>, blockPos2: BlockPos) {
        context.config.trunkPlacer.generate(context.world, context.random, r, blockPos2, Sets.newHashSet(), BlockBox.empty(), context.config)
        horizontalDirections.forEach {
            for (i in -1..0) {
                val pos6 = blockPos2.add(0, r+i, 0).offset(it)
                setBlockState(context.world, pos6, context.config.leavesProvider.getBlockState(context.random, pos6).with(Properties.DISTANCE_1_7, 1))
            }
        }
        for (i in 1..2) {
            val pos5 = blockPos2.up(r+i)
            setBlockState(context.world, pos5, context.config.leavesProvider.getBlockState(context.random, pos5).with(Properties.DISTANCE_1_7, i))
        }
        branches.forEach { (k, v) ->
            val pos = blockPos2.up(r).offset(k).offset(k.rotateYClockwise())
            for (i in v) {
                val pos2 = pos.add(0, i, 0)
                if (TreeFeature.canReplace(context.world, pos2)) setBlockState(context.world, pos2, context.config.trunkProvider.getBlockState(context.random, pos2))
                val flag = context.random.nextInt(2) == 0
                horizontalDirections.forEach {
                    if (i >= 0 || (i == -1 && context.random.nextInt(2) == 0) || (flag && v.first > 0 && i == -2 && context.random.nextInt(2) == 0)) {
                        val pos3 = pos2.offset(it)
                        if (TreeFeature.canReplace(context.world, pos3)) setBlockState(context.world, pos3, context.config.leavesProvider.getBlockState(context.random, pos3).with(Properties.DISTANCE_1_7, 1))
                    }
                }
                if (i == v.last) {
                    val pos4 = pos2.add(0, 1, 0)
                    if (TreeFeature.canReplace(context.world, pos4)) setBlockState(context.world, pos2.add(0, 1, 0), context.config.leavesProvider.getBlockState(context.random, pos2.offset(k)).with(Properties.DISTANCE_1_7, 1))
                }
            }
        }
    }

    override fun generate(context: FeatureContext<TreeFeatureConfig>): Boolean {
        val i: Int = context.config.trunkPlacer.getHeight(context.random)
        val blockPos2: BlockPos
        var r: Int
        if (!context.config.skipFluidCheck) {
            val m: Int = context.world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, context.origin).y
            r = context.world.getTopPosition(Heightmap.Type.WORLD_SURFACE, context.origin).y
            if (r - m > context.config.maxWaterDepth) {
                return false
            }
            val q: Int
            q = if (context.config.heightmap == Heightmap.Type.OCEAN_FLOOR) {
                m
            } else if (context.config.heightmap == Heightmap.Type.WORLD_SURFACE) {
                r
            } else {
                context.world.getTopPosition(context.config.heightmap, context.origin).y
            }
            blockPos2 = BlockPos(context.origin.x, q, context.origin.z)
        } else {
            blockPos2 = context.origin
        }

        return if (blockPos2.y >= context.world.bottomY + 1 && blockPos2.y + i + 1 <= context.world.topY) {
            if (!context.world.testBlockState(context.origin.down()) { isSoil(it) || it.isOf(Blocks.FARMLAND) }) {
                false
            } else {
                val optionalInt: OptionalInt = context.config.minimumSize.minClippedHeight
                val branches = horizontalDirections.filter { context.random.nextInt(12) != 0 }.map { it to (-context.random.nextInt((i > 6).toInt(3, 2))..context.random.nextInt(3)) }.toMap()
                r = this.getTopPosition(context.world, i, branches, blockPos2, context.config)
                if (r >= i + branches.maxHeight() || (optionalInt.isPresent && r >= optionalInt.asInt)) {
                    generateTree(context, i, branches, blockPos2)
                    true
                } else {
                    false
                }
            }
        } else {
            false
        }
    }

    private fun getTopPosition(world: TestableWorld, height: Int, branches: Map<Direction, IntRange>, pos: BlockPos, config: TreeFeatureConfig): Int {
        val mutable = BlockPos.Mutable()
        for (i in 0..height + branches.maxHeight() + 5) {
            val j = config.minimumSize.getRadius(height, i)
            for (k in -j..j) {
                for (l in -j..j) {
                    mutable.set(pos, k, i, l)
                    if (!TreeFeature.canTreeReplace(world, mutable) || !config.ignoreVines && world.testBlockState(mutable) { it.isOf(Blocks.VINE) }) {
                        return i - 2
                    }
                }
            }
        }
        return height + branches.maxHeight() + 5
    }

}

private fun Map<Direction, IntRange>.maxHeight() = this.maxOfOrNull { it.value.last } ?: 0