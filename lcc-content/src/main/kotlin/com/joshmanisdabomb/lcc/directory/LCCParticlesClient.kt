package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.particle.NuclearExplosionParticle
import com.joshmanisdabomb.lcc.particle.SoakingSoulSandBubbleParticle
import com.joshmanisdabomb.lcc.particle.SoakingSoulSandJumpParticle
import com.joshmanisdabomb.lcc.particle.SteamParticle
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleFactory
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType

object LCCParticlesClient : AdvancedDirectory<ParticleFactoryRegistry.PendingParticleFactory<out ParticleEffect>, ParticleType<out ParticleEffect>, ParticleType<out ParticleEffect>, Unit>() {

    val soaking_soul_sand_bubble by entry({ input, context, parameters -> initialiser(input, context, parameters) }) { ParticleFactoryRegistry.PendingParticleFactory { sp -> factory(properties, sp, ::SoakingSoulSandBubbleParticle) } }
        .setProperties(LCCParticles.soaking_soul_sand_bubble)
    val soaking_soul_sand_jump by entry({ input, context, parameters -> initialiser(input, context, parameters) }) { ParticleFactoryRegistry.PendingParticleFactory { sp -> factory(properties, { p, w, x, y, z, dx, dy, dz -> SoakingSoulSandJumpParticle(p as SoakingSoulSandJumpParticleEffect, w, x, y, z, dx, dy, dz) }) } }
        .setProperties(LCCParticles.soaking_soul_sand_jump)
    val steam by entry({ input, context, parameters -> initialiser(input, context, parameters) }) { ParticleFactoryRegistry.PendingParticleFactory { sp -> factory(properties, sp, ::SteamParticle) } }
        .setProperties(LCCParticles.steam)
    val nuclear by entry({ input, context, parameters -> initialiser(input, context, parameters) }) { ParticleFactoryRegistry.PendingParticleFactory { sp -> factory(properties, sp, ::NuclearExplosionParticle) } }
        .setProperties(LCCParticles.nuclear)

    fun <P : ParticleEffect> initialiser(input: ParticleFactoryRegistry.PendingParticleFactory<P>, context: DirectoryContext<ParticleType<out ParticleEffect>>, parameters: Unit): ParticleType<P> {
        val type = context.properties as ParticleType<P>
        input.also { ParticleFactoryRegistry.getInstance().register(type, input) }
        return type
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

    override fun defaultProperties(name: String) = error("No default properties available for this directory.")
    override fun defaultContext() = Unit

}