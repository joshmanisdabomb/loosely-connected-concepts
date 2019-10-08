package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.network.CryingObsidianUpdatePacket;
import com.joshmanisdabomb.lcc.network.HeartsUpdatePacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CapabilityEvents {

    public static final ResourceLocation GAUNTLET_CAPABILITY = new ResourceLocation(LCC.MODID, "gauntlet");
    public static final ResourceLocation HEARTS_CAPABILITY = new ResourceLocation(LCC.MODID, "hearts");
    public static final ResourceLocation SPREADER_CAPABILITY = new ResourceLocation(LCC.MODID, "spreader");
    public static final ResourceLocation CRYING_OBSIDIAN_CAPABILITY = new ResourceLocation(LCC.MODID, "crying_obsidian");

    @SubscribeEvent
    public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(GAUNTLET_CAPABILITY, new GauntletCapability.Provider());
            event.addCapability(HEARTS_CAPABILITY, new HeartsCapability.Provider());
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(CRYING_OBSIDIAN_CAPABILITY, new CryingObsidianCapability.Provider());
            }
        }
    }

    @SubscribeEvent
    public void attachCapabilityToWorld(AttachCapabilitiesEvent<World> event) {
        //global capabilities tied to just overworld
        if (event.getObject().getDimension().getType() == GlobalProvider.DIMENSION) {
            event.addCapability(SPREADER_CAPABILITY, new SpreaderCapability.Provider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        PlayerEntity playerOriginal = event.getOriginal();
        PlayerEntity playerNew = event.getEntityPlayer();
        playerOriginal.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(heartsOriginal -> {
            playerNew.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(heartsNew -> {
                HeartsFunctionality.capabilityClone(heartsOriginal, heartsNew, playerOriginal, playerNew, event);
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)playerNew), new HeartsUpdatePacket(heartsNew));
            });
        });
        playerOriginal.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(coOriginal -> {
            playerNew.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(coNew -> {
                coNew.pos = coOriginal.pos;
                coNew.dimension = coOriginal.dimension;
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)playerNew), new CryingObsidianUpdatePacket(coNew));
            });
        });
    }

    @SubscribeEvent
    public void onPlayerSpawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new HeartsUpdatePacket(hearts));
            });
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CryingObsidianUpdatePacket(co));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerSwitchDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new HeartsUpdatePacket(hearts));
            });
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CryingObsidianUpdatePacket(co));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new HeartsUpdatePacket(hearts));
            });
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CryingObsidianUpdatePacket(co));
            });
        }
    }

}
