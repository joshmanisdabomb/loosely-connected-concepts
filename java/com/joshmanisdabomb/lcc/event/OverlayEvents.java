package com.joshmanisdabomb.lcc.event;

import com.joshmanisdabomb.lcc.gui.overlay.LCCOverlay;
import com.joshmanisdabomb.lcc.gui.overlay.OverlayGauntlet;
import com.joshmanisdabomb.lcc.gui.overlay.OverlayHearts;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayEvents {

    public LCCOverlay gauntlet = new OverlayGauntlet();
    public LCCOverlay hearts = new OverlayHearts();

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if (!(Minecraft.getInstance().getRenderViewEntity() instanceof PlayerEntity)) {return;}
        PlayerEntity player = (PlayerEntity)Minecraft.getInstance().getRenderViewEntity();
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            this.gauntlet.draw(player, Minecraft.getInstance(), event);
        } else if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            this.hearts.draw(player, Minecraft.getInstance(), event);
        }
    }

}
