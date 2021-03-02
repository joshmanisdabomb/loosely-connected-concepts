package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World

class ClassicFoodItem(settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        if (this.isFood) {
            if (user.isSurvival) stack.decrement(1)
            user.heal(foodComponent!!.hunger.toFloat())
            user.hungerManager.add(0, foodComponent!!.hunger.div(2f))
            return TypedActionResult.consume(stack)
        }
        return TypedActionResult.pass(stack)
    }

    override fun finishUsing(stack: ItemStack, world: World, livingEntity: LivingEntity) = stack

    override fun getUseAction(stack: ItemStack) = UseAction.NONE

    override fun getMaxUseTime(stack: ItemStack) = 0

}
