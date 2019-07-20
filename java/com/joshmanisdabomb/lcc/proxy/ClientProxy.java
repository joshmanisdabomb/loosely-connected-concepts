package com.joshmanisdabomb.lcc.proxy;

import net.minecraft.particles.IParticleData;
import net.minecraft.world.World;

public class ClientProxy extends Proxy {

    @Override
    public void addParticle(World world, IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
