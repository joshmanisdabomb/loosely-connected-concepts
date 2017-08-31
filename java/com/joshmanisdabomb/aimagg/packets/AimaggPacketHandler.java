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
		//side means side the packet is delivered to
		INSTANCE.registerMessage(AimaggPacketCapabilityHearts.Handler.class, AimaggPacketCapabilityHearts.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(AimaggPacketCapabilityPills.Handler.class, AimaggPacketCapabilityPills.class, nextID(), Side.CLIENT);
		
		INSTANCE.registerMessage(AimaggPacketLaunchPadText.Handler.class, AimaggPacketLaunchPadText.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AimaggPacketLaunchPadLaunch.Handler.class, AimaggPacketLaunchPadLaunch.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AimaggPacketLaunchPadMissileRenderRequest.Handler.class, AimaggPacketLaunchPadMissileRenderRequest.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AimaggPacketLaunchPadMissileRenderResponse.Handler.class, AimaggPacketLaunchPadMissileRenderResponse.class, nextID(), Side.CLIENT);
		
		INSTANCE.registerMessage(AimaggPacketMovement.Handler.class, AimaggPacketMovement.class, nextID(), Side.CLIENT);
		
		INSTANCE.registerMessage(AimaggPacketRainbowPadTeleport.Handler.class, AimaggPacketRainbowPadTeleport.class, nextID(), Side.SERVER);
	}
}
