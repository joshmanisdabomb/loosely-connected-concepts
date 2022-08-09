package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.block.entity.PapercombBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCPointsOfInterest
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.sound.WaspSoundInstance
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.*
import net.minecraft.entity.ai.AboveGroundTargeting
import net.minecraft.entity.ai.NoPenaltySolidTargeting
import net.minecraft.entity.ai.NoWaterTargeting
import net.minecraft.entity.ai.control.FlightMoveControl
import net.minecraft.entity.ai.control.MoveControl
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.ai.pathing.BirdNavigation
import net.minecraft.entity.ai.pathing.EntityNavigation
import net.minecraft.entity.ai.pathing.Path
import net.minecraft.entity.ai.pathing.PathNodeType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.Angerable
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.Monster
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.BeeEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.fluid.Fluid
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket
import net.minecraft.recipe.Ingredient
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.TagKey
import net.minecraft.util.TimeHelper
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.Difficulty
import net.minecraft.world.World
import net.minecraft.world.WorldView
import net.minecraft.world.poi.PointOfInterestStorage
import java.util.*
import kotlin.math.PI

open class WaspEntity(entityType: EntityType<out WaspEntity>, world: World) : AnimalEntity(entityType, world), Monster, Angerable, Flutterer, LCCContentEntityTrait {

    private var target: UUID? = null

    private var ticksLeftToFindHive = 0

    var hiveLoc: BlockPos? = null
    val hive: PapercombBlockEntity? get() { return world.getBlockEntity(hiveLoc ?: return null) as? PapercombBlockEntity }

    var stingAnimation = 0f
    var lastStingAnimation = 0f

    private lateinit var moveToHive: WaspMoveToHiveGoal

    @Environment(EnvType.CLIENT)
    var sound: WaspSoundInstance? = null

    constructor(world: World) : this(LCCEntities.wasp, world)

    init {
        airStrafingSpeed = 0.09f
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
        goalSelector.add(0, WaspStingGoal(this, 1.1, false))
        goalSelector.add(1, WaspEnterHiveGoal(this))
        goalSelector.add(2, AnimalMateGoal(this, 1.0))
        goalSelector.add(3, TemptGoal(this, 1.1, Ingredient.ofItems(Items.SUGAR), false))
        goalSelector.add(4, FollowParentGoal(this, 1.1))
        goalSelector.add(4, WaspFindHiveGoal(this))
        moveToHive = WaspMoveToHiveGoal(this, 1.25)
        goalSelector.add(4, moveToHive)
        goalSelector.add(5, WaspWanderGoal(this))
        goalSelector.add(6, LookAtEntityGoal(this, LivingEntity::class.java, 8.0f))
        goalSelector.add(6, LookAroundGoal(this))
        goalSelector.add(7, SwimGoal(this))
        targetSelector.add(1, WaspRevengeGoal(this).setGroupRevenge())
        targetSelector.add(2, ActiveTargetGoal(this, LivingEntity::class.java, 100, false, true, this::aggression))
        targetSelector.add(3, ActiveTargetGoal(this, BeeEntity::class.java, 100, false, true) { it.pos.squaredDistanceTo(this.pos) < 4096 })
        targetSelector.add(4, UniversalAngerGoal(this, true))
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        if (hiveLoc != null) nbt.put("Hive", NbtHelper.fromBlockPos(hiveLoc))
        writeAngerToNbt(nbt)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        if (nbt.contains("Hive", NBT_COMPOUND)) hiveLoc = NbtHelper.toBlockPos(nbt.getCompound("Hive"))
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

    fun aggression(entity: LivingEntity): Boolean {
        if (entity is WaspEntity || entity is CreeperEntity) return false
        if (this.shouldAngerAt(entity)) return true
        if (entity.uuid == this.angryAt) return true

        val distsq = hiveLoc?.getSquaredDistance(entity.blockPos)
        if (distsq != null && distsq < 256 && random.nextDouble() <= 256.minus(distsq).div(128)) {
            return true
        }

        val distsq2 = entity.squaredDistanceTo(this)
        if (distsq2 < 100 && random.nextDouble() <= 100.minus(distsq2).div(100)) {
            return true
        }
        return false
    }

    override fun tryAttack(target: Entity): Boolean {
        val bl = target.damage(DamageSource.sting(this), getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE).toInt().toFloat())
        if (bl) {
            applyDamageEffects(this, target)
            if (target is LivingEntity) {
                target.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, when (world.difficulty) {
                    Difficulty.NORMAL -> 80
                    Difficulty.HARD -> 120
                    else -> 40
                }, 1))
            }
            takeKnockback(1.0, target.x - x, target.z - z)
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
        if (world.isClient) {
            val newSound = getLoopSound()
            if (sound?.id != newSound.id) {
                sound?.valid = false
                sound = WaspSoundInstance(newSound, this)
                MinecraftClient.getInstance().soundManager.playNextTick(sound)
            }
        }
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
            dataTracker.set(targetClose, this.angerTime >= 10000 && this.getTarget()?.squaredDistanceTo(this)?.compareTo(5.0) == -1)
            if (ticksLeftToFindHive > 0) ticksLeftToFindHive--
        }
    }

    override fun createNavigation(world: World): EntityNavigation {
        if (isBaby) return super.createNavigation(world)
        val birdNavigation = object : BirdNavigation(this, world) {

            override fun isValidPosition(pos: BlockPos): Boolean {
                return super.isValidPosition(pos)
                /*var flag = false
                for (i in 1..2) {
                    flag = flag || !this.world.getBlockState(pos.down(i)).isAir
                }
                if (!flag) return false

                var flag2 = false
                for (i in 0..2) {
                    flag2 = flag2 || !this.world.getFluidState(pos.down(i)).isEmpty
                }
                return !flag2*/
            }

        }
        birdNavigation.setCanPathThroughDoors(false)
        birdNavigation.setCanSwim(false)
        birdNavigation.setCanEnterOpenDoors(true)
        return birdNavigation
    }

    private fun startMovingTo(pos: BlockPos, speed: Double = 1.0) {
        val vec = Vec3d.ofBottomCenter(pos)
        var i = 0
        val j = vec.y.toInt() - blockPos.y
        if (j > 2) i = 4
        else if (j < -2) i = -4
        var k = 6
        var l = 8
        val m = blockPos.getManhattanDistance(pos)
        if (m < 15) {
            k = m / 2
            l = m / 2
        }
        val vec2 = NoWaterTargeting.find(this, k, l, i, vec, PI.div(10))
        if (vec2 != null) {
            navigation.setRangeMultiplier(0.5f)
            navigation.startMovingTo(vec2.x, vec2.y, vec2.z, speed)
        }
    }

    private fun startMovingToFar(pos: BlockPos, speed: Double = 1.0): Boolean {
        navigation.setRangeMultiplier(10.0f)
        navigation.startMovingTo(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), speed)
        return navigation.currentPath?.reachesTarget() == true
    }

    override fun isBreedingItem(stack: ItemStack) = stack.isOf(Items.SUGAR)

    override fun isInAir() = !this.onGround

    override fun swimUpward(fluid: TagKey<Fluid>) {
        velocity = velocity.add(0.0, 0.02, 0.0)
    }

    override fun hasWings(): Boolean = !isBaby && isInAir && age % MathHelper.ceil(2.4166098f) == 0

    override fun playStepSound(pos: BlockPos, state: BlockState) {
        if (onGround) {
            playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F)
        }
    }

    fun getLoopSound() = if (hasAngerTime()) LCCSounds.wasp_aggressive else LCCSounds.wasp_loop

    override fun getAmbientSound() = null

    override fun getHurtSound(source: DamageSource) = SoundEvents.ENTITY_BEE_HURT

    override fun getDeathSound() = SoundEvents.ENTITY_BEE_DEATH

    override fun getSoundVolume() = 0.4f

    override fun handleFallDamage(fallDistance: Float, damageMultiplier: Float, damageSource: DamageSource) = if (!isBaby) false else super.handleFallDamage(fallDistance, damageMultiplier, damageSource)

    override fun fall(heightDifference: Double, onGround: Boolean, landedState: BlockState, landedPosition: BlockPos) = if (!isBaby) Unit else super.fall(heightDifference, onGround, landedState, landedPosition)

    override fun damage(source: DamageSource, amount: Float): Boolean {
        if (source.isFire) return super.damage(source, amount.times(1.5f).coerceAtLeast(3.0f))
        return super.damage(source, ToolEffectivity.WASTELAND.reduceDamageTaken(this, source, amount))
    }

    override fun lcc_content_applyDamageThroughArmor(attacked: LivingEntity, after: Float, armor: Float, toughness: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original)
    }

    override fun lcc_content_applyDamageThroughProtection(attacked: LivingEntity, after: Float, protection: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original, 1f)
    }

    override fun getGroup() = EntityGroup.ARTHROPOD

    override fun onSpawnPacket(packet: EntitySpawnS2CPacket) {
        super.onSpawnPacket(packet)
        sound = WaspSoundInstance(getLoopSound(), this)
        MinecraftClient.getInstance().soundManager.playNextTick(sound)
    }

    companion object {

        val anger = DataTracker.registerData(WaspEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        val angerRange = TimeHelper.betweenSeconds(590, 600)

        val targetClose = DataTracker.registerData(WaspEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 17.0).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 33.0)
        }

        fun angerNearby(world: World, pos: BlockPos, aggressor: LivingEntity, range: Int): List<WaspEntity> {
            val list = world.getNonSpectatingEntities(WaspEntity::class.java, Box(pos).expand(range.toDouble()))
            if (list.isEmpty()) return emptyList()
            list.removeIf {
                if (it.getTarget() != null) return@removeIf true
                it.setTarget(aggressor)
                false
            }
            return list
        }

    }

    class WaspStingGoal(mob: WaspEntity, speed: Double, pauseWhenMobIdle: Boolean) : MeleeAttackGoal(mob, speed, pauseWhenMobIdle) {

        private val wasp = mob

        override fun canStart() = super.canStart()// && wasp.hasAngerTime()

        override fun shouldContinue() = super.shouldContinue()// && wasp.hasAngerTime()

        override fun getSquaredMaxAttackDistance(entity: LivingEntity) = super.getSquaredMaxAttackDistance(entity).div(1.5)

    }

    class WaspEnterHiveGoal(protected val wasp: WaspEntity) : Goal() {

        override fun canStart(): Boolean {
            if (wasp.angerTime >= 10000 || wasp.hiveLoc?.isWithinDistance(wasp.pos, 2.0) != true) return false
            if (wasp.hive?.isFull() == true) {
                wasp.hiveLoc = null
                return false
            }
            return wasp.hive?.canEnter(wasp) == true
        }

        override fun start() {
            wasp.hive?.enter(wasp)
        }

        override fun shouldContinue() = false

    }

    class WaspFindHiveGoal(protected val wasp: WaspEntity) : Goal() {

        override fun canStart() = wasp.angerTime < 10000 && wasp.ticksLeftToFindHive == 0 && wasp.hiveLoc == null

        override fun start() {
            wasp.ticksLeftToFindHive = 200
            val hives = find()
            if (hives.isNotEmpty()) {
                wasp.hiveLoc = hives.firstOrNull { !wasp.moveToHive.isPossibleHive(it) } ?: hives.firstOrNull().also { wasp.moveToHive.clearPossibleHives() }
            }
        }

        override fun shouldContinue() = false

        private fun find(): List<BlockPos> {
            val poi = (wasp.world as ServerWorld).pointOfInterestStorage
            return poi.getInCircle({ it.value() == LCCPointsOfInterest.papercomb }, wasp.blockPos, 60, PointOfInterestStorage.OccupationStatus.ANY)
                .map { it.pos }.filter { (wasp.world.getBlockEntity(it) as? PapercombBlockEntity)?.isFull() == false }.sorted(Comparator.comparingDouble { it.getSquaredDistance(wasp.blockPos) }).toList()
        }

    }

    class WaspMoveToHiveGoal(protected val wasp: WaspEntity, private val speed: Double) : Goal() {

        private val possibleHives = mutableListOf<BlockPos>()

        private var ticks = 0
        private var ticksUntilLost = 0
        private var path: Path? = null

        override fun canStart() = wasp.angerTime < 10000 && wasp.hiveLoc != null && !wasp.hasPositionTarget() && wasp.hive?.canEnter(wasp) == true && wasp.hive?.isClose(wasp) == false

        override fun start() {
            ticks = 0
            ticksUntilLost = 0
            super.start()
        }

        override fun tick() {
            val hiveLoc = wasp.hiveLoc ?: return
            ++ticks
            if (ticks > 600) {
                this.makeChosenHivePossibleHive()
            } else if (!wasp.navigation.isFollowingPath) {
                if (!wasp.blockPos.isWithinDistance(hiveLoc, 16.0)) {
                    if (!wasp.blockPos.isWithinDistance(wasp.hiveLoc, 160.0)) {
                        this.setLost()
                    } else {
                        wasp.startMovingTo(hiveLoc, speed)
                    }
                } else {
                    if (!wasp.startMovingToFar(hiveLoc, speed)) {
                        this.makeChosenHivePossibleHive()
                    } else if (this.path != null && wasp.navigation.currentPath?.equalsPath(this.path) == true) {
                        ++ticksUntilLost
                        if (ticksUntilLost > 60) {
                            this.setLost()
                            ticksUntilLost = 0
                        }
                    } else {
                        this.path = wasp.navigation.currentPath
                    }
                }
            }
        }

        override fun shouldContinue() = canStart()

        override fun stop() {
            ticks = 0
            ticksUntilLost = 0
            wasp.navigation.stop()
            wasp.navigation.resetRangeMultiplier()
        }

        private fun addPossibleHive(pos: BlockPos) {
            possibleHives.add(pos)
            while (possibleHives.size > 3) {
                possibleHives.removeAt(0)
            }
        }

        private fun makeChosenHivePossibleHive() {
            val hiveLoc = wasp.hiveLoc
            if (hiveLoc != null) this.addPossibleHive(hiveLoc)
            this.setLost()
        }

        private fun setLost() {
            wasp.hiveLoc = null
            wasp.ticksLeftToFindHive = 200
        }

        fun isPossibleHive(pos: BlockPos) = possibleHives.contains(pos)

        fun clearPossibleHives() = possibleHives.clear()

    }

    class WaspRevengeGoal(mob: WaspEntity) : RevengeGoal(mob) {

        private val wasp = mob

        override fun shouldContinue(): Boolean {
            return wasp.angerTime >= 10000 && super.shouldContinue()
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
            val vec: Vec3d
            if (wasp.hive != null && wasp.blockPos.isWithinDistance(wasp.hiveLoc, 22.0)) {
                vec = Vec3d.ofCenter(wasp.hiveLoc).subtract(wasp.pos).normalize()
            } else {
                vec = wasp.getRotationVec(0.0f)
            }
            val vec2 = AboveGroundTargeting.find(wasp, 8, 7, vec.x, vec.z, 1.5707964f, 3, 1)
            return vec2 ?: NoPenaltySolidTargeting.find(wasp, 8, 4, -2, vec.x, vec.z, 1.5707963705062866)
        }

    }

}