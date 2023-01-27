package com.joshmanisdabomb.lcc.world.render.dimension

import net.minecraft.client.render.DimensionEffects
import net.minecraft.util.math.Vec3d
import java.lang.Double.max
import kotlin.math.pow

class RainbowDimensionEffects : DimensionEffects(320f, false, SkyType.NORMAL, false, false) {



    override fun adjustFogColor(color: Vec3d, sunHeight: Float): Vec3d {
        if (sunHeight >= 1f) return color
        val dumb = color.normalize()
        val night = Vec3d(dumb.x.pow(4), dumb.y.pow(4), dumb.z.pow(4)).multiply(max(max(color.x, color.y), color.z))
        return night.lerp(color, sunHeight.toDouble())
    }

    override fun useThickFog(camX: Int, camY: Int) = false

    override fun getFogColorOverride(skyAngle: Float, tickDelta: Float) = null

}