package com.joshmanisdabomb.lcc.particle

import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteBillboardParticle
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.MathHelper
import kotlin.math.min

class DiscipleDustParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, private val sp: SpriteProvider) : SpriteBillboardParticle(world, x, y, z, dx, dy, dz) {

    val finalColor1 = random.nextFloat().times(0.6f).plus(0.3f)
    val finalColor2 = finalColor1.minus(random.nextFloat().times(0.1f)).coerceAtLeast(0.0f)
    val finalColor3 = random.nextFloat().times(0.4f).plus(0.6f).times(min(finalColor1, finalColor2))
    val redFirst = random.nextBoolean()

    override fun getType() = ParticleTextureSheet.PARTICLE_SHEET_OPAQUE

    init {
        maxAge = random.nextInt(7).plus(7)
        setBoundingBoxSpacing(0.02f, 0.02f)
        scale *= random.nextFloat().times(1.7f).plus(0.8f)
        velocityMultiplier = 0.3f
        gravityStrength = 0.0f
        collidesWithWorld = false

        red = redFirst.transform(finalColor1, finalColor2)
        blue = redFirst.transform(finalColor2, finalColor1)
        green = finalColor3

        velocityX = dx
        velocityY = dy
        velocityZ = dz

        setSpriteForAge(sp)
    }

    override fun tick() {
        super.tick()
        setSpriteForAge(sp)
        val lerp = age.div(maxAge.toFloat())
        red = redFirst.transform(finalColor1, finalColor2) * MathHelper.lerp(lerp, 1.0f, 0.6f)
        blue =  redFirst.transform(finalColor2, finalColor1) * MathHelper.lerp(lerp, 1.0f, 0.6f)
        green = finalColor3 * MathHelper.lerp(lerp, 1.0f, 0.4f)
        scale *= 0.9f
        velocityMultiplier = MathHelper.lerp(lerp, 1.0f, 0.5f)
    }

}