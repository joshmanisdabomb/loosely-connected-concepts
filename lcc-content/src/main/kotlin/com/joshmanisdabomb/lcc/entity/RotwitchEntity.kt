package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCAttributes
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.minecraft.entity.EntityData
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.RangedAttackMob
import net.minecraft.entity.ai.control.LookControl
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class RotwitchEntity(type: EntityType<out RotwitchEntity>, world: World) : HostileEntity(type, world), LCCContentEntityTrait, RangedAttackMob {

    init {
        lookControl = RotwitchLookControl()
    }

    override fun initialize(world: ServerWorldAccess, difficulty: LocalDifficulty, spawnReason: SpawnReason, entityData: EntityData?, entityNbt: NbtCompound?): EntityData? {
        yaw = world.random.nextFloat().times(360f).minus(180f)
        headYaw = yaw
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt)
    }

    override fun initGoals() {
        goalSelector.add(1, ProjectileAttackGoal(this, 1.0, 8, 60, 40.0f))
        goalSelector.add(2, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(3, LookAroundGoal(this))
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(2, ActiveTargetGoal(this, PlayerEntity::class.java, true, false))
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason): Boolean {
        return true
    }

    override fun getBaseMovementSpeedMultiplier() = 0.0f

    override fun tick() {
        super.tick()
    }

    override fun attack(target: LivingEntity, pullProgress: Float) {
        if (onGround) velocity = velocity.add(0.0, 0.4, 0.0)

        if (!world.isClient) {
            val difficulty = world.getLocalDifficulty(blockPos)
            val flies = random.nextInt(3.plus(difficulty.localDifficulty.toInt())).plus(difficulty.isAtLeastHard.transformInt(2, 1))
            repeat (flies) {
                val fly = LCCEntities.fly.create(world) ?: return@repeat
                fly.setPosition(pos.x, pos.y + height.div(2.0), pos.z)
                fly.setVelocity(random.nextDouble().minus(0.5).times(2.0), 0.1, random.nextDouble().minus(0.5).times(2.0))
                world.spawnEntity(fly)
                fly.isTamed = true
                fly.ownerUuid = uuid
                fly.target = target
            }
        }
        this.playSound(LCCSounds.rotwitch_heave, 1.5f, 1.0f)
        this.playSound(LCCSounds.rotwitch_hatch, 1.5f, this.soundPitch)
    }

    override fun turnHead(bodyRotation: Float, headRotation: Float) = headRotation

    override fun getMaxLookPitchChange() = 180

    override fun getMaxHeadRotation() = 180

    override fun takeKnockback(strength: Double, x: Double, z: Double) {
        super.takeKnockback(strength.div(2.0), x, z)
    }

    override fun getAmbientSound() = LCCSounds.rotwitch_idle

    override fun getHurtSound(source: DamageSource) = LCCSounds.rotwitch_hurt

    override fun getDeathSound() = LCCSounds.rotwitch_death

    companion object {

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 34.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.0).add(LCCAttributes.wasteland_protection, 1.0)
        }

    }

    inner class RotwitchLookControl : LookControl(this@RotwitchEntity) {

        override fun clampHeadYaw() = Unit

        override fun tick() {
            val yaw = targetYaw.orElseGet { null }
            if (yaw != null) {
                entity.headYaw = this.changeAngle(entity.headYaw, yaw, 20.0f)
            }
            entity.pitch = 0.0f
        }

        override fun getTargetPitch() = Optional.of(0.0f)

    }

}