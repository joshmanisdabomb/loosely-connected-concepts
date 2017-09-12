package com.joshmanisdabomb.aimagg.gen;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggDimension;
import com.joshmanisdabomb.aimagg.util.OreIngotStorage;

import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

public class AimaggWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) {
			this.generateSurface(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if (world.provider.getDimension() == AimaggDimension.RAINBOW.getDimensionID()) {
			this.generateRainbow(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}

	private void generateSurface(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		Biome chunkBiome = world.getBiome(new BlockPos((chunkX * 16) + 8,64,(chunkZ * 16) + 8));
		
		//Ruby Ore
		if (chunkBiome == Biomes.OCEAN || chunkBiome == Biomes.DEEP_OCEAN) {
			for (int i = 0; i < 4; i++) {
				int x = (chunkX * 16) + random.nextInt(16);
				int z = (chunkZ * 16) + random.nextInt(16);
				int y = random.nextInt(48);
				new WorldGenMinable(OreIngotStorage.RUBY.getOreBlockState(), 7)
				.generate(world, random, new BlockPos(x,y,z));
			}
		}
		
		//Topaz Ore
		if (chunkBiome == Biomes.RIVER) {
			for (int i = 0; i < 12; i++) {
				int x = (chunkX * 16) + random.nextInt(16);
				int z = (chunkZ * 16) + random.nextInt(16);
				int y = 45 + random.nextInt(15);
				new WorldGenMinable(OreIngotStorage.TOPAZ.getOreBlockState(), 7)
				.generate(world, random, new BlockPos(x,y,z));
			}
		}
		
		//Sapphire Ore
		if (BiomeDictionary.hasType(chunkBiome, BiomeDictionary.Type.DRY)) {
			for (int i = 0; i < 3; i++) {
				int x = (chunkX * 16) + random.nextInt(16);
				int z = (chunkZ * 16) + random.nextInt(16);
				int y = 50 + random.nextInt(16);
				new WorldGenMinable(OreIngotStorage.SAPPHIRE.getOreBlockState(), 7)
				.generate(world, random, new BlockPos(x,y,z));
			}
		}
		
		//Amethyst Ore
		if (BiomeDictionary.hasType(chunkBiome, BiomeDictionary.Type.JUNGLE) || BiomeDictionary.hasType(chunkBiome, BiomeDictionary.Type.SPOOKY)) {
			for (int i = 0; i < 6; i++) {
				int x = (chunkX * 16) + random.nextInt(16);
				int z = (chunkZ * 16) + random.nextInt(16);
				int y = random.nextInt(96);
				new WorldGenMinable(OreIngotStorage.AMETHYST.getOreBlockState(), 7)
				.generate(world, random, new BlockPos(x,y,z));
			}
		}
		
		//Uranium Ore
		int x = (chunkX * 16) + random.nextInt(16);
		int z = (chunkZ * 16) + random.nextInt(16);
		int y = random.nextInt(32);
		new WorldGenMinable(OreIngotStorage.URANIUM.getOreBlockState(), 5)
		.generate(world, random, new BlockPos(x,y,z));
	}

	private void generateRainbow(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
	}

}
