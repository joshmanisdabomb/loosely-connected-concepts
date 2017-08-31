package com.joshmanisdabomb.aimagg.event;

import org.lwjgl.input.Keyboard;

import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityPlayerRainbowPadRender;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketLaunchPadLaunch;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketRainbowPadTeleport;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class AimaggKeyHandler {

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isKeyDown()) {
			if (AimaggEntityPlayerRainbowPadRender.isOnActivePad(Minecraft.getMinecraft().player)) {
				AimaggPacketRainbowPadTeleport packet = new AimaggPacketRainbowPadTeleport();
				AimaggPacketHandler.INSTANCE.sendToServer(packet);
			}
		}
	}
	
}
