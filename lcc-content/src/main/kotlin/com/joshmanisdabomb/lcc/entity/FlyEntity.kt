package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.entity.ai.TrackOwnerTargetGoal
import com.joshmanisdabomb.lcc.extensions.getIntOrNull
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

class FlyEntity(type: EntityType<out FlyEntity>, world: World) : TameableEntity(type, world) {

    var lifetime = 0

    init {
        moveControl = FlightMoveControl(this, 10, true)
        airStrafingSpeed = 0.15f
        setPathfindingPenalty(PathNodeType.WATER, -1.0f)
        setPathfindingPenalty(PathNodeType.LAVA, -1.0f)
    }

    override fun initGoals() {
        goalSelector.add(0, object : MeleeAttackGoal(this, 1.1, false) {
            override fun getSquaredMaxAttackDistance(entity: LivingEntity) = 0.5
        })
        goalSelector.add(1, FollowOwnerGoal(this, 1.0, 30.0f, 0.0f, false))
        goalSelector.add(2, FlyWanderGoal(this))
        goalSelector.add(3, LookAroundGoal(this))
        targetSelector.add(1, TrackOwnerAttackerGoal(this))
        targetSelector.add(2, TrackOwnerTargetGoal(this))
        targetSelector.add(3, AttackWithOwnerGoal(this))
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
        if (isTouchingWaterOrRain) dropDead(DamageSource.DROWN)
        super.tick()
        if (lifetime++ >= 600) dropDead(DamageSource.STARVE)
    }

    override fun tryAttack(target: Entity): Boolean {
        val kbResistance = (target as? LivingEntity)?.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
        if (kbResistance?.getModifier(knockback_resistance_modifier_uuid) == null) {
            kbResistance?.addTemporaryModifier(EntityAttributeModifier(knockback_resistance_modifier_uuid, "Fly knockback resistance", 1.0, EntityAttributeModifier.Operation.ADDITION))
        }
        val ret = super.tryAttack(target)
        kbResistance?.removeModifier(knockback_resistance_modifier_uuid)
        dropDead(DamageSource.WITHER)
        if (ret) {
            (target as? LivingEntity)?.hurtTime = 1
            target.timeUntilRegen = 1
        }
        return ret
    }

    override fun getOwner() = (world as? ServerWorld)?.getEntity(ownerUuid) as? LivingEntity ?: super.getOwner()

    override fun createChild(world: ServerWorld, entity: PassiveEntity) = null

    override fun isBreedingItem(stack: ItemStack) = false

    override fun canBeLeashedBy(player: PlayerEntity) = false

    override fun canImmediatelyDespawn(distanceSquared: Double) = true

    override fun isPushable() = false

    override fun pushAway(entity: Entity) = Unit

    override fun getPathfindingFavor(pos: BlockPos?, world: WorldView): Float {
        return if (world.getBlockState(pos).isAir) 10.0f else 0.0f
    }

    override fun takeKnockback(strength: Double, x: Double, z: Double) = Unit

    override fun getAmbientSound() = LCCSounds.consumer_ambient

    override fun getHurtSound(source: DamageSource) = LCCSounds.consumer_hurt

    override fun getDeathSound() = LCCSounds.consumer_death

    override fun getGroup() = EntityGroup.ARTHROPOD

    private fun dropDead(source: DamageSource) {
        damage(source, Float.MAX_VALUE)
        velocity = velocity.add(0.0, -1.0, 0.0)
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
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 0.01).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0).add(EntityAttributes.GENERIC_FLYING_SPEED, 1.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
        }

    }

}