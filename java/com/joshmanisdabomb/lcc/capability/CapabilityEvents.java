package com.joshmanisdabomb.lcc.capability;

import com.joshmanisdabomb.lcc.functionality.HeartsFunctionality;
import com.joshmanisdabomb.lcc.network.CapabilitySyncPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CapabilityEvents {

    @SubscribeEvent
    public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(GauntletCapability.LOCATION, new GauntletCapability.Provider());
            event.addCapability(HeartsCapability.LOCATION, new HeartsCapability.Provider());
            event.addCapability(RainbowCapability.LOCATION, new RainbowCapability.Provider());
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(CryingObsidianCapability.LOCATION, new CryingObsidianCapability.Provider());
            }
        }
    }

    @SubscribeEvent
    public void attachCapabilityToWorld(AttachCapabilitiesEvent<World> event) {
        event.addCapability(NuclearCapability.LOCATION, new NuclearCapability.Provider());
        //global capabilities tied to just overworld
        if (event.getObject().getDimension().getType() == GlobalProvider.DIMENSION) {
            event.addCapability(SpreaderCapability.LOCATION, new SpreaderCapability.Provider());
            event.addCapability(PartitionCapability.LOCATION, new PartitionCapability.Provider());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        PlayerEntity playerOriginal = event.getOriginal();
        PlayerEntity playerNew = event.getPlayer();
        playerOriginal.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(heartsOriginal -> {
            playerNew.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(heartsNew -> {
                HeartsFunctionality.capabilityClone(heartsOriginal, heartsNew, playerOriginal, playerNew, event);
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)playerNew), new CapabilitySyncPacket(heartsNew));
            });
        });
        playerOriginal.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(coOriginal -> {
            playerNew.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(coNew -> {
                coNew.pos = coOriginal.pos;
                coNew.dimension = coOriginal.dimension;
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)playerNew), new CapabilitySyncPacket(coNew));
            });
        });
    }

    @SubscribeEvent
    public void onPlayerSpawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(hearts));
            });
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(co));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerSwitchDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(hearts));
            });
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(co));
            });
            player.world.getCapability(NuclearCapability.Provider.DEFAULT_CAPABILITY).ifPresent(n -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(n));
            });
            player.getCapability(RainbowCapability.Provider.DEFAULT_CAPABILITY).ifPresent(r -> {
                r.portalDelay = 20;
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(r));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(hearts));
            });
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(co));
            });
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof ServerPlayerEntity) {
            PlayerEntity player = (PlayerEntity)event.getEntity();
            player.world.getCapability(NuclearCapability.Provider.DEFAULT_CAPABILITY).ifPresent(n -> {
                LCCPacketHandler.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new CapabilitySyncPacket(n));
            });
        }
    }

}
