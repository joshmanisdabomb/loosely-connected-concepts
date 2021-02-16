package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.particle.NuclearExplosionParticle
import com.joshmanisdabomb.lcc.particle.SoakingSoulSandBubbleParticle
import com.joshmanisdabomb.lcc.particle.SoakingSoulSandJumpParticle
import com.joshmanisdabomb.lcc.particle.SteamParticle
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleFactory
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType

object LCCParticlesClient : ThingDirectory<(FabricSpriteProvider) -> ParticleFactory<out ParticleEffect>, ParticleType<out ParticleEffect>>() {

    val soaking_soul_sand_bubble by create(LCCParticles.soaking_soul_sand_bubble) { { sp -> factory(it, sp, ::SoakingSoulSandBubbleParticle) } }
    val soaking_soul_sand_jump by create(LCCParticles.soaking_soul_sand_jump) { { sp -> factory(it, { p, w, x, y, z, dx, dy, dz -> SoakingSoulSandJumpParticle(p as SoakingSoulSandJumpParticleEffect, w, x, y, z, dx, dy, dz) }) } }
    val steam by create(LCCParticles.steam) { { sp -> factory(it, sp, ::SteamParticle) } }
    val nuclear by create(LCCParticles.nuclear) { { sp -> factory(it, sp, ::NuclearExplosionParticle) } }

    override fun registerAll(things: Map<String, (FabricSpriteProvider) -> ParticleFactory<*>>, properties: Map<String, ParticleType<*>>) {
        things.forEach { (k, v) -> typedRegister(properties[k]!!, v) }
    }

    private inline fun <T : ParticleEffect> typedRegister(type: ParticleType<T>, crossinline factory: (FabricSpriteProvider) -> ParticleFactory<*>) {
        ParticleFactoryRegistry.getInstance().register(type) { sp -> factory(sp) as ParticleFactory<T> }
    }

    private fun <P : ParticleEffect, T : ParticleType<P>> factory(type: T, constructor: (world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double) -> Particle): ParticleFactory<P> {
        return ParticleFactory<P> { _, world, x, y, z, dx, dy, dz -> constructor(world, x, y, z, dx, dy, dz) }
    }

    private fun <P : ParticleEffect, T : ParticleType<P>> factory(type: T, sp: SpriteProvider, constructor: (world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, sp: SpriteProvider) -> Particle): ParticleFactory<P> {
        return ParticleFactory<P> { _, world, x, y, z, dx, dy, dz -> constructor(world, x, y, z, dx, dy, dz, sp) }
    }

    private fun <P : ParticleEffect, T : ParticleType<P>> factory(type: T, constructor: (particle: P, world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double) -> Particle): ParticleFactory<P> {
        return ParticleFactory<P> { p, world, x, y, z, dx, dy, dz -> constructor(p, world, x, y, z, dx, dy, dz) }
    }

    private fun <P : ParticleEffect, T : ParticleType<P>> factory(type: T, sp: SpriteProvider, constructor: (particle: P, world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, sp: SpriteProvider) -> Particle): ParticleFactory<P> {
        return ParticleFactory<P> { p, world, x, y, z, dx, dy, dz -> constructor(p, world, x, y, z, dx, dy, dz, sp) }
    }

}