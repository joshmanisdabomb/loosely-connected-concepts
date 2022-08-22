package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCAttributes
import com.joshmanisdabomb.lcc.directory.LCCEffects
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.directory.component.LCCComponents
import com.joshmanisdabomb.lcc.directory.tags.LCCBiomeTags
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.item.KnifeItem.Companion.knockback_resistance_modifier_uuid
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.*
import net.minecraft.entity.ai.control.LookControl
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.ai.pathing.SpiderNavigation
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.world.*
import kotlin.math.max

class PsychoPigEntity(type: EntityType<out PsychoPigEntity>, world: World) : HostileEntity(type, world), LCCContentEntityTrait {

    var aggroTarget: LivingEntity? = null
    val isAggro get() = aggroTarget?.isRemoved == false

    var eyeLeft = false

    init {
        lookControl = PsychoPigLookControl()
    }

    override fun initialize(world: ServerWorldAccess, difficulty: LocalDifficulty, spawnReason: SpawnReason, entityData: EntityData?, entityNbt: NbtCompound?): EntityData? {
        eyeLeft = random.nextBoolean()
        dataTracker.set(eye_left, eyeLeft)

        initEquipment(random, difficulty)
        updateEnchantments(random, difficulty)

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt)
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(eye_left, false)
        dataTracker.startTracking(aggro_id, 0)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        super.onTrackedDataSet(data)
        if (data == eye_left) {
            eyeLeft = dataTracker.get(eye_left)
        }
        if (data == aggro_id) {
            val id = dataTracker.get(aggro_id)
            this.aggroTarget = if (id > 0) world.getEntityById(id - 1) as? LivingEntity else null
        }
    }

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(3, PsychoPigMeleeAttackGoal())
        goalSelector.add(5, WanderAroundFarGoal(this, 0.7))
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(7, LookAroundGoal(this))
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(2, ActiveTargetGoal(this, PlayerEntity::class.java, 100, true, false, this::aggression))
        targetSelector.add(2, RevengeGoal(this))
    }

    private fun aggression(entity: LivingEntity): Boolean {
        if (entity.uuid == target?.uuid) return true
        val vec = Vec3d(entity.x - this.x, entity.eyeY - this.eyeY, entity.z - this.z)
        if (vec.lengthSquared() > 256.0) return false
        return this.getRotationVec(1.0f).normalize().dotProduct(vec.normalize()) > 0.6
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason): Boolean {
        return true
    }

    override fun getPathfindingFavor(pos: BlockPos, world: WorldView): Float {
        if (!world.getBiome(pos).isIn(LCCBiomeTags.wasteland)) return -100.0f
        return 0.0f
    }

    override fun createNavigation(world: World) = SpiderNavigation(this, world)

    override fun canSee(entity: Entity) = entity == target || super.canSee(entity)

    override fun getBaseMovementSpeedMultiplier() = 0.6f

    override fun tick() {
        super.tick()
        calculateDimensions()
        val target = aggroTarget
        if (target != null) {
            if (isAlive) {
                if (!target.hasStatusEffect(LCCEffects.fear) && !world.isClient) {
                    LCCComponents.targeted_effects.maybeGet(target).ifPresent { it.add(LCCEffects.fear, this) }
                    LCCComponents.targeted_effects.sync(target)
                }
                target.addStatusEffect(StatusEffectInstance(LCCEffects.fear, Int.MAX_VALUE, 0))
            }
            lookControl.lookAt(target)
            if (!onGround && isTouchingWater && isAttacking) {
                val x = target.x - this.x
                val z = target.z - this.z
                velocity = velocity.add(Vec3d(x, 0.0, z).normalize().multiply(0.04))
                airStrafingSpeed = 0.02f
            }
        }
    }

    override fun move(type: MovementType, movement: Vec3d) {
        if (isTouchingWater && isAttacking) {
            super.move(type, movement.multiply(1.0, 0.0, 1.0).normalize().multiply(0.33).add(0.0, movement.y, 0.0))
        }
        super.move(type, movement)
    }

    override fun calculateBoundingBox() = super.calculateBoundingBox().stretch(0.0, isAggro.transform(0.8, 0.0), 0.0)

    override fun getActiveEyeHeight(pose: EntityPose, dimensions: EntityDimensions) = super.getActiveEyeHeight(pose, dimensions).plus(isAggro.transform(0.7f, 0.0f))

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putBoolean("LeftEyed", this.eyeLeft)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        nbt.getBoolean("LeftEyed").also { eyeLeft = it; dataTracker.set(eye_left, it) }
    }

    override fun setTarget(target: LivingEntity?) {
        super.setTarget(target)
        aggroTarget = target
        dataTracker.set(aggro_id, target?.id?.plus(1) ?: 0)
    }

    override fun getAmbientSound() = isAggro.transform(LCCSounds.consumer_ambient, SoundEvents.ENTITY_PIG_AMBIENT)

    override fun getHurtSound(source: DamageSource) = isAggro.transform(LCCSounds.consumer_hurt, SoundEvents.ENTITY_PIG_HURT)

    override fun getDeathSound() = isAggro.transform(LCCSounds.consumer_death, SoundEvents.ENTITY_PIG_DEATH)

    override fun tryAttack(target: Entity): Boolean {
        val kbResistance = (target as? LivingEntity)?.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
        if (kbResistance?.getModifier(knockback_resistance_modifier_uuid) == null) {
            kbResistance?.addTemporaryModifier(EntityAttributeModifier(knockback_resistance_modifier_uuid, "Knife knockback resistance", 1.0, EntityAttributeModifier.Operation.ADDITION))
        }
        val ret = super.tryAttack(target)
        kbResistance?.removeModifier(knockback_resistance_modifier_uuid)
        if (ret && this.mainHandStack.isOf(LCCItems.knife) && target is LivingEntity) {
            val difficulty = world.getLocalDifficulty(blockPos).localDifficulty
            target.addStatusEffect(StatusEffectInstance(LCCEffects.bleeding, 120 * difficulty.toInt().plus(1)), this)
        }
        return ret
    }

    override fun initEquipment(random: Random, difficulty: LocalDifficulty) {
        equipStack(EquipmentSlot.MAINHAND, ItemStack(LCCItems.knife).also { it.damage = it.maxDamage - random.nextInt(it.maxDamage.div(2)) })
        setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.04f)
    }

    override fun dropEquipment(source: DamageSource, lootingMultiplier: Int, allowDrops: Boolean) {
        for (slot in EquipmentSlot.values()) {
            val itemStack = getEquippedStack(slot)
            val f = getDropChance(slot)
            val alwaysDrops = f > 1.0f
            if (itemStack.isEmpty || EnchantmentHelper.hasVanishingCurse(itemStack) || !allowDrops && !alwaysDrops || max(random.nextFloat() - lootingMultiplier.toFloat() * 0.01f, 0.0f) >= f) continue
            this.dropStack(itemStack)
            equipStack(slot, ItemStack.EMPTY)
        }
    }

    companion object {
        val eye_left = DataTracker.registerData(PsychoPigEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
        val aggro_id = DataTracker.registerData(PsychoPigEntity::class.java, TrackedDataHandlerRegistry.INTEGER)

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_ARMOR, 9.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 96.0).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0).add(LCCAttributes.wasteland_damage, 1.0).add(LCCAttributes.wasteland_protection, 1.0)
        }
    }

    inner class PsychoPigMeleeAttackGoal : MeleeAttackGoal(this, 1.3, false) {

        private var overrideTickCount: Int? = null

        override fun getTickCount(ticks: Int): Int {
            return overrideTickCount ?: super.getTickCount(ticks)
        }

        override fun resetCooldown() {
            overrideTickCount = 4
            super.resetCooldown()
            overrideTickCount = null
        }

    }

    inner class PsychoPigLookControl : LookControl(this@PsychoPigEntity) {
        override fun tick() {
            val target = aggroTarget
            if (target != null) {
                val d = target.x - this@PsychoPigEntity.x
                val e = target.eyeY - this@PsychoPigEntity.eyeY
                val f = target.z - this@PsychoPigEntity.z
                val g = MathHelper.sqrt((d * d + f * f).toFloat()).toDouble()
                val h = MathHelper.wrapDegrees((-(MathHelper.atan2(e, g) * 57.2957763671875)).toFloat())
                val i = MathHelper.wrapDegrees((MathHelper.atan2(f, d) * 57.2957763671875).toFloat() - 90.0f)
                entity.headYaw = this.changeAngle(entity.headYaw, i, 20.0f)
                entity.pitch = this.changeAngle(entity.pitch, h, 20.0f)
            } else {
                super.tick()
            }
        }

        override fun shouldStayHorizontal() = !isAggro
    }

}