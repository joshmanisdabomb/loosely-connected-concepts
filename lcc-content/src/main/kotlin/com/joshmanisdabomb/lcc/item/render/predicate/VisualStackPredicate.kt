package com.joshmanisdabomb.lcc.item.render.predicate

import net.minecraft.client.item.UnclampedModelPredicateProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

class VisualStackPredicate(val maxCount: Int, val minCount: Int = 1) : UnclampedModelPredicateProvider {

    override fun unclampedCall(stack: ItemStack, world: ClientWorld?, entity: LivingEntity?, seed: Int) = (stack.count - minCount).toFloat().div(maxCount - minCount).coerceIn(0f, 1f)

}