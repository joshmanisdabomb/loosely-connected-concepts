package com.joshmanisdabomb.lcc.enchantment

import com.joshmanisdabomb.lcc.directory.LCCEntities
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents

class InfestedEnchantment(weight: Rarity, type: EnchantmentTarget, vararg slotTypes: EquipmentSlot) : Enchantment(weight, type, slotTypes) {

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (target is LivingEntity) {
            if (!target.world.isClient) {
                val flies = user.random.nextInt(level).plus(1)
                repeat(flies) {
                    val fly = LCCEntities.fly.create(target.world) ?: return@repeat
                    fly.setPosition(target.pos.x, target.pos.y + target.height.div(2.0), target.pos.z)
                    fly.setVelocity(user.random.nextDouble().minus(0.5).times(2.0), 0.1, user.random.nextDouble().minus(0.5).times(2.0))
                    target.world.spawnEntity(fly)
                    fly.isTamed = true
                    fly.ownerUuid = user.uuid
                    fly.target = target
                }
            }
        }
        target.world.playSound(target.pos.x, target.pos.y, target.pos.z, SoundEvents.BLOCK_BAMBOO_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f, false)
    }

    override fun getMinPower(level: Int) = level * 10

    override fun getMaxPower(level: Int) = getMinPower(level) + 15

    override fun isAvailableForEnchantedBookOffer() = false

    override fun isAvailableForRandomSelection() = false

    override fun getMaxLevel() = 5

}
