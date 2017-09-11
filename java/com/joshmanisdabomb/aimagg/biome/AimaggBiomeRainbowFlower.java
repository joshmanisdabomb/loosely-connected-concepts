package com.joshmanisdabomb.aimagg.biome;

import java.util.Random;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class AimaggBiomeRainbowFlower extends AimaggBiomeRainbow {

	public AimaggBiomeRainbowFlower(BiomeProperties properties) {
		super(properties);
        this.decorator = new BiomeRainbowFlowerDecorator();
	}

	public static class BiomeRainbowFlowerDecorator extends BiomeDecorator {

		public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
			
	    }
		
	}

}
