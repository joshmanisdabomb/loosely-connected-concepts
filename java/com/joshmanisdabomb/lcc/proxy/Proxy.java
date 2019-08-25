package com.joshmanisdabomb.lcc.proxy;

import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.network.ParticleSpawnPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public abstract class Proxy {

    public void addParticle(World world, Entity actor, IParticleData particleData, boolean forceAlwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        if (world.isRemote) {
            if (Minecraft.getInstance().player == actor) {
                Minecraft.getInstance().world.addParticle(particleData, forceAlwaysRender, x, y, z, xSpeed, ySpeed, zSpeed);
            }
        } else {
            LCCPacketHandler.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(actor instanceof ServerPlayerEntity ? (ServerPlayerEntity)actor : null, x, y, z, 64, world.dimension.getType())), new ParticleSpawnPacket(particleData, forceAlwaysRender, x, y, z, xSpeed, ySpeed, zSpeed));
        }
    }

    public void handleHeartsUpdatePacket(float redMax, float ironMax, float iron, float crystalMax, float crystal, float temporary) {

    }

    public void handleLCCEntitySpawnPacket(ResourceLocation type, int id, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float pitch, float yaw, UUID uuid) {

    }

    public void handleParticleSpawnPacket(IParticleData data, boolean forceAlwaysRender, double posX, double posY, double posZ, double speedX, double speedY, double speedZ) {

    }

    public World getClientWorld() {
        throw new IllegalStateException("Running on server. Bad.");
    }

    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("Running on server. Bad.");
    }

}
