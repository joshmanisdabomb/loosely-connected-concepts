package com.joshmanisdabomb.aimagg.proxy;

import com.joshmanisdabomb.aimagg.AimaggItems;

public class ClientProxy implements CommonProxy {

	@Override
	public void init() {
		AimaggItems.registerRenders();
	}

}
