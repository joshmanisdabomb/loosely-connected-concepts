package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.utils.DefaultedDyeableItem
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.DyeableArmorItem
import net.minecraft.item.ItemStack

open class HazmatArmorItem(slot: EquipmentSlot, settings: Settings) : DyeableArmorItem(LCCArmorMaterials.HAZMAT, slot, settings), DefaultedDyeableItem, ContainedArmor {

    override fun defaultColor(stack: ItemStack) = 0x909090

    override fun hasFullSuit(piece: ItemStack, pieces: Iterable<ItemStack>) = pieces.all { it.item is HazmatArmorItem }

    override fun disableEating(entity: PlayerEntity, stack: ItemStack, pieces: Iterable<ItemStack>) = this.slot == EquipmentSlot.HEAD

    override fun blockStatusEffect(entity: LivingEntity, effect: StatusEffectInstance, stack: ItemStack, pieces: Iterable<ItemStack>): Boolean {
        if (pieces.any { it.item !is HazmatArmorItem } || pieces.maxOfOrNull { (it.item as? HazmatTankArmorItem)?.getOxygen(it) ?: 0f } ?: 0f <= 0f) return false
        return when (effect.effectType) {
            LCCEffects.stun, StatusEffects.BAD_OMEN, StatusEffects.CONDUIT_POWER, StatusEffects.DOLPHINS_GRACE, StatusEffects.HERO_OF_THE_VILLAGE, StatusEffects.MINING_FATIGUE -> false
            else -> true
        }
    }

}
