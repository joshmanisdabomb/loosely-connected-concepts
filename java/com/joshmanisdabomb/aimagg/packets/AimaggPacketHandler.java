package com.joshmanisdabomb.aimagg.packets;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class AimaggPacketHandler {
	
	private static int packetID = 0;
	
	public static SimpleNetworkWrapper INSTANCE = null;
	
	public static int nextID() {
		return packetID++;
	}
	
	public static void registerMessages(String channelName) {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	public static void registerMessages() {
		INSTANCE.registerMessage(AimaggPacketLaunchPadText.Handler.class, AimaggPacketLaunchPadText.class, nextID(), Side.SERVER);
	}
}
