package com.joshmanisdabomb.lcc.registry;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.particle.HydratedSoulSandBubbleParticle;
import com.joshmanisdabomb.lcc.particle.HydratedSoulSandJumpParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;

import java.util.ArrayList;

public abstract class LCCParticles {

    public static final ArrayList<ParticleType<?>> all = new ArrayList<>();

    public static BasicParticleType hydrated_soul_sand_bubble;
    public static BasicParticleType hydrated_soul_sand_jump;

    public static void init(RegistryEvent.Register<ParticleType<?>> e) {
        all.add(hydrated_soul_sand_bubble = (BasicParticleType)new BasicParticleType(false).setRegistryName(LCC.MODID, "hydrated_soul_sand_bubble"));
        all.add(hydrated_soul_sand_jump = (BasicParticleType)new BasicParticleType(false).setRegistryName(LCC.MODID, "hydrated_soul_sand_jump"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void initFactories() {
        Minecraft.getInstance().particles.registerFactory(hydrated_soul_sand_bubble, (type, world, x, y, z, xSpeed, ySpeed, zSpeed) -> new HydratedSoulSandBubbleParticle(world, x, y, z, xSpeed, ySpeed, zSpeed));
        Minecraft.getInstance().particles.registerFactory(hydrated_soul_sand_jump, (type, world, x, y, z, xSpeed, ySpeed, zSpeed) -> new HydratedSoulSandJumpParticle(world, x, y, z, xSpeed, ySpeed, zSpeed));
    }

}