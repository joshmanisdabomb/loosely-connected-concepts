package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCBiomes
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.ai.pathing.SpiderNavigation
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.*

class PsychoPigEntity(type: EntityType<out PsychoPigEntity>, world: World) : HostileEntity(type, world), LCCContentEntityTrait {

    var eyeLeft = false

    override fun initialize(world: ServerWorldAccess, difficulty: LocalDifficulty, spawnReason: SpawnReason, entityData: EntityData?, entityNbt: NbtCompound?): EntityData? {
        eyeLeft = random.nextBoolean()
        dataTracker.set(eye_left, eyeLeft)
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt)
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(eye_left, false)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        super.onTrackedDataSet(data)
        if (data == eye_left) {
            eyeLeft = dataTracker.get(eye_left)
        }
    }

    override fun initGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(3, PsychoPigMeleeAttackGoal())
        goalSelector.add(5, WanderAroundFarGoal(this, 0.7))
        goalSelector.add(6, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(6, LookAroundGoal(this))
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(2, ActiveTargetGoal(this, PlayerEntity::class.java, true))
        targetSelector.add(2, RevengeGoal(this))
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason): Boolean {
        return true
    }

    override fun getPathfindingFavor(pos: BlockPos, world: WorldView): Float {
        if (LCCBiomes.getOrNull(world.getBiome(pos))?.tags?.contains("wasteland") != true) return -100.0f
        return 0.0f
    }

    override fun createNavigation(world: World) = SpiderNavigation(this, world)

    override fun canSee(entity: Entity) = entity == target || super.canSee(entity)

    override fun tick() {
        super.tick()
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putBoolean("LeftEyed", this.eyeLeft)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        nbt.getBoolean("LeftEyed").also { eyeLeft = it; dataTracker.set(eye_left, it) }
    }

    override fun getAmbientSound() = isAttacking.transform(LCCSounds.consumer_ambient, SoundEvents.ENTITY_PIG_AMBIENT)

    override fun getHurtSound(source: DamageSource) = isAttacking.transform(LCCSounds.consumer_hurt, SoundEvents.ENTITY_PIG_HURT)

    override fun getDeathSound() = isAttacking.transform(LCCSounds.consumer_death, SoundEvents.ENTITY_PIG_DEATH)

    override fun damage(source: DamageSource, amount: Float) = super.damage(source, ToolEffectivity.WASTELAND.reduceDamageTaken(this, source, amount))

    override fun lcc_content_applyDamageThroughArmor(attacked: LivingEntity, after: Float, armor: Float, toughness: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original)
    }

    override fun lcc_content_applyDamageThroughProtection(attacked: LivingEntity, after: Float, protection: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original, 1f)
    }

    companion object {
        val eye_left = DataTracker.registerData(PsychoPigEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_ARMOR, 20.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 96.0)
        }
    }

    inner class PsychoPigMeleeAttackGoal : MeleeAttackGoal(this, 2.0, false) {

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

}