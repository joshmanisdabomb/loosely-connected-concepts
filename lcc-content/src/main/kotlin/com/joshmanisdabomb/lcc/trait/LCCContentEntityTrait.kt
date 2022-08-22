package com.joshmanisdabomb.lcc.trait;

import com.joshmanisdabomb.lcc.abstracts.Temperature
import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCAttributes
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.EntityDamageSource
import net.minecraft.entity.mob.SlimeEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.ProjectileEntity
import kotlin.math.max
import kotlin.math.min

interface LCCContentEntityTrait {

    @JvmDefault
    fun lcc_content_getTemperature() = Temperature.values().filter { (this as Entity).type.isIn(it.entityTag ?: return@filter false) }.maxOrNull()

    @JvmDefault
    fun lcc_content_getTemperatureEnergy(temperature: Temperature) = when (this) {
        is SlimeEntity -> temperature.entityEnergy.times(size)
        else -> temperature.entityEnergy
    }

    @JvmDefault
    fun lcc_content_applyDamage(after: Float, source: DamageSource, original: Float): Float {
        val player = source.source as? PlayerEntity ?: (source.source as? ProjectileEntity)?.owner as? PlayerEntity ?: return after
        if (this is PlayerEntity || source.isMagic || source !is EntityDamageSource) return after
        val living = this as? LivingEntity ?: return after
        var result = after
        for (te in ToolEffectivity.values()) {
            val protection = living.getAttributeValue(te.protection)
            if (protection > 0.0) {
                val damage = player.getAttributeValue(te.damage)
                println(player.attributes.getValue(LCCAttributes.wasteland_damage))
                result = min(result, te.reduceDamageTaken(living, protection, player, damage, after, original))
            }
        }
        return result
    }

    @JvmDefault
    fun lcc_content_applyDamageThroughArmor(attacked: LivingEntity, after: Float, armor: Float, toughness: Float, original: Float): Float {
        val living = this as? LivingEntity ?: return after
        var result = after
        for (te in ToolEffectivity.values()) {
            val damage = living.getAttributeValue(te.damage)
            if (damage > 0.0) {
                val protection = attacked.getAttributeValue(te.protection)
                result = max(result, te.increaseDamageGiven(living, damage, attacked, protection, after, original, 1.5f))
            }
        }
        return result
    }

    @JvmDefault
    fun lcc_content_applyDamageThroughProtection(attacked: LivingEntity, after: Float, protection: Float, original: Float): Float {
        val living = this as? LivingEntity ?: return after
        var result = after
        for (te in ToolEffectivity.values()) {
            val damage = living.getAttributeValue(te.damage)
            if (damage > 0.0) {
                val protection = attacked.getAttributeValue(te.protection)
                result = max(result, te.increaseDamageGiven(living, damage, attacked, protection, after, original, 1.0f))
            }
        }
        return result
    }

}