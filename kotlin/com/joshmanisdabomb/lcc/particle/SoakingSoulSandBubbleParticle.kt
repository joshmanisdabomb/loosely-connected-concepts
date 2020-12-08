package com.joshmanisdabomb.lcc.particle

import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteBillboardParticle
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld

class SoakingSoulSandBubbleParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, private val sp: SpriteProvider) : SpriteBillboardParticle(world, x, y, z, dx, dy, dz) {

    override fun getType() = ParticleTextureSheet.PARTICLE_SHEET_OPAQUE

    init {
        maxAge = 20 + random.nextInt(20)
        setBoundingBoxSpacing(0.02f, 0.02f)
        scale *= random.nextFloat() * 0.6f + 0.2f
        gravityStrength = 0.008f
        velocityX = dx
        velocityY = dy
        velocityZ = dz
        setSprite(sp.getSprite(0, 5))
    }

    override fun tick() {
        prevPosX = x
        prevPosY = y
        prevPosZ = z
        if (age++ >= maxAge) {
            this.markDead()
        } else {
            this.move(velocityX, velocityY, velocityZ)
            this.velocityX *= 0.77
            this.velocityY *= 0.77
            this.velocityZ *= 0.77
            setSprite(sp.getSprite(age.plus(5).minus(maxAge).coerceIn(0, 5), 5))
        }
    }

}