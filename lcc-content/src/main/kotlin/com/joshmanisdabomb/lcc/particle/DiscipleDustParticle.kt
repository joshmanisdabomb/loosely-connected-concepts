package com.joshmanisdabomb.lcc.particle

import com.joshmanisdabomb.lcc.abstracts.color.LCCExtendedDyeColor
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteBillboardParticle
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.MathHelper

class DiscipleDustParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double, private val sp: SpriteProvider) : SpriteBillboardParticle(world, x, y, z, dx, dy, dz) {

    override fun getType() = ParticleTextureSheet.PARTICLE_SHEET_OPAQUE

    init {
        maxAge = random.nextInt(6).plus(6)
        setBoundingBoxSpacing(0.02f, 0.02f)
        scale = 0.2f
        velocityMultiplier = 0.3f
        gravityStrength = 0.0f
        collidesWithWorld = false

        val svValue = random.nextFloat().times(0.8f).plus(0.2f)
        val svChoice = random.nextBoolean()
        val color = MathHelper.hsvToRgb(random.nextFloat().times(0.12f).plus(0.72f), if (svChoice) svValue else 1.0f, if (!svChoice) svValue else 1.0f)
        val floats = LCCExtendedDyeColor.getComponents(color)
        red = floats[0]
        green = floats[1]
        blue = floats[2]

        velocityX = dx
        velocityY = dy
        velocityZ = dz

        setSprite(sp.getSprite(0, 5))
    }

    override fun tick() {
        super.tick()
        setSprite(sp.getSprite(age.plus(5).minus(maxAge).coerceIn(0, 5), 5))
        val lerp = age.div(maxAge.toFloat())

        red = MathHelper.clamp(red + 0.8f.times(lerp), 0f, 1f)
        green = MathHelper.clamp(green + 0.5f.times(lerp), 0f, 1f)
        blue = MathHelper.clamp(blue + 0.8f.times(lerp), 0f, 1f)

        scale *= 0.96f
        velocityMultiplier = MathHelper.lerp(lerp, 1.0f, 1.8f)
    }

}