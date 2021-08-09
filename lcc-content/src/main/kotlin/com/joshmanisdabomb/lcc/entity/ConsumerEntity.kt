package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.RangedAttackMob
import net.minecraft.entity.ai.control.LookControl
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class ConsumerEntity(entityType: EntityType<out ConsumerEntity>, world: World) : HostileEntity(entityType, world), RangedAttackMob, LCCContentEntityTrait {

    internal var tongueEntity: ConsumerTongueEntity? = null
    val tongue get() = tongueEntity

    init {
        moveControl = ConsumerMoveControl()
        lookControl = ConsumerLookControl()
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(tongue_id, 0)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        if (data == tongue_id) {
            val id = dataTracker.get(tongue_id)
            this.tongueEntity = if (id > 0) world.getEntityById(id - 1) as? ConsumerTongueEntity else null
        }
        super.onTrackedDataSet(data)
    }

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(2, object : PounceAtTargetGoal(this, 0.3f) {
            override fun canStart() = super.canStart() && this@ConsumerEntity.tongueEntity?.isRemoved != false
            override fun shouldContinue() = super.shouldContinue() && this@ConsumerEntity.tongueEntity?.isRemoved != false
        })
        goalSelector.add(3, ConsumerMeleeAttack())
        goalSelector.add(4, ProjectileAttackGoal(this, 1.0, 30, 13.0F))
        goalSelector.add(5, WanderAroundFarGoal(this, 0.7))
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(6, LookAroundGoal(this))
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(2, FollowTargetGoal(this, PlayerEntity::class.java, true))
        targetSelector.add(3, FollowTargetGoal(this, IronGolemEntity::class.java, true))
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason): Boolean {
        return true
    }

    override fun attack(target: LivingEntity, pullProgress: Float) {
        val tongue = tongueEntity
        if (tongue != null && !tongue.isRemoved) return

        val dist = this.squaredDistanceTo(target)
        if (dist < 2048.0) {
            val entity = ConsumerTongueEntity(world, this)
            val e = target.x - this.x
            val f = (target.eyeY - 0.1) - entity.y
            val g = target.z - this.z
            entity.setVelocity(e, f, g, ConsumerTongueEntity.speed, 0.8f)
            playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0f, 0.4f / (getRandom().nextFloat() * 0.4f + 0.8f))
            world.spawnEntity(entity)
            tongueEntity = entity
        } else {
            this.target = null
        }
    }

    override fun tick() {
        super.tick()
        val tongue = tongueEntity
        if (tongue != null && !tongue.isRemoved) lookControl.lookAt(tongue)
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
    }

    override fun getJumpVelocity() = super.getJumpVelocity().times(1.4f)

    override fun dropLoot(source: DamageSource, causedByPlayer: Boolean) {
        if (lootTable == type.lootTableId && tongue?.isRemoved == false) {
            this.world.server?.lootManager?.getTable(type.lootTableId.suffix("tongue"))?.generateLoot(this.getLootContextBuilder(causedByPlayer, source).build(LootContextTypes.ENTITY), this::dropStack)
        }
        super.dropLoot(source, causedByPlayer)
    }

    override fun damage(source: DamageSource, amount: Float) = super.damage(source, ToolEffectivity.WASTELAND.reduceDamageTaken(this, source, amount))

    override fun lcc_content_applyDamageThroughArmor(attacked: LivingEntity, after: Float, armor: Float, toughness: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original)
    }

    override fun lcc_content_applyDamageThroughProtection(attacked: LivingEntity, after: Float, protection: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original, 1f)
    }

    companion object {
        val tongue_id = DataTracker.registerData(ConsumerEntity::class.java, TrackedDataHandlerRegistry.INTEGER)

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 29.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0)
        }
    }

    inner class ConsumerLookControl : LookControl(this@ConsumerEntity) {
        override fun tick() {
            val tongue = tongueEntity
            if (tongue != null && !tongue.isRemoved) {
                val hooked = tongue.hooked
                val d = (hooked ?: tongue).x - this@ConsumerEntity.x
                val e = (hooked?.y?.plus(hooked.height.div(2f)) ?: tongue.y) - tongue.targetY!!
                val f = (hooked ?: tongue).z - this@ConsumerEntity.z
                val g = MathHelper.sqrt(d * d + f * f).toDouble()
                val h = MathHelper.wrapDegrees((-(MathHelper.atan2(e, g) * 57.2957763671875)).toFloat())
                val i = MathHelper.wrapDegrees((MathHelper.atan2(f, d) * 57.2957763671875).toFloat() - 90.0f)
                entity.headYaw = this.changeAngle(entity.headYaw, i, 20.0f)
                entity.pitch = this.changeAngle(entity.pitch, h, 20.0f)
            } else {
                super.tick()
            }
        }

        override fun shouldStayHorizontal(): Boolean {
            val tongue = tongueEntity
            return tongue == null || tongue.isRemoved
        }
    }

    inner class ConsumerMoveControl : MoveControl(this@ConsumerEntity) {
        override fun tick() {
            val tongue = tongueEntity
            if (tongue != null && !tongue.isRemoved) {
                entity.setForwardSpeed(0.0f)
                entity.setSidewaysSpeed(0.0f)
            } else {
                super.tick()
            }
        }
    }

    inner class ConsumerMeleeAttack : MeleeAttackGoal(this, 1.4, false) {

        override fun canStart() = super.canStart() && tongueEntity?.isRemoved != false && mob?.target?.squaredDistanceTo(this@ConsumerEntity)?.let { it < 24.0 } == true

        override fun shouldContinue() = super.shouldContinue() && tongueEntity?.isRemoved != false && mob?.target?.squaredDistanceTo(this@ConsumerEntity)?.let { it < 24.0 } == true

    }

}