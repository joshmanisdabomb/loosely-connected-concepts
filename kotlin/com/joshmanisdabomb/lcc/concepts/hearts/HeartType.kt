package com.joshmanisdabomb.lcc.concepts.hearts

import com.joshmanisdabomb.lcc.concepts.heartsLastType
import com.joshmanisdabomb.lcc.directory.LCCTrackers
import com.joshmanisdabomb.lcc.entity.data.EntityDataManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.LivingEntity
import net.minecraft.util.StringIdentifiable
import org.jetbrains.annotations.NotNull

enum class HeartType(val amountManager: EntityDataManager<Float>? = null, val maxManager: EntityDataManager<Float>? = null) : StringIdentifiable {

    RED(maxManager = EntityDataManager("hearts_red_max", LCCTrackers.heartsRedMax)) {
        override val drawable = false
        override val v = -1
        override val sortOrder = 30

        override fun getHealth(entity: LivingEntity) = entity.health

        override fun setHealth(entity: LivingEntity, amount: Float, limit: Float) {
            entity.health = amount.coerceIn(0f, limit)
        }

        override fun getDefaultLimit(entity: LivingEntity) = entity.maxHealth

        override fun calculateDamage(entity: LivingEntity, damage: Float) = damage
    },
    IRON(EntityDataManager("hearts_iron", LCCTrackers.heartsIron), EntityDataManager("hearts_iron_max", LCCTrackers.heartsIronMax)) {
        override val v = 0
        override val hurtColor = 0xB288889E.toInt()
        override val sortOrder = 20
    },
    CRYSTAL(EntityDataManager("hearts_crystal", LCCTrackers.heartsCrystal), EntityDataManager("hearts_crystal_max", LCCTrackers.heartsCrystalMax)) {
        override val v = 18
        override val hurtColor = 0xB2EBCC34.toInt()
        override val sortOrder = 10
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
        @JvmStatic
        fun calculateDamageAll(entity: LivingEntity, damage: Float): Float {
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