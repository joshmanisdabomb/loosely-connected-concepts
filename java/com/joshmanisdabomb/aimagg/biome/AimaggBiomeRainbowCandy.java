package com.joshmanisdabomb.aimagg.biome;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.biome.AimaggBiomeRainbowCandy.BiomeRainbowCandyDecorator;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass.RainbowGrassType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld.RainbowWorldType;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;

public class AimaggBiomeRainbowCandy extends AimaggBiomeRainbow {

	public AimaggBiomeRainbowCandy(BiomeProperties properties) {
		super(properties);
        this.decorator = new BiomeRainbowCandyDecorator();
        this.topBlock = AimaggBlocks.rainbowGrass.getDefaultState().withProperty(AimaggBlockRainbowGrass.TYPE, RainbowGrassType.SUGAR);
	}

	public static class BiomeRainbowCandyDecorator extends BiomeDecorator {

		public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
			
	    }
		
	}

}
