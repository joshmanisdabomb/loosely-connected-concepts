package com.joshmanisdabomb.lcc.entity

import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityPose
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.BowAttackGoal
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.AbstractSkeletonEntity
import net.minecraft.entity.projectile.ProjectileUtil
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class BabySkeletonEntity(type: EntityType<out BabySkeletonEntity>, world: World) : AbstractSkeletonEntity(type, world) {

    override fun updateAttackType() {
        if (world?.isClient != false) return
        goalSelector.goals.filterIsInstance<MeleeAttackGoal>().forEach(goalSelector::remove)
        goalSelector.goals.filterIsInstance<BowAttackGoal<BabySkeletonEntity>>().forEach(goalSelector::remove)
        val stack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW))
        if (stack.isOf(Items.BOW)) {
            goalSelector.add(4, BowAttackGoal(this, 1.0, 0, 15.0f))
        } else {
            goalSelector.add(4, object : MeleeAttackGoal(this, 1.2, false) {
                override fun stop() {
                    super.stop()
                    this@BabySkeletonEntity.setAttacking(false)
                }

                override fun start() {
                    super.start()
                    this@BabySkeletonEntity.setAttacking(true)
                }
            })
        }
    }

    override fun isBaby() = true

    override fun getAmbientSound() = SoundEvents.ENTITY_SKELETON_AMBIENT

    override fun getHurtSound(source: DamageSource) = SoundEvents.ENTITY_SKELETON_HURT

    override fun getDeathSound() = SoundEvents.ENTITY_SKELETON_DEATH

    override fun getStepSound() = SoundEvents.ENTITY_SKELETON_STEP

    override fun getHeightOffset() = 0.0

    override fun getActiveEyeHeight(pose: EntityPose, dimensions: EntityDimensions) = 0.93f

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.375)
        }
    }

}