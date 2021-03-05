package com.joshmanisdabomb.lcc.abstracts.oxygen

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.ItemStack

interface ContainedArmor {

    //fun disableEating(entity: PlayerEntity, stack: ItemStack, pieces: List<ItemStack>) = true

    fun blockStatusEffect(entity: LivingEntity, effect: StatusEffectInstance, stack: ItemStack, pieces: Iterable<ItemStack>) = false

    //fun

}