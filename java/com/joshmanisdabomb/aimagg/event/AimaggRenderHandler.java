package com.joshmanisdabomb.aimagg.event;

import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.entity.render.AimaggEntityPlayerRainbowPadRender;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimaggRenderHandler {

	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Pre event) {
		if (AimaggEntityPlayerRainbowPadRender.isOnActivePad(event.getEntityPlayer())) {
			AimaggEntityPlayerRainbowPadRender.getInstance(event.getRenderer().getRenderManager(), ((AbstractClientPlayer)event.getEntityPlayer()).getSkinType().equals("slim")).doRender((AbstractClientPlayer)event.getEntityPlayer(), event.getX(), event.getY(), event.getZ(), event.getEntityPlayer().rotationYaw, event.getPartialRenderTick());
			event.setCanceled(true);
		}
	}	

	@SubscribeEvent
	public void onHandEvent(RenderSpecificHandEvent event) {
		if (event.getItemStack().getItem() == AimaggItems.gauntlet) {
			event.setCanceled(true);
		}
	}
	
}
