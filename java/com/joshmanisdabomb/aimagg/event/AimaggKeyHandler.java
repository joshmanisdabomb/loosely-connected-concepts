package com.joshmanisdabomb.aimagg.event;

import org.lwjgl.input.Keyboard;

import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityPlayerRainbowPadRender;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketLaunchPadLaunchServer;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketRainbowPadTeleportServer;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class AimaggKeyHandler {

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
			if (AimaggEntityPlayerRainbowPadRender.isOnActivePad(Minecraft.getMinecraft().player)) {
				AimaggPacketRainbowPadTeleportServer packet = new AimaggPacketRainbowPadTeleportServer();
				AimaggPacketHandler.INSTANCE.sendToServer(packet);
			}
		}
	}
	
}
