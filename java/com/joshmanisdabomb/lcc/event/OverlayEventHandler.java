package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.gui.overlay.LCCOverlay;
import com.joshmanisdabomb.lcc.gui.overlay.OverlayGauntlet;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayEventHandler {

    public LCCOverlay gauntlet = new OverlayGauntlet();

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if (!(Minecraft.getInstance().getRenderViewEntity() instanceof EntityPlayer)) {return;}
        EntityPlayer player = (EntityPlayer)Minecraft.getInstance().getRenderViewEntity();
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            this.gauntlet.draw(player, Minecraft.getInstance(), event);
        }
    }

}
