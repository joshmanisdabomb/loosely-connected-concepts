package com.joshmanisdabomb.lcc.concepts.hearts

import com.joshmanisdabomb.lcc.concepts.heartsLastType
import com.joshmanisdabomb.lcc.directory.LCCTrackers
import com.joshmanisdabomb.lcc.entity.data.EntityDataManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.StringIdentifiable
import org.jetbrains.annotations.NotNull
import java.util.*
import kotlin.math.pow

enum class HeartType(val amountManager: EntityDataManager<Float>? = null, val maxManager: EntityDataManager<Float>? = null) : StringIdentifiable {

    //TODO fix multiplayer desync

    RED() {
        private val id = UUID.fromString("a1494707-946f-462b-9c27-522404db24a0")

        override val drawable = false
        override val v = -1
        override val sortOrder = 30

        override fun getHealth(entity: LivingEntity) = entity.health

        override fun setHealth(entity: LivingEntity, amount: Float, limit: Float) {
            entity.health = amount.coerceIn(0f, limit)
        }

        override fun getMaxHealth(entity: LivingEntity) = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.getModifier(id)?.value?.toFloat() ?: 0f

        override fun setMaxHealth(entity: LivingEntity, amount: Float, limit: Float) {
            val modifier = createModifier(amount.toDouble())
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)?.apply {
                removeModifier(modifier.id)
                addPersistentModifier(modifier)
            }
        }

        override fun getDefaultLimit(entity: LivingEntity) = entity.maxHealth

        override fun calculateDamage(entity: LivingEntity, damage: Float) = damage

        fun createModifier(amount: Double) = EntityAttributeModifier(id, "LCC Red Heart Max Health", amount, EntityAttributeModifier.Operation.ADDITION)
    },
    IRON(EntityDataManager("hearts_iron", LCCTrackers.heartsIron), EntityDataManager("hearts_iron_max", LCCTrackers.heartsIronMax)) {
        override val v = 0
        override val hurtColor = 0xB288889E.toInt()
        override val sortOrder = 20

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
    CRYSTAL(EntityDataManager("hearts_crystal", LCCTrackers.heartsCrystal), EntityDataManager("hearts_crystal_max", LCCTrackers.heartsCrystalMax)) {
        override val v = 18
        override val hurtColor = 0xB2EBCC34.toInt()
        override val sortOrder = 10

        override fun calculateDamage(entity: LivingEntity, damage: Float): Float {
            //any damage over 1 heart is exponential
            val before = getHealth(entity)

            val damageMod = damage.coerceAtMost(2f) + damage.minus(2).coerceAtLeast(0f).pow(1.32f)
            val after = getHealth(entity).minus(damageMod).coerceAtLeast(0f)
            setHealth(entity, after)

            crystalRegen.toTracker(entity, CompoundTag().apply { putFloat("amount", before - after); putLong("tick", entity.world.time) })

            val maxAbsorbable = before.coerceAtMost(2f) + before.minus(2).coerceAtLeast(0f).pow(1/1.32f)
            return damage - maxAbsorbable
        }

        override fun tick(entity: LivingEntity) {
            val regen = crystalRegen.fromTracker(entity)
            if (regen.getFloat("amount") > 0 && entity.world.time > regen.getLong("tick") + 60) {
                val amount = 0.0005F * entity.world.time.minus(regen.getLong("tick") + 60).coerceAtLeast(0)
                addHealth(entity, amount)
                crystalRegen.modifyTracker(entity) { it.putFloat("amount", it.getFloat("amount").minus(amount).coerceAtLeast(0f)); it }
            }
        }
    },
    TEMPORARY(amountManager = EntityDataManager("hearts_temporary", LCCTrackers.heartsTemporary)) {
        override val v = 45
        override val hurtColor = 0xB2AA0000.toInt()
        override val sortOrder = 0
        override val container = false

        override fun getMaxHealth(entity: LivingEntity) = getHealth(entity)

        override fun setMaxHealth(entity: LivingEntity, amount: Float, limit: Float) = Unit

        override fun getDefaultLimit(entity: LivingEntity) = 20f

        override fun tick(entity: LivingEntity) {
            if (getHealth(entity) > 0) addHealth(entity, -0.012F)
        }
    };

    open val container = true
    open val drawable = true
    abstract val v: Int
    abstract val sortOrder: Int
    open val hurtColor = 0xB20000FF.toInt()

    //Render Caches
    @Environment(EnvType.CLIENT)
    var lastHealthValue = 0
    @Environment(EnvType.CLIENT)
    var renderHealthValue = 0
    @Environment(EnvType.CLIENT)
    var lastHealthCheckTime = 0L
    @Environment(EnvType.CLIENT)
    var heartJumpEndTick = 0L

    open fun getHealth(entity: LivingEntity) = amountManager?.fromTracker(entity) ?: 0f

    open fun setHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultLimit(entity)) = amountManager?.toTracker(entity, amount.coerceIn(0f, limit))

    fun addHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultLimit(entity)) = setHealth(entity, getHealth(entity) + amount)

    open fun getMaxHealth(entity: LivingEntity) = maxManager?.fromTracker(entity) ?: 0f

    open fun setMaxHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultMaxLimit(entity)) = maxManager?.toTracker(entity, amount.coerceIn(0f, limit))

    fun addMaxHealth(entity: LivingEntity, amount: Float, limit: Float = getDefaultMaxLimit(entity)) = setMaxHealth(entity, getMaxHealth(entity) + amount)

    open fun getDefaultLimit(entity: LivingEntity) = getMaxHealth(entity)

    open fun getDefaultMaxLimit(entity: LivingEntity) = 20f

    open fun tick(entity: LivingEntity) = Unit

    open fun calculateDamage(entity: LivingEntity, damage: Float): Float {
        val before = getHealth(entity)
        val after = getHealth(entity).minus(damage).coerceAtLeast(0f)
        setHealth(entity, after)
        val damage2 = before - after
        return damage - damage2
    }

    override fun asString() = name.toLowerCase()

    companion object {
        val crystalRegen by lazy { EntityDataManager("hearts_crystal_regen", LCCTrackers.heartsCrystalRegen) }

        @JvmStatic
        fun calculateDamageAll(entity: LivingEntity, damage: Float): Float {
            crystalRegen.resetTracker(entity)
            if (damage <= 0F) {
                heartsLastType.toTracker(entity, RED.ordinal.toByte())
                return 0f
            }
            var d = damage
            for (ht in values().filter { it.getHealth(entity) > 0 }.sortedBy { it.sortOrder }) {
                heartsLastType.toTracker(entity, ht.ordinal.toByte())
                d = ht.calculateDamage(entity, d).coerceAtLeast(0f)
                if (d <= 0) break
            }
            if (d > 0) heartsLastType.toTracker(entity, RED.ordinal.toByte())
            return d
        }
    }

}