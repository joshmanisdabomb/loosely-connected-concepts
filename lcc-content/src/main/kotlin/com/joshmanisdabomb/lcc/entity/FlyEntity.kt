package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.entity.ai.TrackOwnerTargetGoal
import com.joshmanisdabomb.lcc.extensions.getIntOrNull
import com.joshmanisdabomb.lcc.extensions.replaceVelocity
import com.joshmanisdabomb.lcc.trait.LCCEntityTrait
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityGroup
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.AboveGroundTargeting
import net.minecraft.entity.ai.NoPenaltySolidTargeting
import net.minecraft.entity.ai.control.FlightMoveControl
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.ai.pathing.BirdNavigation
import net.minecraft.entity.ai.pathing.EntityNavigation
import net.minecraft.entity.ai.pathing.PathNodeType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.EntityDamageSource
import net.minecraft.entity.mob.HostileEntity.createHostileAttributes
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.WorldView
import java.util.*

class FlyEntity(type: EntityType<out FlyEntity>, world: World) : TameableEntity(type, world), LCCEntityTrait {

    var lifetime = 0

    init {
        moveControl = FlightMoveControl(this, 10, true)
        airStrafingSpeed = 0.15f
        setPathfindingPenalty(PathNodeType.WATER, -1.0f)
        setPathfindingPenalty(PathNodeType.LAVA, -1.0f)
    }

    override fun initGoals() {
        goalSelector.add(0, object : MeleeAttackGoal(this, 1.1, false) {
            override fun canStart() = super.canStart() && this@FlyEntity.lifetime >= 3

            override fun getSquaredMaxAttackDistance(entity: LivingEntity) = 0.7
        })
        goalSelector.add(1, FollowOwnerGoal(this, 1.0, 1.0f, 0.0f, false))
        goalSelector.add(2, FlyWanderGoal(this))
        goalSelector.add(3, LookAroundGoal(this))
        targetSelector.add(1, object : AttackWithOwnerGoal(this) {
            override fun canStart() = super.canStart() && this@FlyEntity.lifetime >= 3
        })
        targetSelector.add(2, object : TrackOwnerAttackerGoal(this) {
            override fun canStart() = super.canStart() && this@FlyEntity.lifetime >= 3
        })
        targetSelector.add(3, object : TrackOwnerTargetGoal(this) {
            override fun canStart() = super.canStart() && this@FlyEntity.lifetime >= 3
        })
        targetSelector.add(4, RevengeGoal(this))
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putInt("Lifetime", lifetime)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        lifetime = nbt.getIntOrNull("Lifetime") ?: 0
    }

    override fun getBaseMovementSpeedMultiplier() = 1.0f

    override fun createNavigation(world: World): EntityNavigation {
        val birdNavigation = BirdNavigation(this, world)
        birdNavigation.setCanPathThroughDoors(false)
        birdNavigation.setCanSwim(false)
        birdNavigation.setCanEnterOpenDoors(true)
        return birdNavigation
    }

    override fun canTarget(target: LivingEntity): Boolean {
        return super.canTarget(target) && target.uuid != ownerUuid
    }

    override fun canTakeDamage() = false

    override fun hasWings() = true

    override fun handleFallDamage(fallDistance: Float, damageMultiplier: Float, damageSource: DamageSource) = false

    override fun fall(heightDifference: Double, onGround: Boolean, landedState: BlockState, landedPosition: BlockPos) = Unit

    override fun tick() {
        if (isTouchingWaterOrRain) damage(DamageSource.DROWN, Float.MAX_VALUE)
        super.tick()
        if (!world.isClient) {
            if (lifetime++ >= 600 && random.nextInt(10) == 0) damage(DamageSource.STARVE, Float.MAX_VALUE)
        }
    }

    override fun tryAttack(target: Entity): Boolean {
        val kbResistance = (target as? LivingEntity)?.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
        if (kbResistance?.getModifier(knockback_resistance_modifier_uuid) == null) {
            kbResistance?.addTemporaryModifier(EntityAttributeModifier(knockback_resistance_modifier_uuid, "Fly knockback resistance", 1.0, EntityAttributeModifier.Operation.ADDITION))
        }
        val ret = super.tryAttack(target)
        kbResistance?.removeModifier(knockback_resistance_modifier_uuid)
        damage(DamageSource.WITHER, Float.MAX_VALUE)
        if (ret) {
            (target as? LivingEntity)?.hurtTime = 1
            target.timeUntilRegen = 1
            val owner = owner
            if (owner is PlayerEntity) {
                (target as? LivingEntity)?.setAttacking(owner)
                (target as? LivingEntity)?.attacker = owner
            }
        }
        return ret
    }

    override fun damage(source: DamageSource, amount: Float): Boolean {
        val attacker = (source as? EntityDamageSource)?.source
        var new = amount
        if (attacker != null) {
            if (attacker.uuid == ownerUuid) return false
            new = Float.MAX_VALUE
        }
        if (new == Float.MAX_VALUE) replaceVelocity(y = -1.0)
        return super.damage(source, new)
    }

    override fun getOwner() = (world as? ServerWorld)?.getEntity(ownerUuid) as? LivingEntity ?: super.getOwner()

    override fun createChild(world: ServerWorld, entity: PassiveEntity) = null

    override fun isBreedingItem(stack: ItemStack) = false

    override fun canBeLeashedBy(player: PlayerEntity) = false

    override fun canImmediatelyDespawn(distanceSquared: Double) = true

    override fun isPushable() = false

    override fun pushAway(entity: Entity) = Unit

    override fun shouldDropXp() = owner !is PlayerEntity

    override fun getXpToDrop() = 1

    override fun lcc_raycastIgnore(caster: Entity): Boolean {
        if (caster.uuid == ownerUuid) return true
        return super.lcc_raycastIgnore(caster)
    }

    override fun handleAttack(attacker: Entity): Boolean {
        if (attacker.uuid == ownerUuid) return true
        return super.handleAttack(attacker)
    }

    override fun isInvulnerableTo(source: DamageSource): Boolean {
        val attacker = (source as? EntityDamageSource)?.source
        if (attacker != null && attacker.uuid == ownerUuid) return true
        return super.isInvulnerableTo(source)
    }

    override fun getPathfindingFavor(pos: BlockPos?, world: WorldView): Float {
        return if (world.getBlockState(pos).isAir) 10.0f else 0.0f
    }

    override fun takeKnockback(strength: Double, x: Double, z: Double) = Unit

    override fun playStepSound(pos: BlockPos, state: BlockState) = Unit

    override fun getAmbientSound() = LCCSounds.consumer_ambient

    override fun getHurtSound(source: DamageSource) = LCCSounds.consumer_hurt

    override fun getDeathSound() = LCCSounds.consumer_death

    override fun getGroup() = EntityGroup.ARTHROPOD

    override fun onDeath(source: DamageSource) {
        val uuid = ownerUuid
        ownerUuid = null
        super.onDeath(source)
        ownerUuid = uuid
    }

    class FlyWanderGoal(private val fly: FlyEntity) : Goal() {

        init {
            setControls(EnumSet.of(Control.MOVE));
        }

        override fun canStart(): Boolean {
            return fly.navigation.isIdle
        }

        override fun shouldContinue(): Boolean {
            return fly.navigation.isFollowingPath
        }

        override fun start() {
            val vec3d = getRandomLocation()
            if (vec3d != null) {
                fly.navigation.startMovingAlong(fly.navigation.findPathTo(BlockPos(vec3d), 1), 1.0)
            }
        }

        private fun getRandomLocation(): Vec3d? {
            val vec = fly.getRotationVec(0.0f)
            val vec2 = AboveGroundTargeting.find(fly, 16, 10, vec.x, vec.z, 3.14f, 4, 2)
            return vec2 ?: NoPenaltySolidTargeting.find(fly, 8, 4, -2, vec.x, vec.z, 1.5707963705062866)
        }

    }

    companion object {

        val knockback_resistance_modifier_uuid = UUID.fromString("4c626433-490e-4f15-b358-5f09a74e1854")

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0).add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
        }

    }

}