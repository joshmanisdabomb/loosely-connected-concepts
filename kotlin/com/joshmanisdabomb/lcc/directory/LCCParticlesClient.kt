package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.particle.SoakingSoulSandBubbleParticle
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleFactory
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType

object LCCParticlesClient : ThingDirectory<(FabricSpriteProvider) -> ParticleFactory<*>, ParticleType<*>>() {

    val soaking_soul_sand_bubble by create(LCCParticles.soaking_soul_sand_bubble) { { sp -> factory(it, sp, ::SoakingSoulSandBubbleParticle) } }

    override fun registerAll(things: Map<String, (FabricSpriteProvider) -> ParticleFactory<*>>, properties: Map<String, ParticleType<*>>) {
        things.forEach { (k, v) -> typedRegister(properties[k]!!, v) }
    }

    private fun <T : ParticleEffect> typedRegister(type: ParticleType<T>, factory: (FabricSpriteProvider) -> ParticleFactory<*>) {
        ParticleFactoryRegistry.getInstance().register(type) { sp -> factory(sp) as ParticleFactory<T> }
    }

    private fun <P : ParticleEffect, T : ParticleType<P>> factory(type: T, sp: SpriteProvider, constructor: (world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, sp: SpriteProvider) -> Particle): ParticleFactory<P> {
        return ParticleFactory<P> { _, world, x, y, z, dx, dy, dz -> constructor(world, x, y, z, dx, dy, dz, sp) }
    }

}