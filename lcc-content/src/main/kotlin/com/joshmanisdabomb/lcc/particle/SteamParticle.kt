package com.joshmanisdabomb.lcc.particle

import net.minecraft.client.particle.AscendingParticle
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.render.Camera
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Vec3d

class SteamParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, private val sp: SpriteProvider) : AscendingParticle(world, x, y, z, 0.05f, 0.05f, 0.05f, dx, dy, dz, 4f, sp, 1.0f, 6, 0.003f, true) {

    override fun getType() = ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT

    private val ceiling by lazy { Vec3d(random.nextDouble().times(2).minus(1), 0.0, random.nextDouble().times(2).minus(1)).normalize() }

    init {
        val f = random.nextFloat() * 0.2f + 0.8f
        red = f
        green = f
        blue = f
    }

    override fun buildGeometry(vertexConsumer: VertexConsumer, camera: Camera, tickDelta: Float) {
        //setColorAlpha(0.99F)
        super.buildGeometry(vertexConsumer, camera, tickDelta)
    }

    override fun tick() {
        setAlpha(0.3f.plus(1f.minus(age.toFloat().div(maxAge)).times(0.2f)))

        prevPosX = x
        prevPosY = y
        prevPosZ = z
        if (age++ >= maxAge) {
            markDead()
        } else {
            setSpriteForAge(sp)
            velocityY += 0.003
            this.move(velocityX, velocityY, velocityZ)
            if (velocityY > 0 && y == prevPosY) {
                x += ceiling.x.times(0.025)
                z += ceiling.z.times(0.025)
            }
            velocityX *= 0.9599999785423279
            velocityY *= 0.9599999785423279
            velocityZ *= 0.9599999785423279
            if (onGround) {
                velocityX *= 0.699999988079071
                velocityZ *= 0.699999988079071
            }
        }
    }

}