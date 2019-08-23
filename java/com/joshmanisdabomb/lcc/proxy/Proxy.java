package com.joshmanisdabomb.lcc.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class Proxy {

    public void addParticle(World world, Entity actor, IParticleData particleData, boolean forceAlwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

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
