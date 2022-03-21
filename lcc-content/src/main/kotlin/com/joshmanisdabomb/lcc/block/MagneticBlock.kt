package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.mixin.content.common.ItemEntityAccessor
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import java.util.*

class MagneticBlock(val speed: Double, val range: Double, settings: Settings) : Block(settings) {

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        attract(world, pos, speed.times(2.0))
        world.createAndScheduleBlockTick(pos, this, 1)
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        world.createAndScheduleBlockTick(pos, this, 1)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        attract(world, pos, speed)
        world.createAndScheduleBlockTick(pos, this, 1)
    }

    fun attract(world: World, pos: BlockPos, multiplier: Double) {
        world.getEntitiesByType(EntityType.ITEM, Box(pos).expand(range), Entity::isAlive).forEach {
            val distsq = Math.sqrt(it.pos.squaredDistanceTo(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5))
            val s: Double = range.times(range).minus(distsq) * 0.012f
            val speed = s * s * MathHelper.clamp((it as ItemEntityAccessor).age * 0.13, 0.0, 1.0) * multiplier

            it.velocity = it.velocity.add(it.pos.subtract(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5).normalize().multiply(-speed))
            it.velocityModified = true
            it.velocityDirty = true
        }
    }

}
