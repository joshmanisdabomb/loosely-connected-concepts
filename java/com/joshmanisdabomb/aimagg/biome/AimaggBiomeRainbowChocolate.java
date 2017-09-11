package com.joshmanisdabomb.aimagg.biome;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass.RainbowGrassType;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class AimaggBiomeRainbowChocolate extends AimaggBiomeRainbow {

	public AimaggBiomeRainbowChocolate(BiomeProperties properties) {
		super(properties);
        this.decorator = new BiomeRainbowChocolateDecorator();
        this.topBlock = AimaggBlocks.rainbowGrass.getDefaultState().withProperty(AimaggBlockRainbowGrass.TYPE, RainbowGrassType.CHOCOLATE);
	}

	public static class BiomeRainbowChocolateDecorator extends BiomeDecorator {

		public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
			
	    }
		
	}

}
