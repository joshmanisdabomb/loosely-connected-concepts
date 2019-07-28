package com.joshmanisdabomb.lcc.proxy;

import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.network.ParticleSpawnPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class ServerProxy extends Proxy {

    @Override
    public void addParticle(World world, IParticleData particleData, boolean forceAlwaysRender, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        LCCPacketHandler.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(x, y, z, 64, world.dimension.getType())), new ParticleSpawnPacket(particleData, forceAlwaysRender, x, y, z, xSpeed, ySpeed, zSpeed));
    }
}
