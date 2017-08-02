package com.joshmanisdabomb.aimagg.proxy;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggEntities;
import com.joshmanisdabomb.aimagg.AimaggItems;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		AimaggEntities.initModels();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		AimaggItems.registerColoring();
		AimaggBlocks.registerTileEntityRenderers();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

}
