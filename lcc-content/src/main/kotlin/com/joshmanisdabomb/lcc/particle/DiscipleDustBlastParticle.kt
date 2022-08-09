package com.joshmanisdabomb.lcc.particle

import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.particle.effect.DiscipleDustBlastParticleEffect
import net.minecraft.client.MinecraftClient
import net.minecraft.client.particle.NoRenderParticle
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Vec3d
import kotlin.math.floor

class DiscipleDustBlastParticle(type: DiscipleDustBlastParticleEffect, world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double) : NoRenderParticle(world, x, y, z, dx, dy, dz) {

    private val size = type.size
    private val particles = floor(6 * size * size).toInt()

    init {
        velocityX = dx
        velocityY = dy
        velocityZ = dz
    }

    override fun tick() {
        repeat(particles.times(2 - MinecraftClient.getInstance().options.particles.value.id)) {
            val dir = Vec3d(random.nextDouble().minus(0.5), random.nextDouble().minus(0.5), random.nextDouble().minus(0.5)).normalize().multiply(size.times(0.4))
            val distance = random.nextDouble()
            val pos = dir.multiply(size.times(0.6)).multiply(distance).add(x, y, z)
            world.addParticle(LCCParticles.disciple_dust, pos.x, pos.y, pos.z, velocityX - dir.x.times(distance), velocityY - dir.y.times(distance), velocityZ - dir.z.times(distance))
        }
        this.markDead()
    }

}