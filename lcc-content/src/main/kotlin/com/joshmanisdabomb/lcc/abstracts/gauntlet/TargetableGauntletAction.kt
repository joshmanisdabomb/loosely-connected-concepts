package com.joshmanisdabomb.lcc.abstracts.gauntlet

import com.joshmanisdabomb.lcc.directory.LCCComponents
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.entity.vehicle.MinecartEntity

interface TargetableGauntletAction<A : GauntletActorInstance, T : GauntletTargetInstance<A>> {

    fun newTargetInstance(target: Entity): T
    fun initTargetInstance(instance: T, actor: PlayerEntity, info: A)

    fun target(target: Entity, actor: PlayerEntity, info: A): Boolean {
        if (canBeTarget(target, actor)) {
            val gt = LCCComponents.gauntlet_target.maybeGet(target).orElse(null) ?: return false
            val i = this.newTargetInstance(target).apply {
                this.actor = actor
                init(actor, info)
                initTargetInstance(this, actor, info)
            }
            if (!i.initial(actor, info)) {
                gt.add(i)
                return true
            }
        }
        return false
    }

    fun hasTargetEffect(target: Entity, actor: PlayerEntity) = LCCComponents.gauntlet_target.maybeGet(target).map { it.instances.any { it.actor == actor } }.orElse(false)

    fun isTargettable(target: Entity, actor: PlayerEntity) = when (target) {
        is LivingEntity -> target.deathTime <= 0 && (target !is PlayerEntity || (!target.isCreative && !actor.isSpectator))
        is BoatEntity -> true
        is MinecartEntity -> true
        else -> false
    }

    fun canTargetStack(target: Entity, actor: PlayerEntity) = false

    fun canBeTarget(target: Entity, actor: PlayerEntity) = isTargettable(target, actor) && (canTargetStack(target, actor) || !hasTargetEffect(target, actor))

}