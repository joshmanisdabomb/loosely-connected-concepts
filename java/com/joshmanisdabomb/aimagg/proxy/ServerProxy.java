package com.joshmanisdabomb.aimagg.proxy;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;

public class ServerProxy implements CommonProxy {

	@Override
	public void preInit() {
        AimaggPacketHandler.registerMessages(Constants.MOD_ID);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
