package com.joshmanisdabomb.aimagg.proxy;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggEntities;
import com.joshmanisdabomb.aimagg.AimaggItems;

public class ClientProxy implements CommonProxy {

	@Override
	public void preInit() {
		AimaggEntities.initModels();
		AimaggItems.customModelResourceLocations();
	}

	@Override
	public void init() {
		AimaggItems.registerRenders();
		AimaggBlocks.registerRenders();
	}

}
