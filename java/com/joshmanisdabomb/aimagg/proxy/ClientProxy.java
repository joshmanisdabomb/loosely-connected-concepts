package com.joshmanisdabomb.aimagg.proxy;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggEntities;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.event.AimaggKeyHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements CommonProxy {

	@Override
	public void preInitBeforeCommon(FMLPreInitializationEvent event) {
		AimaggBlocks.registerAdvancedRenders();
	}

	@Override
	public void preInitAfterCommon(FMLPreInitializationEvent event) {
		AimaggEntities.initModels();
	}

	@Override
	public void initBeforeCommon(FMLInitializationEvent event) {
		
	}

	@Override
	public void initAfterCommon(FMLInitializationEvent event) {
		AimaggItems.registerColoring();
		AimaggBlocks.registerTileEntityRenderers();
		MinecraftForge.EVENT_BUS.register(new AimaggKeyHandler());
	}

	@Override
	public void postInitBeforeCommon(FMLPostInitializationEvent event) {
		
	}

	@Override
	public void postInitAfterCommon(FMLPostInitializationEvent event) {
		
	}

}
