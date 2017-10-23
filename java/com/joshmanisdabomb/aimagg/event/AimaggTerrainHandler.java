package com.joshmanisdabomb.aimagg.event;

import com.joshmanisdabomb.aimagg.biome.AimaggBiomeWasteland;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;
import net.minecraftforge.event.terraingen.WorldTypeEvent.InitBiomeGens;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimaggTerrainHandler {
	
	@SubscribeEvent
	public void onPopulate(PopulateChunkEvent.Populate e) {
		Biome biome = e.getWorld().getBiome(new BlockPos(e.getChunkX() + 8,0,e.getChunkZ() + 8));
		if ((e.getType() == EventType.LAKE || e.getType() == EventType.LAVA) && AimaggBiomeWasteland.isWastelandBiome(biome)) {
			e.setResult(Result.DENY);
		}
	}

}
