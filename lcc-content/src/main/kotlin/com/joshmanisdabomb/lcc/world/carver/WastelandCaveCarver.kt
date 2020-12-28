package com.joshmanisdabomb.lcc.world.carver

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.gen.ProbabilityConfig
import net.minecraft.world.gen.carver.CaveCarver
import org.apache.commons.lang3.mutable.MutableBoolean
import java.util.*
import java.util.function.Function

class WastelandCaveCarver(codec: Codec<ProbabilityConfig>, height: Int) : CaveCarver(codec, height) {

    override fun getBranchFactor() = 8

    override fun getMaxCaveCount() = 32

    override fun getTunnelSystemWidth(random: Random) = random.nextFloat() * 0.5f

    override fun getTunnelSystemHeightWidthRatio() = 2.0

    override fun canCarveBlock(state: BlockState, stateAbove: BlockState) = super.canCarveBlock(state, stateAbove) || state.isOf(LCCBlocks.cracked_mud)

    override fun carveAtPoint(chunk: Chunk, posToBiome: Function<BlockPos, Biome>, carvingMask: BitSet, random: Random, mutable: BlockPos.Mutable, mutable2: BlockPos.Mutable, mutable3: BlockPos.Mutable, seaLevel: Int, mainChunkX: Int, mainChunkZ: Int, x: Int, z: Int, relativeX: Int, y: Int, relativeZ: Int, mutableBoolean: MutableBoolean): Boolean {
        val i = relativeX or (relativeZ shl 4) or (y shl 8)
        return if (carvingMask[i]) {
            false
        } else {
            carvingMask.set(i)
            mutable[x, y] = z
            val blockState = chunk.getBlockState(mutable)
            val blockState2 = chunk.getBlockState(mutable2.set(mutable, Direction.UP))
            if (blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isOf(Blocks.MYCELIUM)) {
                mutableBoolean.setTrue()
            }
            if (!canCarveBlock(blockState, blockState2)) {
                false
            } else {
                chunk.setBlockState(mutable, CAVE_AIR, false)
                if (mutableBoolean.isTrue) {
                    mutable3[mutable] = Direction.DOWN
                    if (chunk.getBlockState(mutable3).isOf(Blocks.DIRT)) {
                        chunk.setBlockState(mutable3, posToBiome.apply(mutable).generationSettings.surfaceConfig.topMaterial, false)
                    }
                }
                true
            }
        }
    }

}