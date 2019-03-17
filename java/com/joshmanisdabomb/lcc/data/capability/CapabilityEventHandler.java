package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.network.PacketHeartsUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CapabilityEventHandler {

    public static final ResourceLocation GAUNTLET_CAPABILITY = new ResourceLocation(LCC.MODID, "gauntlet");
    public static final ResourceLocation HEARTS_CAPABILITY = new ResourceLocation(LCC.MODID, "hearts");

    @SubscribeEvent
    public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(GAUNTLET_CAPABILITY, new CapabilityGauntlet.CGauntletProvider());
        event.addCapability(HEARTS_CAPABILITY, new CapabilityHearts.CHeartsProvider());
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        EntityPlayer playerOriginal = event.getOriginal();
        EntityPlayer playerNew = event.getEntityPlayer();
        playerOriginal.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(heartsOriginal -> {
            playerNew.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(heartsNew -> {
                HeartsFunctionality.capabilityClone(heartsOriginal, heartsNew, playerOriginal, playerNew, event);
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP)playerNew), new PacketHeartsUpdate(heartsNew));
            });
        });
    }

    @SubscribeEvent
    public void onPlayerSpawn(PlayerRespawnEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player instanceof EntityPlayerMP) {
            player.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP) player), new PacketHeartsUpdate(hearts));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerSwitchDimension(PlayerChangedDimensionEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player instanceof EntityPlayerMP) {
            player.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP) player), new PacketHeartsUpdate(hearts));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player instanceof EntityPlayerMP) {
            player.getCapability(CapabilityHearts.CHeartsProvider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (EntityPlayerMP) player), new PacketHeartsUpdate(hearts));
            });
        }
    }

}
