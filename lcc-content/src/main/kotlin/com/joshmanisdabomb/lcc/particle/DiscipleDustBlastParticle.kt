package com.joshmanisdabomb.lcc.particle

import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.particle.effect.DiscipleDustBlastParticleEffect
import net.minecraft.client.MinecraftClient
import net.minecraft.client.particle.NoRenderParticle
import net.minecraft.client.world.ClientWorld
import kotlin.math.floor

class DiscipleDustBlastParticle(type: DiscipleDustBlastParticleEffect, world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double) : NoRenderParticle(world, x, y, z, dx, dy, dz) {

    private val size = type.size
    private val particles = floor(2 * size * size).toInt()

    init {
        velocityX = dx
        velocityY = dy
        velocityZ = dz
    }

    override fun tick() {
        repeat(particles.times(2 - MinecraftClient.getInstance().options.particles.value.id)) {
            world.addParticle(LCCParticles.disciple_dust, x + random.nextDouble().minus(0.5).times(size).times(2), y + random.nextDouble().minus(0.5).times(size).times(2), z + random.nextDouble().minus(0.5).times(size).times(2), velocityX + random.nextDouble().minus(0.5).times(0.1), velocityY + random.nextDouble().times(0.1).plus(0.1), velocityZ + random.nextDouble().minus(0.5).times(0.1))
        }
        this.markDead()
    }

}