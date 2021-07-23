package com.joshmanisdabomb.lcc.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.RangedAttackMob
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class ConsumerEntity(entityType: EntityType<out ConsumerEntity>, world: World) : HostileEntity(entityType, world), RangedAttackMob {

    override fun initDataTracker() {
        super.initDataTracker()
    }

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(2, PounceAtTargetGoal(this, 0.3f))
        goalSelector.add(3, object : MeleeAttackGoal(this, 1.4, false) {

            override fun canStart() = super.canStart() && mob?.target?.squaredDistanceTo(this@ConsumerEntity)?.let { it < 24.0 } == true

            override fun shouldContinue() = super.shouldContinue() && mob?.target?.squaredDistanceTo(this@ConsumerEntity)?.let { it < 24.0 } == true

        })
        goalSelector.add(4, ProjectileAttackGoal(this, 1.0, 60, 15.0F))
        goalSelector.add(5, WanderAroundFarGoal(this, 0.7))
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(6, LookAroundGoal(this))
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(2, FollowTargetGoal(this, PlayerEntity::class.java, true))
        targetSelector.add(3, FollowTargetGoal(this, IronGolemEntity::class.java, true))
    }

    override fun attack(target: LivingEntity, pullProgress: Float) {
        val entity = ConsumerTongueEntity(world, this)
        val e = target.x - this.x
        val f = (target.eyeY - 0.1) - entity.y
        val g = target.z - this.z
        entity.setVelocity(e, f, g, 1.6f, 2f)
        playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0f, 0.4f / (getRandom().nextFloat() * 0.4f + 0.8f))
        world.spawnEntity(entity)
    }

    override fun tick() {
        super.tick()
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
    }

    override fun getJumpVelocity() = super.getJumpVelocity().times(1.4f)

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 57.0 /* TODO increase health for wasteland weapons */).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0)
        }
    }

}