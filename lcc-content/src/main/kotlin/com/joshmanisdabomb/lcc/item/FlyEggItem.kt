package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
import net.minecraft.entity.SpawnReason
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.util.ActionResult
import net.minecraft.util.math.Direction
import net.minecraft.world.event.GameEvent

class FlyEggItem(settings: Settings) : Item(settings) {

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        if (world !is ServerWorld) return ActionResult.SUCCESS
        val state = world.getBlockState(context.blockPos)
        val pos2 = if (state.getCollisionShape(world, context.blockPos).isEmpty) context.blockPos else context.blockPos.offset(context.side)
        val entity = LCCEntities.fly.create(world, null, null, null, pos2, SpawnReason.SPAWN_EGG, true, pos2 == context.blockPos && context.side == Direction.UP) ?: return ActionResult.FAIL
        entity.yaw = context.world.random.nextFloat().times(360)
        entity.isTamed = true
        entity.ownerUuid = context.player?.uuid
        world.spawnEntity(entity)
        context.stack.decrement(1)
        world.emitGameEvent(context.player, GameEvent.ENTITY_PLACE, context.blockPos)
        world.playSound(null, entity.x, entity.y, entity.z, LCCSounds.fly_egg_hatch, SoundCategory.PLAYERS, 1.0f, entity.soundPitch)
        return ActionResult.CONSUME
    }

}