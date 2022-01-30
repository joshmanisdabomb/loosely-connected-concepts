package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.minecraft.entity.EntityData
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.control.LookControl
import net.minecraft.entity.ai.goal.ActiveTargetGoal
import net.minecraft.entity.ai.goal.LookAroundGoal
import net.minecraft.entity.ai.goal.LookAtEntityGoal
import net.minecraft.entity.ai.goal.RevengeGoal
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import java.util.*

class RotwitchEntity(type: EntityType<out RotwitchEntity>, world: World) : HostileEntity(type, world), LCCContentEntityTrait {

    init {
        lookControl = RotwitchLookControl()
    }

    override fun initialize(world: ServerWorldAccess, difficulty: LocalDifficulty, spawnReason: SpawnReason, entityData: EntityData?, entityNbt: NbtCompound?): EntityData? {
        yaw = 0.0f
        headYaw = yaw
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt)
    }

    override fun initGoals() {
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(7, LookAroundGoal(this))
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(2, ActiveTargetGoal(this, PlayerEntity::class.java, true, false))
        targetSelector.add(2, RevengeGoal(this))
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason): Boolean {
        return true
    }

    override fun getBaseMovementSpeedMultiplier() = 0.0f

    override fun stopRiding() {
        super.stopRiding()
        prevBodyYaw = 0.0f
        bodyYaw = 0.0f
    }

    override fun readFromPacket(packet: MobSpawnS2CPacket) {
        super.readFromPacket(packet)
        bodyYaw = 0.0f
        prevBodyYaw = 0.0f
    }

    override fun tick() {
        super.tick()
    }

    override fun turnHead(bodyRotation: Float, headRotation: Float) = headRotation

    override fun getMaxLookPitchChange() = 180

    override fun getMaxHeadRotation() = 180

    override fun takeKnockback(strength: Double, x: Double, z: Double) {
        super.takeKnockback(strength.div(2.0), x, z)
    }

    override fun lcc_content_applyDamageThroughArmor(attacked: LivingEntity, after: Float, armor: Float, toughness: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original)
    }

    override fun lcc_content_applyDamageThroughProtection(attacked: LivingEntity, after: Float, protection: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original, 1f)
    }

    override fun getAmbientSound() = LCCSounds.consumer_ambient

    override fun getHurtSound(source: DamageSource) = LCCSounds.consumer_hurt

    override fun getDeathSound() = LCCSounds.consumer_death

    companion object {

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 34.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.0)
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