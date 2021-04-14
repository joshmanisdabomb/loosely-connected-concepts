package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.adaptation.boat.LCCBoatEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.item.BoatItem
import net.minecraft.item.ItemStack
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

class LCCBoatItem(settings: Settings, entity: () -> EntityType<LCCBoatEntity>) : BoatItem(BoatEntity.Type.OAK, settings) {

    val entity by lazy(entity)

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)
        val hitResult = raycast(world, user, RaycastContext.FluidHandling.ANY)
        if (hitResult.type == HitResult.Type.BLOCK) {
            val rot = user.getRotationVec(1.0f)
            val list = world.getOtherEntities(user, user.boundingBox.stretch(rot.multiply(5.0)).expand(1.0), collisionCheck)
            if (list.isNotEmpty()) {
                val eyePos = user.eyePos
                for (e in list) {
                    val box = e.boundingBox.expand(e.targetingMargin.toDouble())
                    if (box.contains(eyePos)) {
                        return TypedActionResult.pass(itemStack)
                    }
                }
            }
            val boat = entity.create(world)?.apply { placed(hitResult.pos.x, hitResult.pos.y, hitResult.pos.z, user.yaw) } ?: return TypedActionResult.fail(itemStack)
            if (!world.isSpaceEmpty(boat, boat.boundingBox.expand(-0.1))) {
                return TypedActionResult.fail(itemStack)
            } else {
                if (!world.isClient) {
                    world.spawnEntity(boat)
                    world.emitGameEvent(user, GameEvent.ENTITY_PLACE, BlockPos(hitResult.pos))
                    if (!user.abilities.creativeMode) {
                        itemStack.decrement(1)
                    }
                }
                user.incrementStat(Stats.USED.getOrCreateStat(this))
                return TypedActionResult.success(itemStack, world.isClient())
            }
        }
        return TypedActionResult.pass(itemStack)
    }

    companion object {
        val collisionCheck = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides)
    }

}
