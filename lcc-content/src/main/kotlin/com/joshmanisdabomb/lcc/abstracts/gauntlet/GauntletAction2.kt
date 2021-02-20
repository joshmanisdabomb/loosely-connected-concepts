package com.joshmanisdabomb.lcc.abstracts.gauntlet

import com.joshmanisdabomb.lcc.directory.LCCComponents
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.UseAction
import net.minecraft.util.math.MathHelper

abstract class GauntletAction2<A : GauntletActorInstance> {

    fun hasInfo(actor: PlayerEntity) = getInfo(actor) != null
    fun getInfo(actor: PlayerEntity) = LCCComponents.gauntlet_actor[actor][this]

    fun isCasting(actor: PlayerEntity) = getInfo(actor)?.isCasting == true
    fun isCooldown(actor: PlayerEntity) = getInfo(actor)?.isCooldown == true

    abstract fun newActorInstance(actor: PlayerEntity): A
    abstract fun initActorInstance(instance: A)

    fun act(actor: PlayerEntity, remaining: Int = 0, only: (info: A?) -> Boolean = { it == null }): Boolean {
        val ga = LCCComponents.gauntlet_actor.maybeGet(actor).orElse(null) ?: return false
        if (only(ga[this])) {
            val info = newActorInstance(actor)
            info.init(remaining, false)
            initActorInstance(info)
            if (info.maxCast < 0 || !info.initial(remaining, false)) {
                ga[this] = info
                return true
            }
        }
        return false
    }

    fun cancel(actor: PlayerEntity, remaining: Int): Boolean {
        val ga = LCCComponents.gauntlet_actor.maybeGet(actor).orElse(null) ?: return false
        val info = newActorInstance(actor)
        info.init(remaining, true)
        initActorInstance(info)
        if (info.maxCast < 0 || !info.initial(remaining, true)) {
            ga[this] = info
            return true
        }
        return false
    }

    open val maxChargeTime = 0
    open val chargeBiteTime = 0
    open val chargeAction = UseAction.BOW
    val canCharge get() = maxChargeTime > 0

    protected fun markFallHandler(entity: LivingEntity, actor: Boolean) {
        if (entity is PlayerEntity) LCCComponents.gauntlet_actor.maybeGet(entity).ifPresent { it.fallHandler = if (actor) this else null }
        LCCComponents.gauntlet_target.maybeGet(entity).ifPresent { it.fallHandler = if (!actor) this else null }
    }

    protected fun confirmVelocity(entity: Entity) {
        entity.velocityModified = true
        entity.velocityDirty = true
    }

    protected fun damageReady(entity: Entity) {
        if (entity is LivingEntity) entity.hurtTime = 0
        entity.timeUntilRegen = 0
    }

    protected fun cappedDamage(entity: Entity, modifier: Float, range: ClosedFloatingPointRange<Float>) = (entity as? LivingEntity)?.maxHealth?.times(modifier)?.coerceIn(range) ?: range.start

    open fun chargeTick(player: PlayerEntity, remaining: Int) = Unit

    open fun actorFall(actor: PlayerEntity, distance: Float, multiplier: Float): Int? = null
    open fun targetFall(target: LivingEntity, distance: Float, multiplier: Float): Int? = null

    protected fun handleFall(entity: LivingEntity, distance: Float, multiplier: Float, distanceBreak: Float) = MathHelper.ceil((distance - 3 - distanceBreak - (entity.getStatusEffect(StatusEffects.JUMP_BOOST)?.amplifier?.plus(1) ?: 0)) * multiplier)

    companion object {
        fun getFromTag(tag: CompoundTag?) = GauntletDirectory.getOrNull(tag?.getString("Selected") ?: "") ?: UppercutGauntletAction
        fun putInTag(ability: GauntletAction2<*>, tag: CompoundTag) = tag.putString("Selected", GauntletDirectory[ability].name)
    }

}