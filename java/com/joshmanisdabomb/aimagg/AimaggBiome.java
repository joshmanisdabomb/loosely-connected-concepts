package com.joshmanisdabomb.aimagg;

import com.joshmanisdabomb.aimagg.biome.AimaggBiomeRainbowCandy;
import com.joshmanisdabomb.aimagg.biome.AimaggBiomeRainbowChocolate;
import com.joshmanisdabomb.aimagg.biome.AimaggBiomeRainbowFlower;
import com.joshmanisdabomb.aimagg.biome.AimaggBiomeRainbowStarlight;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public enum AimaggBiome {

	RAINBOW_CANDY(new AimaggBiomeRainbowCandy(new BiomeProperties("Rainbow (Candy)").setBaseHeight(0.03F).setHeightVariation(0.06F).setRainfall(0.1F).setWaterColor(0xFF0099).setTemperature(0.5F)), BiomeType.COOL, 1, false, false, true),
	RAINBOW_FLOWER(new AimaggBiomeRainbowFlower(new BiomeProperties("Rainbow (Flower)").setBaseHeight(0.03F).setHeightVariation(0.06F).setRainfall(0.1F).setWaterColor(0xFF0099).setTemperature(0.5F)), BiomeType.COOL, 1, false, false, false),
	RAINBOW_CHOCOLATE(new AimaggBiomeRainbowChocolate(new BiomeProperties("Rainbow (Chocolate)").setBaseHeight(0.03F).setHeightVariation(0.06F).setRainfall(0.1F).setWaterColor(0xFF0099).setTemperature(0.5F)), BiomeType.COOL, 1, false, false, false),
	RAINBOW_STARLIGHT(new AimaggBiomeRainbowStarlight(new BiomeProperties("Rainbow (Starlight)").setBaseHeight(0.03F).setHeightVariation(0.06F).setRainfall(0.1F).setWaterColor(0xFF0099).setTemperature(0.5F)), BiomeType.COOL, 1, false, false, false);

	private final Biome biome;
	private final BiomeType biomeType;

	private final boolean spawnInOverworld;
	private final boolean spawnStructuresHere;
	private final boolean canSpawnHere;
	private final int weight;

	AimaggBiome(Biome b, BiomeType bt, int weight, boolean spawnInOverworld, boolean spawnStructuresHere, boolean canSpawnHere) {
		this.biome = b;
		b.setRegistryName(this.name());
		this.biomeType = bt;
		this.weight = weight;
		this.spawnInOverworld = spawnInOverworld;
		this.spawnStructuresHere = spawnStructuresHere;
		this.canSpawnHere = canSpawnHere;
	}

	public Biome getBiome() {
		return this.biome;
	}

	public BiomeType getBiomeType() {
		return this.biomeType;
	}

	public int getWeight() {
		return this.weight;
	}
	
	public static void init() {
		for (AimaggBiome b : AimaggBiome.values()) {
			if (b.spawnInOverworld) {
				BiomeManager.addBiome(b.getBiomeType(), new BiomeEntry(b.getBiome(), b.getWeight()));
			}
	        if (b.canSpawnHere) {
	        	BiomeManager.addSpawnBiome(b.getBiome());
	        }
	        if (b.spawnStructuresHere) {
	        	BiomeManager.addStrongholdBiome(b.getBiome());
	        	BiomeManager.addVillageBiome(b.getBiome(), true);
	        }
		}
	}
	
}
