package com.joshmanisdabomb.lcc.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public interface LCCOverlay {

    void draw(PlayerEntity player, Minecraft minecraft, RenderGameOverlayEvent.Post event);

}
