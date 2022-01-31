package com.joshmanisdabomb.lcc.entity.ai

import net.minecraft.entity.ai.TargetPredicate
import net.minecraft.entity.ai.goal.TrackTargetGoal
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.TameableEntity

class TrackOwnerTargetGoal(private val tameable: TameableEntity, val predicate: TargetPredicate = TargetPredicate.createAttackable(), checkVisibility: Boolean = false) : TrackTargetGoal(tameable, checkVisibility) {

    private var owner: MobEntity? = null

    override fun canStart(): Boolean {
        val owner = tameable.owner as? MobEntity ?: return false
        this.owner = owner
        return owner.target != null && canTrack(owner.target, predicate)
    }

    override fun start() {
        tameable.target = owner?.target
    }

}
