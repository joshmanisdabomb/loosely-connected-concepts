package com.joshmanisdabomb.lcc.world.feature

import com.joshmanisdabomb.lcc.block.OilBlock.Companion.GEYSER
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.toInt
import com.joshmanisdabomb.lcc.world.GenUtils
import com.mojang.serialization.Codec
import net.minecraft.block.FluidBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.feature.DefaultFeatureConfig
import net.minecraft.world.gen.feature.Feature
import java.util.*

class OilGeyserFeature(configCodec: Codec<DefaultFeatureConfig>) : Feature<DefaultFeatureConfig>(configCodec) {

    override fun generate(world: StructureWorldAccess, chunkGenerator: ChunkGenerator, random: Random, pos: BlockPos, config: DefaultFeatureConfig): Boolean {
        val height = 4 + random.nextInt(4)

        if (!GenUtils.areaMatches(world, pos.x, pos.y, pos.z, height = height.minus(1)) { it.y < world.height }) return false
        if (!GenUtils.areaMatches(world, pos.x, pos.y - 2, pos.z, ex = 3, ez = 3, height = 1) { world.getBlockState(it).isOf(LCCBlocks.cracked_mud) }) return false
        if (!GenUtils.areaMatches(world, pos.x, pos.y, pos.z, ex = 2, ez = 2, height = height.minus(1))) return false

        val bp = BlockPos.Mutable()
        for (i in height.minus(1) downTo -1) {
            world.setBlockState(bp.set(pos).move(0, i, 0), LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, (i < height.minus(1) && i > -1).toInt(8)).with(GEYSER, i > -1), 18)
            for (j in 0..3) {
                val direction = Direction.fromHorizontal(j)
                world.setBlockState(bp.offset(direction), LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, (i == height.minus(1)).toInt(7, (i == -1).toInt(0, 8))).with(GEYSER, i > -1), 18)
                if (i == 0) {
                    world.setBlockState(bp.offset(direction, 2), LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, 7), 18)
                    world.setBlockState(bp.add(if (j % 2 == 0) 1 else -1, 0, if (j / 2 == 0) 1 else -1), LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, 7), 18)
                } else if (i == -1) {
                    if (random.nextInt(2) == 0) world.setBlockState(bp.offset(direction, 2), LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, 0), 18)
                    if (random.nextInt(2) == 0) world.setBlockState(bp.add(if (j % 2 == 0) 1 else -1, 0, if (j / 2 == 0) 1 else -1), LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, 0), 18)
                }
            }
        }

        for (x in -2..2) {
            for (z in -2..2) {
                if (random.nextInt(3) == 0) world.setBlockState(bp.set(pos).move(x, -1, z), LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, 0), 18)
            }
        }

        return true
    }

}