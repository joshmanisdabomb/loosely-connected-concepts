package com.joshmanisdabomb.lcc.particle

import net.minecraft.client.particle.AscendingParticle
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld

class NuclearExplosionParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, private val sp: SpriteProvider) : AscendingParticle(world, x, y, z, 0.0f, 0.0f, 0.0f, dx, dy, dz, 200f, sp, 1.0f, 1, 0.0f, true) {

    override fun getType() = ParticleTextureSheet.PARTICLE_SHEET_OPAQUE

    init {
        val f = 1.0f
        colorRed = f
        colorGreen = f
        colorBlue = f
        maxAge = 32
    }

    override fun getSize(tickDelta: Float) = scale

    override fun getBrightness(tint: Float) = 15728880

}