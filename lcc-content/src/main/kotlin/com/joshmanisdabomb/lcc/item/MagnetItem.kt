package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.mixin.content.common.ItemEntityAccessor
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class MagnetItem(val speed: Double, val range: Double, settings: Settings): Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        user.setCurrentHand(hand)
        return TypedActionResult.consume(user.getStackInHand(hand))
    }

    override fun finishUsing(stack: ItemStack, world: World, user: LivingEntity): ItemStack {
        (user as? PlayerEntity)?.itemCooldownManager?.set(this, 80)
        attract(world, user.pos, speed)
        stack.damage(8, user) { it.sendToolBreakStatus(user.activeHand) }
        return stack
    }

    override fun usageTick(world: World, user: LivingEntity, stack: ItemStack, remainingUseTicks: Int) {
        val ticks = getMaxUseTime(stack) - remainingUseTicks
        attract(world, user.pos, speed.times(0.001).times(ticks))
        stack.damage(1, user) { it.sendToolBreakStatus(user.activeHand) }
    }

    fun attract(world: World, pos: Vec3d, multiplier: Double) {
        world.getEntitiesByType(EntityType.ITEM, Box.of(pos, range, range, range), Entity::isAlive).forEach {
            val distsq = Math.sqrt(it.pos.squaredDistanceTo(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5))
            val s: Double = range.times(range).minus(distsq) * 0.012f
            val speed = s * s * MathHelper.clamp((it as ItemEntityAccessor).age * 0.13, 0.0, 1.0) * multiplier

            it.velocity = it.velocity.add(it.pos.subtract(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5).normalize().multiply(-speed))
            it.velocityModified = true
            it.velocityDirty = true
        }
    }

    override fun getUseAction(stack: ItemStack) = UseAction.TOOT_HORN

    override fun getMaxUseTime(stack: ItemStack) = 30

}
