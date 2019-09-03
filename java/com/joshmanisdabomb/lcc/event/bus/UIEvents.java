package com.joshmanisdabomb.lcc.event.bus;

import com.joshmanisdabomb.lcc.data.capability.CryingObsidianCapability;
import com.joshmanisdabomb.lcc.gui.overlay.GauntletOverlay;
import com.joshmanisdabomb.lcc.gui.overlay.HeartsOverlay;
import com.joshmanisdabomb.lcc.gui.overlay.LCCOverlay;
import com.joshmanisdabomb.lcc.network.CryingObsidianSpawnPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class UIEvents {

    public LCCOverlay gauntlet = new GauntletOverlay();
    public LCCOverlay hearts = new HeartsOverlay();

    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent.Post e) {
        if (!(Minecraft.getInstance().getRenderViewEntity() instanceof PlayerEntity)) {return;}
        PlayerEntity player = (PlayerEntity)Minecraft.getInstance().getRenderViewEntity();
        if (e.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            this.gauntlet.draw(player, Minecraft.getInstance(), e);
        } else if (e.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            this.hearts.draw(player, Minecraft.getInstance(), e);
        }
    }

    @SubscribeEvent
    public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post e) {
        if (e.getGui() instanceof DeathScreen && !Minecraft.getInstance().world.getWorldInfo().isHardcore()) {
            Minecraft.getInstance().player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                if (co.pos != null) {
                    e.getWidgetList().stream().filter(widget -> e.getWidgetList().indexOf(widget) != 0).forEach(widget -> widget.y += 24);

                    Button coRespawn = new Button(e.getGui().width / 2 - 100, e.getGui().height / 4 + 96, 200, 20, I18n.format("deathScreen.lcc.respawn_crying_obsidian"), button -> {
                        LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new CryingObsidianSpawnPacket(Minecraft.getInstance().player.getUniqueID()));
                        Minecraft.getInstance().displayGuiScreen(null);
                    });
                    e.addWidget(coRespawn);
                    coRespawn.active = false;
                }
            });
        }
    }

}
