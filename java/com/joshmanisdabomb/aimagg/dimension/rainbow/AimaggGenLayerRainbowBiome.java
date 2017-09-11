package com.joshmanisdabomb.aimagg.dimension.rainbow;

import java.util.ArrayList;
import java.util.List;

import com.joshmanisdabomb.aimagg.AimaggBiome;

import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class AimaggGenLayerRainbowBiome extends GenLayerBiome {

	private List<BiomeEntry>[] biomes = new ArrayList[BiomeType.values().length];
	
	public AimaggGenLayerRainbowBiome(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, ChunkGeneratorSettings p_i45560_5_) {
		super(p_i45560_1_, p_i45560_3_, p_i45560_4_, p_i45560_5_);
		
		System.out.println("hello?");
		
		for (BiomeType type : BiomeType.values()) {
            biomes[type.ordinal()] = new ArrayList<BiomeEntry>();
            biomes[type.ordinal()].add(new BiomeEntry(AimaggBiome.RAINBOW_CANDY.getBiome(), 20));
            biomes[type.ordinal()].add(new BiomeEntry(AimaggBiome.RAINBOW_FLOWER.getBiome(), 20));
            biomes[type.ordinal()].add(new BiomeEntry(AimaggBiome.RAINBOW_CHOCOLATE.getBiome(), 20));
            biomes[type.ordinal()].add(new BiomeEntry(AimaggBiome.RAINBOW_STARLIGHT.getBiome(), 20));
        }
	}
	
	@Override
	public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {		
		int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
		int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

		for (int i = 0; i < areaHeight; ++i) {
			for (int j = 0; j < areaWidth; ++j) {
				this.initChunkSeed((long) (j + areaX), (long) (i + areaY));
				int k = aint[j + i * areaWidth];
				k = (k % 4) + 1;
				
				if (k == 1) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.DESERT).biome);
				} else if (k == 2) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.WARM).biome);
				} else if (k == 3) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.COOL).biome);
				} else if (k == 4) {
					aint1[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(net.minecraftforge.common.BiomeManager.BiomeType.ICY).biome);
				}
			}
		}

		return aint1;
	}
	
	@Override
	protected BiomeEntry getWeightedBiomeEntry(BiomeType type) {
        List<BiomeEntry> biomeList = biomes[type.ordinal()];
        int totalWeight = WeightedRandom.getTotalWeight(biomeList);
        int weight = BiomeManager.isTypeListModded(type)?nextInt(totalWeight):nextInt(totalWeight / 10) * 10;
        return (BiomeEntry)WeightedRandom.getRandomItem(biomeList, weight);
    }

}
