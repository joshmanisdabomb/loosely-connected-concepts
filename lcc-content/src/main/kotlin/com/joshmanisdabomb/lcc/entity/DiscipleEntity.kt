package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCBiomes
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.sqrt
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.RangedAttackMob
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class DiscipleEntity(entityType: EntityType<out DiscipleEntity>, world: World) : HostileEntity(entityType, world), RangedAttackMob {

    init {
        airStrafingSpeed = 0.14f
    }

    override fun initDataTracker() {
        super.initDataTracker()
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        super.onTrackedDataSet(data)
    }

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(2, ProjectileAttackGoal(this, 1.0, 60, 10.0f))
        goalSelector.add(3, WanderAroundFarGoal(this, 1.0))
        goalSelector.add(4, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(4, LookAroundGoal(this))
        targetSelector.add(1, ActiveTargetGoal(this, HostileEntity::class.java, 100, true, true, this::target))
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason): Boolean {
        return true
    }

    override fun getPathfindingFavor(pos: BlockPos, world: WorldView): Float {
        if (LCCBiomes.getOrNull(world.getBiome(pos))?.tags?.contains("wasteland") != true) return -100.0f
        return 0.0f
    }

    override fun attack(target: LivingEntity, pullProgress: Float) {

    }

    fun target(entity: LivingEntity): Boolean {
        if ((entity as? MobEntity)?.target is PlayerEntity) {
            return true
        }
        return false
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
    }

    override fun getJumpVelocity() = super.getJumpVelocity().times(2f)

    override fun tick() {
        super.tick()
        var vel = velocity
        if (!onGround) {
            target?.also {
                val x = it.x - this.x
                val z = it.z - this.z
                vel = vel.add(Vec3d(x, 0.0, z).normalize().multiply(0.07.times(squaredDistanceTo(it).div(2304.0))))
            }
            if (vel.y < 0.0) {
                vel = vel.multiply(1.0, 0.67, 1.0)
            }
            velocity = vel
            airStrafingSpeed = (0.14f - fallDistance.sqrt().times(0.05f)).coerceAtLeast(0f)
        }
    }

    override fun handleFallDamage(fallDistance: Float, damageMultiplier: Float, damageSource: DamageSource) = false

    override fun getAmbientSound() = LCCSounds.consumer_ambient

    override fun getHurtSound(source: DamageSource) = LCCSounds.consumer_hurt

    override fun getDeathSound() = LCCSounds.consumer_death

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
        }
    }

}