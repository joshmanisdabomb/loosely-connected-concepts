package com.joshmanisdabomb.aimagg.world;

import java.util.Random;

import com.joshmanisdabomb.aimagg.data.OreIngotStorage;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class AimaggWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		//All OIS Ores
		OreIngotStorage.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		
		switch (world.provider.getDimension()) {
			case 0:
				this.generateSurface(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				break;
			case -1:
				break;
			case 1:
				break;	
		}
	}

	private void generateSurface(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		
	}

}
