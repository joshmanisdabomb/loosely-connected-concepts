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
		INSTANCE.registerMessage(AimaggPacketCapabilityHeartsClient.Handler.class, AimaggPacketCapabilityHeartsClient.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(AimaggPacketCapabilityPillsClient.Handler.class, AimaggPacketCapabilityPillsClient.class, nextID(), Side.CLIENT);
		
		INSTANCE.registerMessage(AimaggPacketLaunchPadTextServer.Handler.class, AimaggPacketLaunchPadTextServer.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AimaggPacketLaunchPadLaunchServer.Handler.class, AimaggPacketLaunchPadLaunchServer.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AimaggPacketLaunchPadUpdateRequestServer.Handler.class, AimaggPacketLaunchPadUpdateRequestServer.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AimaggPacketLaunchPadUpdateResponseClient.Handler.class, AimaggPacketLaunchPadUpdateResponseClient.class, nextID(), Side.CLIENT);
		
		INSTANCE.registerMessage(AimaggPacketComputerCasePowerServer.Handler.class, AimaggPacketComputerCasePowerServer.class, nextID(), Side.SERVER);
		
		INSTANCE.registerMessage(AimaggPacketBouncePadTextServer.Handler.class, AimaggPacketBouncePadTextServer.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(AimaggPacketBouncePadTextClient.Handler.class, AimaggPacketBouncePadTextClient.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(AimaggPacketBouncePadExtensionClient.Handler.class, AimaggPacketBouncePadExtensionClient.class, nextID(), Side.CLIENT);
		
		INSTANCE.registerMessage(AimaggPacketMovementClient.Handler.class, AimaggPacketMovementClient.class, nextID(), Side.CLIENT);
		
		INSTANCE.registerMessage(AimaggPacketRainbowPadTeleportServer.Handler.class, AimaggPacketRainbowPadTeleportServer.class, nextID(), Side.SERVER);
	}
}
