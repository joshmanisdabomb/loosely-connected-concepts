package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.capability.CryingObsidianCapability;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SServerDifficultyPacket;
import net.minecraft.network.play.server.SSetExperiencePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.DemoPlayerInteractionManager;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.UUID;

public class CryingObsidianSpawnPacket implements LCCPacket {

    private final UUID player;

    public CryingObsidianSpawnPacket(UUID player) {
        this.player = player;
    }

    public static void encode(CryingObsidianSpawnPacket msg, PacketBuffer buf) {
        buf.writeUniqueId(msg.player);
    }

    public static CryingObsidianSpawnPacket decode(PacketBuffer buf) {
        return new CryingObsidianSpawnPacket(buf.readUniqueId());
    }

    @Override
    public void handleLogicalServer() {
        MinecraftServer s = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
        ServerPlayerEntity playerOriginal = s.getPlayerList().getPlayerByUUID(this.player);
        CryingObsidianCapability co = playerOriginal.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).orElseThrow(RuntimeException::new);
        BlockPos spawn = co.pos;

        if (playerOriginal.getHealth() > 0.0F) return;
        s.getPlayerList().removePlayer(playerOriginal);
        playerOriginal.getServerWorld().removePlayer(playerOriginal, true);
        playerOriginal.dimension = co.dimension;
        PlayerInteractionManager interactions = s.isDemo() ? new DemoPlayerInteractionManager(s.getWorld(playerOriginal.dimension)) : new PlayerInteractionManager(s.getWorld(playerOriginal.dimension));

        ServerPlayerEntity playerNew = new ServerPlayerEntity(s, s.getWorld(playerOriginal.dimension), playerOriginal.getGameProfile(), interactions);
        playerNew.connection = playerOriginal.connection;
        playerNew.copyFrom(playerOriginal, false);
        playerNew.dimension = playerOriginal.dimension;
        playerOriginal.remove(false);
        playerNew.setEntityId(playerOriginal.getEntityId());
        playerNew.setPrimaryHand(playerOriginal.getPrimaryHand());

        for (String tag : playerOriginal.getTags()) playerNew.addTag(tag);
        
        ServerWorld world = s.getWorld(playerNew.dimension);
        interactions.setGameType(playerOriginal.interactionManager.getGameType());
        interactions.initializeGameType(world.getWorldInfo().getGameType());
        playerNew.setLocationAndAngles(spawn.getX() + 0.5, spawn.getY() + 1, spawn.getZ() + 0.5, 0.0F, 0.0F);

        while(!world.func_226669_j_(playerNew) && playerNew.getPosY() < 256.0D) playerNew.setPosition(playerNew.getPosX(), playerNew.getPosY() + 1.0D, playerNew.getPosZ());

        WorldInfo info = playerNew.world.getWorldInfo();
        playerNew.connection.sendPacket(new SRespawnPacket(playerNew.dimension, WorldInfo.byHashing(info.getSeed()), info.getGenerator(), playerNew.interactionManager.getGameType()));
        playerNew.connection.setPlayerLocation(playerNew.getPosX(), playerNew.getPosY(), playerNew.getPosZ(), playerNew.rotationYaw, playerNew.rotationPitch);
        playerNew.connection.sendPacket(new SServerDifficultyPacket(info.getDifficulty(), info.isDifficultyLocked()));
        playerNew.connection.sendPacket(new SSetExperiencePacket(playerNew.experience, playerNew.experienceTotal, playerNew.experienceLevel));
        s.getPlayerList().sendWorldInfo(playerNew, world);
        s.getPlayerList().updatePermissionLevel(playerNew);
        world.addRespawnedPlayer(playerNew);
        s.getPlayerList().addPlayer(playerNew);
        //TODO access transformer s.getPlayerList().uuidToPlayerMap.put(playerNew.getUniqueID(), playerNew);
        playerNew.addSelfToInternalCraftingInventory();
        playerNew.setHealth(playerNew.getHealth());
        net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerRespawnEvent(playerNew, false);

        playerNew.connection.player = playerNew;
        if (s.isHardcore()) {
            playerNew.setGameType(GameType.SPECTATOR);
            playerNew.getServerWorld().getGameRules().get(GameRules.SPECTATORS_GENERATE_CHUNKS).set(false, s);
        }
    }

}