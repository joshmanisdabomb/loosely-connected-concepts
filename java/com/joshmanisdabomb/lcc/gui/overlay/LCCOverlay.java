package com.joshmanisdabomb.lcc.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public interface LCCOverlay {

    void draw(EntityPlayer player, Minecraft minecraft, RenderGameOverlayEvent.Post event);

}
