package com.joshmanisdabomb.lcc.block

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.world.BlockView

class RubberBlock(settings: Settings) : Block(settings) {

    override fun onEntityLand(world: BlockView, entity: Entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity)
        } else if (!bounce(entity)) {
            super.onEntityLand(world, entity)
        }
    }

    fun bounce(entity: Entity): Boolean {
        val vel = entity.velocity
        if (vel.y < -0.08) {
            val d = if (entity is LivingEntity) 1.0 else 0.8
            entity.setVelocity(vel.x, vel.y.times(-d).coerceIn(0.3, 1.0), vel.z)
            return true
        }
        return false
    }

}
