package com.joshmanisdabomb.lcc.proxy;

import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.UUID;

public class ClientProxy extends Proxy {

    @Override
    public void handleHeartsUpdatePacket(float redMax, float ironMax, float iron, float crystalMax, float crystal, float temporary) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                hearts.setRedMaxHealth(redMax);
                hearts.setIronMaxHealth(ironMax);
                hearts.setIronHealth(iron);
                hearts.setCrystalMaxHealth(crystalMax);
                hearts.setCrystalHealth(crystal);
                hearts.setTemporaryHealth(temporary, Float.MAX_VALUE);
            });
        }
    }

    @Override
    public void handleLCCEntitySpawnPacket(ResourceLocation type, int id, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float pitch, float yaw, UUID uuid) {
        ClientWorld world = Minecraft.getInstance().world;
        EntityType.byKey(type.toString()).ifPresent(t -> {
            Entity e = t.create(world);

            e.setPosition(posX, posY, posZ);
            e.setMotion(motionX, motionY, motionZ);
            e.rotationPitch = pitch;
            e.rotationYaw = yaw;

            world.addEntity(id, e);
        });
    }

    @Override
    public void handleParticleSpawnPacket(IParticleData data, boolean forceAlwaysRender, double posX, double posY, double posZ, double speedX, double speedY, double speedZ) {
        Minecraft.getInstance().world.addParticle(data, forceAlwaysRender, posX, posY, posZ, speedX, speedY, speedZ);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

}
