package com.joshmanisdabomb.lcc.proxy;

import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class Proxy {

    public void addParticle(World world, IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

    }

    public void handleHeartsUpdatePacket(float redMax, float ironMax, float iron, float crystalMax, float crystal, float temporary) {

    }

    public void handleLCCEntitySpawnPacket(ResourceLocation type, int id, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float pitch, float yaw, UUID uuid) {

    }

}
