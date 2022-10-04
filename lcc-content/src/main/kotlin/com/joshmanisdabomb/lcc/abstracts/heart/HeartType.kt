package com.joshmanisdabomb.lcc.abstracts.heart

import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.sound.SoundEvent
import net.minecraft.util.StringIdentifiable
import java.util.*
import kotlin.math.pow

enum class HeartType : StringIdentifiable {

    RED {
        private val id = UUID.fromString("a1494707-946f-462b-9c27-522404db24a0")

        override val drawable = false
        override val v = -1
        override val sortOrder = 30

        override fun getHealth(entity: LivingEntity) = entity.health

        override fun setHealth(entity: LivingEntity, amount: Float, limit: Float, sync: Boolean) {
            entity.health = amount.coerceIn(0f, limit)
        }

        override fun update(entity: LivingEntity, amount: Float, maxAmount: Float) {
            val modifier = createModifier(maxAmount.toDouble())
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.apply {
                removeModifier(modifier.id)
                addPersistentModifier(modifier)
            }
        }

        override fun getDefaultLimit(entity: LivingEntity) = entity.maxHealth

        override fun calculateDamage(entity: LivingEntity, damage: Float) = damage

        fun createModifier(amount: Double) = EntityAttributeModifier(id, "LCC Red Heart Max Health", amount, EntityAttributeModifier.Operation.ADDITION)
    },
    IRON {
        override val v = 0
        override val hurtColor = 0xB288889E.toInt()
        override val sortOrder = 20
        
        override val rememberMax = true

        override val hurtSound by lazy { LCCSounds.player_hurt_iron }

        override fun calculateDamage(entity: LivingEntity, damage: Float): Float {
            //any damage after 2 hearts is halved
            val before = getHealth(entity)

            val damageMod = damage - damage.minus(4).coerceAtLeast(0f).div(2)
            val after = getHealth(entity).minus(damageMod).coerceAtLeast(0f)
            setHealth(entity, after)

            val maxAbsorbable = before + before.minus(4).coerceAtLeast(0f)
            return damage - maxAbsorbable
        }
    },
    CRYSTAL {
        override val v = 18
        override val hurtColor = 0xB2EBCC34.toInt()
        override val sortOrder = 10

        override val rememberMax = true

        override val hurtSound by lazy { LCCSounds.player_hurt_crystal }

        override fun calculateDamage(entity: LivingEntity, damage: Float): Float {
            //any damage over 1 heart is exponential
            val before = getHealth(entity)

            val damageMod = damage.coerceAtMost(2f) + damage.minus(2).coerceAtLeast(0f).pow(1.32f)
            val after = getHealth(entity).minus(damageMod).coerceAtLeast(0f)
            setHealth(entity, after)

            LCCComponents.hearts.maybeGet(entity).ifPresent {
                it.crystalRegenAmount = before - after
                it.crystalRegenTick = entity.world.time
            }
            LCCComponents.hearts.sync(entity)

            val maxAbsorbable = before.coerceAtMost(2f) + before.minus(2).coerceAtLeast(0f).pow(1/1.32f)
            return damage - maxAbsorbable
        }

        override fun tick(entity: LivingEntity): Boolean {
            val hearts = LCCComponents.hearts.maybeGet(entity).orElse(null) ?: return false
            if (hearts.crystalRegenAmount > 0 && entity.world.time > hearts.crystalRegenTick + 60) {
                val amount = 0.0005F * entity.world.time.minus(hearts.crystalRegenTick + 60).coerceAtLeast(0)
                addHealth(entity, amount, sync = false)
                hearts.crystalRegenAmount = hearts.crystalRegenAmount.minus(amount).coerceAtLeast(0f)
                if (hearts.crystalRegenAmount <= 0f) return true
            }
            return false
        }
    },
    TEMPORARY {
        override val v = 45
        override val hurtColor = 0xB2AA0000.toInt()
        override val sortOrder = 0
        override val container = false

        override val hurtSound by lazy { LCCSounds.player_hurt_temporary }

        override fun getMaxHealth(entity: LivingEntity) = getHealth(entity)

        override fun setMaxHealth(entity: LivingEntity, amount: Float, limit: Float, sync: Boolean) = Unit

        override fun getDefaultLimit(entity: LivingEntity) = 20f

        override fun tick(entity: LivingEntity): Boolean {
            val before = getHealth(entity)
            if (before <= 0f) return false
            addHealth(entity, -0.012F, sync = false)
            val after = getHealth(entity)
            if (before.toInt() != after.toInt() || after <= 0f) return true
            return false
        }
    };

    open val container = true
    open val drawable = true
    abstract val v: Int
    abstract val sortOrder: Int
    open val hurtColor = 0xB20000FF.toInt()

    open val remember = false
    open val rememberMax = false

    open val hurtSound: SoundEvent? = null

    //Render Caches
    @Environment(EnvType.CLIENT)
    var lastHealthValue = 0
    @Environment(EnvType.CLIENT)
    var renderHealthValue = 0
    @Environment(EnvType.CLIENT)
    var lastHealthCheckTime = 0L
    @Environment(EnvType.CLIENT)
    var heartJumpEndTick = 0L

    open fun getHealth(entity: LivingEntity) = LCCComponents.hearts.maybeGet(entity).map { it.getHealth(this) }.orElse(0f)

    open fun setHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultLimit(entity), sync: Boolean = true) {
        LCCComponents.hearts.maybeGet(entity).ifPresent { it.setHealth(this, amount.coerceIn(0f, limit)) }
        update(entity, amount, getMaxHealth(entity))
        if (sync) LCCComponents.hearts.sync(entity)
    }

    fun addHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultLimit(entity), sync: Boolean = true) = setHealth(entity, getHealth(entity) + amount, limit, sync)

    open fun getMaxHealth(entity: LivingEntity) = LCCComponents.hearts.maybeGet(entity).map { it.getMaxHealth(this) }.orElse(0f)

    open fun setMaxHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultMaxLimit(entity), sync: Boolean = true) {
        LCCComponents.hearts.maybeGet(entity).ifPresent { it.setMaxHealth(this, amount.coerceIn(0f, limit)) }
        update(entity, getHealth(entity), amount)
        if (sync) LCCComponents.hearts.sync(entity)
    }

    fun addMaxHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultMaxLimit(entity), sync: Boolean = true) = setMaxHealth(entity, getMaxHealth(entity) + amount, limit, sync)

    open fun getDefaultLimit(entity: LivingEntity) = getMaxHealth(entity)

    open fun getDefaultMaxLimit(entity: LivingEntity) = 20f

    open fun tick(entity: LivingEntity) = false

    open fun calculateDamage(entity: LivingEntity, damage: Float): Float {
        val before = getHealth(entity)
        val after = getHealth(entity).minus(damage).coerceAtLeast(0f)
        setHealth(entity, after, sync = false)
        val damage2 = before - after
        return damage - damage2
    }

    open fun update(entity: LivingEntity, amount: Float, maxAmount: Float) = Unit

    override fun asString() = name.toLowerCase()

    val tag get() = asString().capitalize()

    companion object {
        @JvmStatic
        fun calculateDamageAll(entity: LivingEntity, damage: Float): Float {
            val hearts = LCCComponents.hearts.maybeGet(entity).orElse(null) ?: return damage
            hearts.crystalRegenAmount = 0f
            hearts.crystalRegenTick = 0L
            if (damage <= 0F) {
                return 0f
            }
            var d = damage
            for (ht in values().filter { it.getHealth(entity) > 0 }.sortedBy { it.sortOrder }) {
                d = ht.calculateDamage(entity, d).coerceAtLeast(0f)
                hearts.damageLayer = ht
                if (d <= 1.0E-5f) break
            }
            if (d > 1.0E-5f) hearts.damageLayer = RED
            LCCComponents.hearts.sync(entity)
            return d
        }
    }

}