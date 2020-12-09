package com.joshmanisdabomb.lcc.particle

import com.joshmanisdabomb.lcc.directory.LCCParticles
import net.minecraft.client.particle.NoRenderParticle
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d

class SoakingSoulSandJumpParticle(world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double) : NoRenderParticle(world, x, y, z, dx, dy, dz) {

    private val initialSpace = dx
    private val bubbleHeight = dy
    private val ringMotion = dz

    override fun tick() {
        //ring of bubbles
        for (i in 0 until 100) {
            val side = Direction.fromHorizontal(random.nextInt(4))
            val xAxis = side.offsetX != 0
            val x = if (xAxis) side.offsetX.toFloat() else (random.nextFloat() - 0.5f) * 2.0f
            val z = if (!xAxis) side.offsetZ.toFloat() else (random.nextFloat() - 0.5f) * 2.0f
            val motion: Vec3d = Vec3d.fromPolar(0.0f, MathHelper.floorMod(side.asRotation() + (if (xAxis) -z else x) * if (side.direction == Direction.AxisDirection.NEGATIVE) 45.0f else -45.0f, 360.0f))
            world.addParticle(
                LCCParticles.soaking_soul_sand_bubble,
                this.x + x * (initialSpace / 2), this.y + random.nextDouble() * 0.1, this.z + z * (initialSpace / 2),
                motion.x * ringMotion * 0.3, 0.0, motion.z * ringMotion * 0.3
            )
        }
        //column of bubbles
        for (i in 0 until 50) {
            world.addParticle(
                LCCParticles.soaking_soul_sand_bubble,
                this.x + (random.nextDouble() - 0.5) * initialSpace, this.y + random.nextDouble() * 0.1, this.z + (random.nextDouble() - 0.5) * initialSpace,
                0.0, random.nextDouble() * 0.4 + random.nextDouble() * bubbleHeight * 0.4, 0.0
            )
        }
        this.markDead()
    }

}