package com.joshmanisdabomb.lcc.proxy;

import com.joshmanisdabomb.lcc.network.LCCPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.network.ParticleSpawnPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.PacketDistributor;

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

    public void handlePacket(LogicalSide side, LCCPacket msg) {
        switch (side) {
            case CLIENT:
                msg.handleClient();
                break;
            case SERVER:
                msg.handleLogicalServer();
                break;
        }
    }

    public World getClientWorld() {
        throw new IllegalStateException("Running on server. Bad.");
    }

    public PlayerEntity getClientPlayer() {
        throw new IllegalStateException("Running on server. Bad.");
    }

}
