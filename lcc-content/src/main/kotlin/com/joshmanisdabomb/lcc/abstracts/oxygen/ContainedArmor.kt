package com.joshmanisdabomb.lcc.abstracts.oxygen

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

interface ContainedArmor {

    fun hasFullSuit(piece: ItemStack, pieces: Iterable<ItemStack>): Boolean

    fun disableEating(entity: PlayerEntity, stack: ItemStack, pieces: Iterable<ItemStack>) = false

    fun blockStatusEffect(entity: LivingEntity, effect: StatusEffectInstance, stack: ItemStack, pieces: Iterable<ItemStack>) = false

}