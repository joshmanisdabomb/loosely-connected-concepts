package com.joshmanisdabomb.aimagg.biome;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass.RainbowGrassType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld.RainbowWorldType;
import com.joshmanisdabomb.aimagg.dimension.rainbow.AimaggChunkGeneratorRainbow;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

public abstract class AimaggBiomeRainbow extends Biome {

	public AimaggBiomeRainbow(BiomeProperties properties) {
		super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.topBlock = AimaggBlocks.rainbowGrass.getDefaultState().withProperty(AimaggBlockRainbowGrass.TYPE, RainbowGrassType.RAINBOW);
        this.fillerBlock = AimaggBlocks.rainbowWorld.getDefaultState().withProperty(AimaggBlockRainbowWorld.TYPE, RainbowWorldType.DIRT);
	}
	
	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {		
		int i = worldIn.getSeaLevel();
		IBlockState top = this.topBlock;
		IBlockState filler = this.fillerBlock;
		int j = -1;
		int k = (int) (noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
		int l = x & 15;
		int i1 = z & 15;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int j1 = 255; j1 >= 0; --j1) {
			if (j1 <= rand.nextInt(5)) {
				chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
			} else {				
				IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

				if (iblockstate2.getMaterial() == Material.AIR) {
					j = -1;
				} else if (iblockstate2.equals(AimaggChunkGeneratorRainbow.RAINBOW_STONE)) {
					if (j == -1) {
						if (j1 >= i - 4 && j1 <= i + 1) {
							top = this.topBlock;
							filler = this.fillerBlock;
						}

						j = k;

						chunkPrimerIn.setBlockState(i1, j1, l, top);
					} else if (j > 0) {
						--j;
						chunkPrimerIn.setBlockState(i1, j1, l, filler);
					}
				}
			}
		}
	}

}
