package com.joshmanisdabomb.lcc.particle

import com.joshmanisdabomb.lcc.directory.LCCParticles
import com.joshmanisdabomb.lcc.particle.effect.SoakingSoulSandJumpParticleEffect
import net.minecraft.client.particle.NoRenderParticle
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d

class SoakingSoulSandJumpParticle(type: SoakingSoulSandJumpParticleEffect, world: ClientWorld, x: Double, y: Double, z: Double, dx: Double, dy: Double, dz: Double) : NoRenderParticle(world, x, y, z, dx, dy, dz) {

    private val direction = type.direction
    private val initialSpace = type.area
    private val bubbleHeight = type.height
    private val ringMotion = type.ring

    override fun tick() {
        //ring of bubbles
        for (i in 0 until 100) {
            val side = Direction.fromHorizontal(random.nextInt(4))
            val xAxis = side.offsetX != 0
            val x = if (xAxis) side.offsetX.toFloat() else (random.nextFloat() - 0.5f) * 2.0f
            val z = if (!xAxis) side.offsetZ.toFloat() else (random.nextFloat() - 0.5f) * 2.0f
            val motion: Vec3d = Vec3d.fromPolar(0.0f, MathHelper.floorMod(side.asRotation() + (if (xAxis) -z else x) * if (side.direction == Direction.AxisDirection.NEGATIVE) 45.0f else -45.0f, 360.0f)).multiply(ringMotion * 0.3)
            when (direction.axis) {
                Direction.Axis.X -> world.addParticle(
                    LCCParticles.soaking_soul_sand_bubble,
                    this.x + random.nextDouble() * 0.1 * direction.offsetX, this.y + x * (initialSpace / 2), this.z + z * (initialSpace / 2),
                    0.0, motion.x, motion.z
                )
                Direction.Axis.Y, null -> world.addParticle(
                    LCCParticles.soaking_soul_sand_bubble,
                    this.x + x * (initialSpace / 2), this.y + random.nextDouble() * 0.1 * direction.offsetY, this.z + z * (initialSpace / 2),
                    motion.x, 0.0, motion.z
                )
                Direction.Axis.Z -> world.addParticle(
                    LCCParticles.soaking_soul_sand_bubble,
                    this.x + x * (initialSpace / 2), this.y + z * (initialSpace / 2), this.z + random.nextDouble() * 0.1 * direction.offsetZ,
                    motion.x, motion.z, 0.0
                )
            }
        }
        //column of bubbles
        for (i in 0 until 50) {
            when (direction.axis) {
                Direction.Axis.X -> world.addParticle(
                    LCCParticles.soaking_soul_sand_bubble,
                    this.x + random.nextDouble() * 0.1 * direction.offsetX, this.y + (random.nextDouble() - 0.5) * initialSpace, this.z + (random.nextDouble() - 0.5) * initialSpace,
                    (random.nextDouble() * 0.4 + random.nextDouble() * bubbleHeight * 0.4) * direction.offsetX, 0.0, 0.0
                )
                Direction.Axis.Y, null -> world.addParticle(
                    LCCParticles.soaking_soul_sand_bubble,
                    this.x + (random.nextDouble() - 0.5) * initialSpace, this.y + random.nextDouble() * 0.1 * direction.offsetY, this.z + (random.nextDouble() - 0.5) * initialSpace,
                    0.0, (random.nextDouble() * 0.4 + random.nextDouble() * bubbleHeight * 0.4) * direction.offsetY, 0.0
                )
                Direction.Axis.Z -> world.addParticle(
                    LCCParticles.soaking_soul_sand_bubble,
                    this.x + (random.nextDouble() - 0.5) * initialSpace, this.y + (random.nextDouble() - 0.5) * initialSpace, this.z + random.nextDouble() * 0.1 * direction.offsetZ,
                    0.0, 0.0, (random.nextDouble() * 0.4 + random.nextDouble() * bubbleHeight * 0.4) * direction.offsetZ
                )
            }
        }
        this.markDead()
    }

}