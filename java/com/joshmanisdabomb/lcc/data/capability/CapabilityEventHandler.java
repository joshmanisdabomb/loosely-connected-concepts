package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityEventHandler {

    public static final ResourceLocation GAUNTLET_CAPABILITY = new ResourceLocation(LCC.MODID, "gauntlet");

    @SubscribeEvent
    public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(GAUNTLET_CAPABILITY, new CapabilityGauntlet.CGauntletProvider());
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {

    }

    @SubscribeEvent
    public void onPlayerSpawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event) {
        if (event.getPlayer() instanceof EntityPlayerMP) {

        }
    }

    @SubscribeEvent
    public void onPlayerSwitchDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer() instanceof EntityPlayerMP) {

        }
    }

    @SubscribeEvent
    public void onPlayerJoin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof EntityPlayerMP) {

        }
    }

}
