package com.joshmanisdabomb.lcc.particle

import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteBillboardParticle
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld

open class StaticBillboardParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, private val sp: SpriteProvider) : SpriteBillboardParticle(world, x, y, z, dx, dy, dz) {

    init {
        velocityX = 0.0
        velocityY = 0.0
        velocityZ = 0.0
        gravityStrength = 0.0F
        maxAge = 80
        collidesWithWorld = false
        setSprite(sp)
    }

    override fun getType() = ParticleTextureSheet.PARTICLE_SHEET_OPAQUE

    override fun getSize(tickDelta: Float) = 0.5f

}