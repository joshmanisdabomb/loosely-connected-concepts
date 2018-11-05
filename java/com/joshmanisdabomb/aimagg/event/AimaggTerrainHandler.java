package com.joshmanisdabomb.aimagg.event;

import com.joshmanisdabomb.aimagg.AimaggBiome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimaggTerrainHandler {
	
	@SubscribeEvent
	public void onChunkPopulate(PopulateChunkEvent.Populate e) {		
		Biome biome = e.getWorld().getBiome(new BlockPos((e.getChunkX() * 16) + 8,0,(e.getChunkZ() * 16) + 8));
		if (AimaggBiome.comparison(biome, AimaggBiome.WASTELAND.getBiome())) {
			if ((e.getType() == EventType.LAKE || e.getType() == EventType.LAVA)) {
				e.setResult(Result.DENY);
			}
		}
	}

}
