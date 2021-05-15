package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.block.BlockState
import net.minecraft.entity.*
import net.minecraft.entity.ai.AboveGroundTargeting
import net.minecraft.entity.ai.Durations
import net.minecraft.entity.ai.NoPenaltySolidTargeting
import net.minecraft.entity.ai.control.FlightMoveControl
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.ai.pathing.BirdNavigation
import net.minecraft.entity.ai.pathing.EntityNavigation
import net.minecraft.entity.ai.pathing.PathNodeType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.Angerable
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.Monster
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.recipe.Ingredient
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.Tag
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.Difficulty
import net.minecraft.world.World
import net.minecraft.world.WorldView
import java.util.*

open class WaspEntity(entityType: EntityType<out WaspEntity>, world: World) : AnimalEntity(entityType, world), Monster, Angerable, Flutterer {

    private var target: UUID? = null

    var stingAnimation = 0f
    var lastStingAnimation = 0f

    constructor(world: World) : this(LCCEntities.wasp, world)

    init {
        flyingSpeed = 0.09f
        setPathfindingPenalty(PathNodeType.WATER, -1.0f)
        setPathfindingPenalty(PathNodeType.LAVA, -1.0f)
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(anger, 0)
        dataTracker.startTracking(targetClose, false)
    }

    override fun getPathfindingFavor(pos: BlockPos, world: WorldView) = world.getBlockState(pos).isAir.transform(10f, 0f)

    override fun initGoals() {
        goalSelector.add(2, StingGoal(this, 1.4, false))
        goalSelector.add(4, AnimalMateGoal(this, 1.0))
        goalSelector.add(6, TemptGoal(this, 1.25, Ingredient.ofItems(Items.SUGAR), false))
        goalSelector.add(8, FollowParentGoal(this, 1.25))
        goalSelector.add(10, WaspWanderGoal(this))
        goalSelector.add(12, SwimGoal(this))
        goalSelector.add(14, LookAtEntityGoal(this, LivingEntity::class.java, 8.0f))
        goalSelector.add(14, LookAroundGoal(this))
        targetSelector.add(1, WaspRevengeGoal(this).setGroupRevenge())
        targetSelector.add(2, WaspFollowTargetGoal(this))
        targetSelector.add(3, UniversalAngerGoal(this, true))
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        writeAngerToNbt(nbt)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        readAngerFromNbt(world, nbt)
    }

    override fun createChild(world: ServerWorld, entity: PassiveEntity) = LCCEntities.wasp.create(world)

    override fun getAngerTime() = dataTracker.get(anger)

    override fun setAngerTime(ticks: Int) = dataTracker.set(anger, ticks)

    override fun chooseRandomAngerTime() {
        angerTime = angerRange.get(random)
    }

    override fun getAngryAt() = target

    override fun setAngryAt(uuid: UUID?) {
        target = uuid
    }

    override fun tryAttack(target: Entity): Boolean {
        val bl = target.damage(DamageSource.sting(this), getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE).toInt().toFloat())
        if (bl) {
            dealDamage(this, target)
            if (target is LivingEntity) {
                target.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, when (world.difficulty) {
                    Difficulty.NORMAL -> 80
                    Difficulty.HARD -> 120
                    else -> 40
                }, 1))
            }
            takeKnockback(1f, target.x - x, target.z - z)
            playSound(SoundEvents.ENTITY_BEE_STING, 1.0f, 1.0f)
        }

        return bl
    }

    override fun mobTick() {
        super.mobTick()
        if (!world.isClient) {
            tickAngerLogic(world as ServerWorld, false)
        }
    }

    override fun tick() {
        super.tick()
        this.lastStingAnimation = this.stingAnimation
        if (dataTracker.get(targetClose)) {
            this.stingAnimation = this.stingAnimation.plus(0.2F).coerceAtMost(1.0F)
        } else {
            this.stingAnimation = this.stingAnimation.minus(0.24F).coerceAtLeast(0.0F)
        }
    }

    override fun tickMovement() {
        if (isBaby && (moveControl is FlightMoveControl || navigation is BirdNavigation)) {
            moveControl = MoveControl(this)
            navigation = createNavigation(world)
        } else if (!isBaby && (moveControl !is FlightMoveControl || navigation !is BirdNavigation)) {
            moveControl = FlightMoveControl(this, 90, true)
            navigation = createNavigation(world)
        }
        super.tickMovement()
        if (!world.isClient) {
            dataTracker.set(targetClose, this.hasAngerTime() && this.getTarget()?.squaredDistanceTo(this)?.compareTo(5.0) == -1)
        }
    }

    override fun createNavigation(world: World): EntityNavigation {
        if (isBaby) return super.createNavigation(world)
        val birdNavigation = object : BirdNavigation(this, world) {

            override fun isValidPosition(pos: BlockPos): Boolean {
                var flag = false
                for (i in 1..2) {
                    flag = flag || !this.world.getBlockState(pos.down(i)).isAir
                }
                if (!flag) return false

                var flag2 = false
                for (i in 0..2) {
                    flag2 = flag2 || !this.world.getFluidState(pos.down(i)).isEmpty
                }
                return !flag2
            }

        }
        birdNavigation.setCanPathThroughDoors(false)
        birdNavigation.setCanSwim(false)
        birdNavigation.setCanEnterOpenDoors(true)
        return birdNavigation
    }

    override fun isBreedingItem(stack: ItemStack) = stack.isOf(Items.SUGAR)

    override fun isInAir() = !this.onGround

    override fun swimUpward(fluid: Tag<Fluid>) {
        velocity = velocity.add(0.0, 0.02, 0.0)
    }

    override fun hasWings(): Boolean = !isBaby && isInAir && age % MathHelper.ceil(2.4166098f) == 0

    override fun playStepSound(pos: BlockPos, state: BlockState) {
        if (onGround) {
            playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F)
        }
    }

    override fun getAmbientSound() = null

    override fun getHurtSound(source: DamageSource) = SoundEvents.ENTITY_BEE_HURT

    override fun getDeathSound() = SoundEvents.ENTITY_BEE_DEATH

    override fun getSoundVolume() = 0.4f

    override fun handleFallDamage(fallDistance: Float, damageMultiplier: Float, damageSource: DamageSource) = if (!isBaby) false else super.handleFallDamage(fallDistance, damageMultiplier, damageSource)

    override fun fall(heightDifference: Double, onGround: Boolean, landedState: BlockState, landedPosition: BlockPos) = if (!isBaby) Unit else super.fall(heightDifference, onGround, landedState, landedPosition)

    override fun getGroup() = EntityGroup.ARTHROPOD

    companion object {

        val anger = DataTracker.registerData(WaspEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        val angerRange = Durations.betweenSeconds(300, 600)

        val targetClose = DataTracker.registerData(WaspEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 28.0 /* TODO increase health for wasteland weapons */).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 96.0)
        }

    }

    class StingGoal(mob: WaspEntity, speed: Double, pauseWhenMobIdle: Boolean) : MeleeAttackGoal(mob, speed, pauseWhenMobIdle) {

        private val wasp = mob

        override fun canStart() = super.canStart() && wasp.hasAngerTime()

        override fun shouldContinue() = super.shouldContinue() && wasp.hasAngerTime()

        override fun getSquaredMaxAttackDistance(entity: LivingEntity) = super.getSquaredMaxAttackDistance(entity).div(1.5)

    }

    class WaspRevengeGoal(mob: WaspEntity) : RevengeGoal(mob) {

        private val wasp = mob

        override fun shouldContinue(): Boolean {
            return wasp.hasAngerTime() && super.shouldContinue()
        }

        override fun setMobEntityTarget(mob: MobEntity, target: LivingEntity?) {
            if (mob is WaspEntity && wasp.canSee(target)) mob.setTarget(target)
        }

    }

    class WaspWanderGoal(mob: WaspEntity) : Goal() {

        private val wasp = mob

        init {
            setControls(EnumSet.of(Control.MOVE));
        }

        override fun canStart(): Boolean {
            return wasp.navigation.isIdle && wasp.random.nextInt(10) == 0
        }

        override fun shouldContinue(): Boolean {
            return wasp.navigation.isFollowingPath
        }

        override fun start() {
            val vec3d = getRandomLocation()
            if (vec3d != null) {
                wasp.navigation.startMovingAlong(wasp.navigation.findPathTo(BlockPos(vec3d), 1), 1.0)
            }
        }

        private fun getRandomLocation(): Vec3d? {
            val vec3d3 = wasp.getRotationVec(0.0f)
            val vec3d4 = AboveGroundTargeting.find(wasp, 8, 7, vec3d3.x, vec3d3.z, 1.5707964f, 3, 1)
            return vec3d4 ?: NoPenaltySolidTargeting.find(wasp, 8, 4, -2, vec3d3.x, vec3d3.z, 1.5707963705062866)
        }

    }

    class WaspFollowTargetGoal(mob: WaspEntity) : FollowTargetGoal<LivingEntity>(mob, LivingEntity::class.java,100, true, false, mob::shouldAngerAt) {

        private val wasp = mob

        override fun shouldContinue(): Boolean {
            if (wasp.getTarget() != null) {
                return super.shouldContinue()
            } else {
                target = null
                return false
            }
        }

    }

}