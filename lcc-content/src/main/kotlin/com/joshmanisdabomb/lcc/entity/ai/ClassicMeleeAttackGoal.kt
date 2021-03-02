package com.joshmanisdabomb.lcc.entity.ai

import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.util.Hand
import kotlin.math.max

class ClassicMeleeAttackGoal(entity: PathAwareEntity, private val speed: Double) : MeleeAttackGoal(entity, speed, false) {

    private var targetX = 0.0
    private var targetY = 0.0
    private var targetZ = 0.0
    private var lastUpdateTime = 0L
    private var field_24667 = 0

    override fun canStart(): Boolean {
        val i: Long = mob.world.time
        return if (i - lastUpdateTime < 20L) {
            false
        } else {
            lastUpdateTime = i
            val livingentity = mob.target
            livingentity != null && livingentity.isAlive
        }
    }

    override fun shouldContinue(): Boolean {
        val livingentity = mob.target
        return if (livingentity == null) {
            false
        } else if (!livingentity.isAlive) {
            false
        } else {
            livingentity !is PlayerEntity || livingentity.isSurvival
        }
    }

    override fun start() {
        mob.isAttacking = true
        field_24667 = 0
    }

    override fun stop() {
        mob.isAttacking = false
        if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(mob.target ?: return)) {
            mob.target = null
        }
    }

    override fun tick() {
        val livingentity = mob.target ?: return
        mob.lookControl.lookAt(livingentity, 30.0f, 30.0f)

        this.targetX = livingentity.x
        this.targetY = livingentity.boundingBox.minY
        this.targetZ = livingentity.z
        mob.moveControl.moveTo(this.targetX, this.targetY, this.targetZ, this.speed)
        if (mob.horizontalCollision) mob.jumpControl.setActive()

        field_24667 = max(field_24667 - 1, 0)

        val d0 = mob.squaredDistanceTo(livingentity.x, livingentity.boundingBox.minY, livingentity.z)
        this.attack(livingentity, d0)
    }

    override fun attack(target: LivingEntity?, squaredDistance: Double) {
        val d = getSquaredMaxAttackDistance(target)
        if (squaredDistance <= d && this.field_24667 <= 0) {
            method_28346()
            mob.swingHand(Hand.MAIN_HAND)
            mob.tryAttack(target)
        }
    }

    override fun method_28346() { field_24667 = 20 }

    override fun method_28347() = field_24667 <= 0

    override fun method_28348() = field_24667

    override fun method_28349() = 20

}
