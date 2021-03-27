package com.joshmanisdabomb.lcc.abstracts.oxygen

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

interface ContainedArmor {

    fun hasFullSuit(piece: ItemStack, pieces: Iterable<ItemStack>): Boolean

    fun disableEating(entity: PlayerEntity, piece: ItemStack, pieces: Iterable<ItemStack>) = false

    fun blockStatusEffect(entity: LivingEntity, effect: StatusEffectInstance, piece: ItemStack, pieces: Iterable<ItemStack>) = false

    fun hideAirMeter(entity: LivingEntity, piece: ItemStack, pieces: Iterable<ItemStack>) = false

    fun setAirUnderwater(entity: LivingEntity, air: Int, piece: ItemStack, pieces: Iterable<ItemStack>): Int? = null

    fun setAirOnLand(entity: LivingEntity, air: Int, piece: ItemStack, pieces: Iterable<ItemStack>): Int? = null

    companion object {
        inline fun <reified O : OxygenStorage> getTotalOxygen(pieces: Iterable<ItemStack>): Float {
            var oxygen = 0f
            for (piece in pieces) {
                oxygen += (piece.item as? O)?.getOxygen(piece) ?: 0f
            }
            return oxygen
        }

        inline fun <reified O : OxygenStorage> getTotalMaxOxygen(pieces: Iterable<ItemStack>): Float {
            var oxygen = 0f
            for (piece in pieces) {
                oxygen += (piece.item as? O)?.getMaxOxygen(piece) ?: 0f
            }
            return oxygen
        }
    }
}