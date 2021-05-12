package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.mixin.content.common.LivingEntityAccessor
import com.joshmanisdabomb.lcc.lib.item.DefaultedDyeableItem
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.DyeableArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.tag.FluidTags
import net.minecraft.world.World

open class HazmatArmorItem(slot: EquipmentSlot, settings: Settings) : DyeableArmorItem(LCCArmorMaterials.HAZMAT, slot, settings),
    DefaultedDyeableItem, ContainedArmor {

    override fun defaultColor(stack: ItemStack) = 0x909090

    override fun hasFullSuit(piece: ItemStack, pieces: Iterable<ItemStack>) = pieces.all { it.item is HazmatArmorItem }

    override fun blockEating(entity: PlayerEntity, piece: ItemStack, pieces: Iterable<ItemStack>) = slot == EquipmentSlot.HEAD

    override fun blockStatusEffect(entity: LivingEntity, effect: StatusEffectInstance, piece: ItemStack, pieces: Iterable<ItemStack>): Boolean {
        if (!hasFullSuit(piece, pieces) || ContainedArmor.getTotalOxygen<HazmatTankArmorItem>(pieces) <= 0f) return false
        return when (effect.effectType) {
            LCCEffects.stun, StatusEffects.BAD_OMEN, StatusEffects.CONDUIT_POWER, StatusEffects.DOLPHINS_GRACE, StatusEffects.HERO_OF_THE_VILLAGE, StatusEffects.MINING_FATIGUE -> false
            else -> true
        }
    }

    override fun blockDamage(entity: LivingEntity, damage: DamageSource, amount: Float, piece: ItemStack, pieces: Iterable<ItemStack>): Boolean {
        if (!hasFullSuit(piece, pieces) || ContainedArmor.getTotalOxygen<HazmatTankArmorItem>(pieces) <= 0f) return false
        return when (damage) {
            DamageSource.CACTUS, DamageSource.DRAGON_BREATH, DamageSource.FREEZE, DamageSource.HOT_FLOOR, DamageSource.SWEET_BERRY_BUSH, LCCDamage.boiled, LCCDamage.heated -> true
            else -> false
        }
    }

    override fun hideAirMeter(entity: LivingEntity, piece: ItemStack, pieces: Iterable<ItemStack>): Boolean {
        if (slot != EquipmentSlot.HEAD) return false
        if (!hasFullSuit(piece, pieces)) return false
        return true
    }

    override fun setAirUnderwater(entity: LivingEntity, air: Int, piece: ItemStack, pieces: Iterable<ItemStack>): Int? {
        if (slot != EquipmentSlot.HEAD) return null
        if (!hasFullSuit(piece, pieces)) return air
        if (ContainedArmor.getTotalOxygen<HazmatTankArmorItem>(entity.armorItems) <= 0f) return air
        return (entity as? LivingEntityAccessor)?.getAirChangeOnLand(air)
    }

    override fun setAirOnLand(entity: LivingEntity, air: Int, piece: ItemStack, pieces: Iterable<ItemStack>): Int? {
        if (slot != EquipmentSlot.HEAD) return null
        if (!hasFullSuit(piece, pieces)) return air
        if (ContainedArmor.getTotalOxygen<HazmatTankArmorItem>(entity.armorItems) <= 0f) return air
        return null
    }

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        if ((entity as? PlayerEntity)?.isSurvival == false) return
        if (entity.armorItems.indexOf(stack) <= -1) return
        if (entity is LivingEntity && entity.isSubmergedIn(FluidTags.WATER) && world.random.nextInt(160) == 0) {
            stack.damage(1, entity) {
                it.sendEquipmentBreakStatus(this.slot)
            }
        }

        if (this.slot != EquipmentSlot.HEAD) return
        if (hasFullSuit(stack, entity.armorItems) && ContainedArmor.getTotalOxygen<HazmatTankArmorItem>(entity.armorItems) > 0f) return
        entity.air -= 1
        if (entity.air <= -20) {
            entity.air = 0
            entity.damage(LCCDamage.hazmat_anoxia, 2f)
        }
    }

}
