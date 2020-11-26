package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.concepts.heart.HeartType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World

open class HeartItem(val heart: HeartType, val value: Float, settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand) ?: return TypedActionResult.fail(ItemStack.EMPTY)
        if (canUseHeart(user)) {
            user.setCurrentHand(hand)
            return TypedActionResult.consume(stack)
        }
        return TypedActionResult.fail(stack)
    }

    override fun usageTick(world: World, user: LivingEntity, stack: ItemStack, remainingUseTicks: Int) {
        val ticks = getMaxUseTime(stack) - remainingUseTicks
        if (ticks >= 30 && ticks % 10 == 0) {
            useHeart(user)
            if ((user as? PlayerEntity)?.isCreative != true) stack.decrement(1)
            if (stack.isEmpty || !canUseHeart(user)) user.clearActiveItem()
            //TODO custom sound
        }
    }

    open fun canUseHeart(user: LivingEntity) = heart.getHealth(user) < heart.getDefaultLimit(user)

    open fun useHeart(user: LivingEntity) = heart.addHealth(user, value)

    override fun getUseAction(stack: ItemStack?) = UseAction.SPEAR

    override fun getMaxUseTime(stack: ItemStack) = 72000;

}
