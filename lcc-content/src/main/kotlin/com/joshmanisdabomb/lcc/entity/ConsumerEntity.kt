package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCBiomes
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.suffix
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.Entity
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
import net.minecraft.entity.passive.MerchantEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class ConsumerEntity(entityType: EntityType<out ConsumerEntity>, world: World) : HostileEntity(entityType, world), RangedAttackMob, LCCContentEntityTrait {

    var aggroTarget: LivingEntity? = null
    val isAggro get() = aggroTarget?.isRemoved == false

    var tongue: ConsumerTongueEntity? = null
    val isTongueActive get() = tongue?.isRemoved == false

    @Environment(EnvType.CLIENT)
    var jawPitch = 0f
    @Environment(EnvType.CLIENT)
    var lastJawPitch = 0f

    init {
        moveControl = ConsumerMoveControl()
        lookControl = ConsumerLookControl()
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(aggro_id, 0)
        dataTracker.startTracking(tongue_id, 0)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        if (data == tongue_id) {
            val id = dataTracker.get(tongue_id)
            this.tongue = if (id > 0) world.getEntityById(id - 1) as? ConsumerTongueEntity else null
        }
        if (data == aggro_id) {
            val id = dataTracker.get(aggro_id)
            this.aggroTarget = if (id > 0) world.getEntityById(id - 1) as? LivingEntity else null
        }
        super.onTrackedDataSet(data)
    }

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(2, object : PounceAtTargetGoal(this, 0.3f) {
            override fun canStart() = super.canStart() && !isTongueActive
            override fun shouldContinue() = super.shouldContinue() && !isTongueActive
        })
        goalSelector.add(3, ConsumerMeleeAttackGoal())
        goalSelector.add(4, ProjectileAttackGoal(this, 1.0, 56, 13.0F))
        goalSelector.add(5, WanderAroundFarGoal(this, 0.7))
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(6, LookAroundGoal(this))
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(2, ActiveTargetGoal(this, PlayerEntity::class.java, true))
        targetSelector.add(3, ActiveTargetGoal(this, IronGolemEntity::class.java, false, true))
        targetSelector.add(4, ActiveTargetGoal(this, MerchantEntity::class.java, false, true))
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason): Boolean {
        return true
    }

    override fun getPathfindingFavor(pos: BlockPos, world: WorldView): Float {
        if (LCCBiomes.getOrNull(world.getBiome(pos))?.tags?.contains("wasteland") != true) return -100.0f
        return 0.0f
    }

    override fun attack(target: LivingEntity, pullProgress: Float) {
        if (isTongueActive) return

        val dist = this.squaredDistanceTo(target)
        if (dist < 2048.0) {
            val entity = ConsumerTongueEntity(world, this)
            val e = target.x - this.x
            val f = (target.eyeY - 0.1) - entity.y
            val g = target.z - this.z
            entity.setVelocity(e, f, g, ConsumerTongueEntity.tongueSpeed, 0.8f)
            val h = Vec3d(e, f, g).normalize().multiply(0.01)
            entity.setPosition(this.x + h.x, entity.getTargetY()!! + h.y, this.z + h.z)
            playSound(LCCSounds.consumer_tongue_shoot, 2.5f, random.nextFloat().times(0.2f).plus(0.9f))
            world.spawnEntity(entity)
            tongue = entity
            dataTracker.set(tongue_id, entity.id.plus(1))
        } else {
            this.target = null
        }
    }

    override fun tick() {
        super.tick()
        if (world.isClient) lastJawPitch = jawPitch
        if (isTongueActive) {
            lookControl.lookAt(tongue)
        } else if (canBiteTarget(target)) {
            ambientSoundChance += 6
        }
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
    }

    override fun setTarget(target: LivingEntity?) {
        super.setTarget(target)
        aggroTarget = target
        dataTracker.set(aggro_id, target?.id?.plus(1) ?: 0)
    }

    fun canBiteTarget(target: LivingEntity? = this.target): Boolean {
        if (target == null) return false
        return target.squaredDistanceTo(this) < 40.0
    }

    override fun getAmbientSound(): SoundEvent {
        if (!isTongueActive && canBiteTarget(target)) {
            return LCCSounds.consumer_cqc
        }
        return LCCSounds.consumer_ambient
    }

    override fun getHurtSound(source: DamageSource) = LCCSounds.consumer_hurt

    override fun getDeathSound() = LCCSounds.consumer_death

    override fun tryAttack(target: Entity): Boolean {
        if (super.tryAttack(target)) {
            playSound(LCCSounds.consumer_attack, soundVolume, soundPitch)
            return true
        }
        return false
    }

    override fun getJumpVelocity() = super.getJumpVelocity().times(1.4f)

    override fun dropLoot(source: DamageSource, causedByPlayer: Boolean) {
        if (lootTable == type.lootTableId && isTongueActive) {
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
        val aggro_id = DataTracker.registerData(ConsumerEntity::class.java, TrackedDataHandlerRegistry.INTEGER)

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 29.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0)
        }
    }

    inner class ConsumerLookControl : LookControl(this@ConsumerEntity) {
        override fun tick() {
            val tongue = tongue
            if (tongue?.isRemoved == false) {
                val hooked = tongue.hooked
                val d = (hooked ?: tongue).x - this@ConsumerEntity.x
                val e = (hooked?.y?.plus(hooked.height.div(2f)) ?: tongue.y) - tongue.getTargetY()!!
                val f = (hooked ?: tongue).z - this@ConsumerEntity.z
                val g = MathHelper.sqrt((d * d + f * f).toFloat()).toDouble()
                val h = MathHelper.wrapDegrees((-(MathHelper.atan2(e, g) * 57.2957763671875)).toFloat())
                val i = MathHelper.wrapDegrees((MathHelper.atan2(f, d) * 57.2957763671875).toFloat() - 90.0f)
                entity.headYaw = this.changeAngle(entity.headYaw, i, 20.0f)
                entity.pitch = this.changeAngle(entity.pitch, h, 20.0f)
            } else {
                super.tick()
            }
        }

        override fun shouldStayHorizontal() = !isTongueActive
    }

    inner class ConsumerMoveControl : MoveControl(this@ConsumerEntity) {
        override fun tick() {
            if (isTongueActive) {
                entity.setForwardSpeed(0.0f)
                entity.setSidewaysSpeed(0.0f)
            } else {
                super.tick()
            }
        }
    }

    inner class ConsumerMeleeAttackGoal : MeleeAttackGoal(this, 1.4, false) {

        override fun canStart() = super.canStart() && !isTongueActive && canBiteTarget(target)

        override fun shouldContinue() = super.shouldContinue() && !isTongueActive && canBiteTarget(target)

    }

}