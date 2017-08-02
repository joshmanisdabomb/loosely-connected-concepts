package com.joshmanisdabomb.aimagg.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AimaggOverlayHandler {

	public AimaggOverlayHearts guiHearts = new AimaggOverlayHearts();
	
	@SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if (!(Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)) {return;}
		EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
		if (event.getType() == ElementType.EXPERIENCE) {
			this.guiHearts.draw(player, Minecraft.getMinecraft(), event);
		}
    }
	
}
