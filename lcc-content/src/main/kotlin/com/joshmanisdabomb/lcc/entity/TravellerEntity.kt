package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCBiomes
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.getCompoundOrNull
import com.joshmanisdabomb.lcc.extensions.stack
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.ProjectileDamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.mob.*
import net.minecraft.entity.passive.MerchantEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FireworkRocketEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.FireworkRocketItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.util.ActionResult
import net.minecraft.util.DyeColor
import net.minecraft.util.Hand
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.village.TradeOffer
import net.minecraft.world.Heightmap
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeKeys
import java.util.*
import java.util.function.Predicate

class TravellerEntity(entityType: EntityType<out TravellerEntity>, world: World) : MerchantEntity(entityType, world) {

    var destination: BlockPos? = null
    var waiting: Boolean = false
    var completed: Boolean = false
    var completedTicks = 0

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(1, FleeEntityGoal(this, ZombieEntity::class.java, 8.0f, 0.5, 0.5))
        goalSelector.add(1, FleeEntityGoal(this, EvokerEntity::class.java, 12.0f, 0.5, 0.5))
        goalSelector.add(1, FleeEntityGoal(this, VindicatorEntity::class.java, 8.0f, 0.5, 0.5))
        goalSelector.add(1, FleeEntityGoal(this, VexEntity::class.java, 8.0f, 0.5, 0.5))
        goalSelector.add(1, FleeEntityGoal(this, PillagerEntity::class.java, 15.0f, 0.5, 0.5))
        goalSelector.add(1, FleeEntityGoal(this, IllusionerEntity::class.java, 12.0f, 0.5, 0.5))
        goalSelector.add(1, FleeEntityGoal(this, ZoglinEntity::class.java, 10.0f, 0.5, 0.5))
        goalSelector.add(1, EscapeDangerGoal(this, 0.5))
        goalSelector.add(2, WaitingGoal(this))
        goalSelector.add(3, CelebrateGoal(this))
        goalSelector.add(4, MoveTowardsDestinationGoal(this, 0.45))
        goalSelector.add(5, WanderAroundFarGoal(this, 0.35))
        goalSelector.add(6, LookAtEntityGoal(this, MobEntity::class.java, 8.0f))
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(Companion.waiting, false)
        dataTracker.startTracking(Companion.completed, false)
    }

    override fun onTrackedDataSet(data: TrackedData<*>) {
        super.onTrackedDataSet(data)
        if (data == Companion.waiting) waiting = dataTracker.get(Companion.waiting)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        destination = nbt.getCompoundOrNull("Destination")?.run(NbtHelper::toBlockPos)
        waiting = nbt.getBoolean("Waiting")
        completed = nbt.getBoolean("Completed")
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        if (destination != null) nbt.put("Destination", NbtHelper.fromBlockPos(destination))
        nbt.putBoolean("Waiting", waiting)
        nbt.putBoolean("Completed", completed)
    }

    override fun interactMob(player: PlayerEntity, hand: Hand): ActionResult {
        if (!this.getStackInHand(Hand.MAIN_HAND).isEmpty) {
            waiting = !waiting
            dataTracker.set(Companion.waiting, waiting)
            stopWalking()
            playSound(if (waiting) SoundEvents.ENTITY_WANDERING_TRADER_DISAPPEARED else SoundEvents.ENTITY_WANDERING_TRADER_REAPPEARED, this.soundVolume, this.soundPitch)
            return ActionResult.SUCCESS
        }

        val stack = player.getStackInHand(hand)
        if (stack.isIn(ItemTags.TERRACOTTA)) return this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.BADLANDS }
        if ((stack.item as? BlockItem)?.block?.defaultState?.isIn(BlockTags.CORAL_BLOCKS) == true) return this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.WARM_OCEAN }
        return when (stack.item) {
            LCCBlocks.cracked_mud.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BuiltinRegistries.BIOME.getKey(LCCBiomes.wasteland).orElseThrow() } }
            Blocks.BIRCH_SAPLING.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.BIRCH_FOREST } }
            Blocks.SPRUCE_SAPLING.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.TAIGA } }
            Blocks.JUNGLE_SAPLING.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.JUNGLE } }
            Blocks.ACACIA_SAPLING.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.SAVANNA } }
            Blocks.DARK_OAK_SAPLING.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.DARK_FOREST } }
            Blocks.MANGROVE_PROPAGULE.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.MANGROVE_SWAMP } }
            Blocks.LILY_PAD.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.SWAMP } }
            Blocks.CACTUS.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.DESERT } }
            Blocks.BROWN_MUSHROOM_BLOCK.asItem(), Blocks.RED_MUSHROOM_BLOCK.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.MUSHROOM_FIELDS } }
            Blocks.BLUE_ICE.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.FROZEN_OCEAN } }
            Blocks.BAMBOO.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.BAMBOO_JUNGLE } }
            Blocks.PODZOL.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.OLD_GROWTH_PINE_TAIGA || it.key.orElseThrow() == BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA } }
            Blocks.ALLIUM.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.FLOWER_FOREST } }
            Blocks.SUNFLOWER.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.SUNFLOWER_PLAINS } }
            Blocks.GRAVEL.asItem() -> { this.findBiome(stack) { it.key.orElseThrow() == BiomeKeys.WINDSWEPT_GRAVELLY_HILLS } }
            else -> super.interactMob(player, hand)
        }
    }

    override fun tick() {
        super.tick()
        if (this.headRollingTimeLeft > 0) {
            this.headRollingTimeLeft--
            this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY)
        }
        if (completed && completedTicks in 1..400) {
            completedTicks++
            if (random.nextInt(25) == 0) spawnFirework()
        } else if (!completed && destination?.isWithinDistance(pos, 24.0) == true) {
            completed = true
            dataTracker.set(Companion.completed, true)
            completedTicks = 1
        }
    }

    private fun spawnFirework() {
        val color = Util.getRandom(DyeColor.values(), random)
        val rocket = Items.FIREWORK_ROCKET.stack()
        val star = Items.FIREWORK_STAR.stack()
        val explosion = star.getOrCreateSubNbt("Explosion")
        explosion.putIntArray("Colors", intArrayOf(color.fireworkColor))
        explosion.putByte("Type", FireworkRocketItem.Type.BURST.id.toByte())
        val fireworks = rocket.getOrCreateSubNbt("Fireworks")
        fireworks.putByte("Flight", random.nextInt(3).toByte())
        val explosions = NbtList()
        explosions.add(explosion)
        fireworks.put("Explosions", explosions)
        val firework = FireworkRocketEntity(world, this, x, eyeY, z, rocket)
        world.spawnEntity(firework)
    }

    override fun damage(source: DamageSource, amount: Float): Boolean {
        if (source is ProjectileDamageSource && source.name == "fireworks") return false
        return super.damage(source, amount)
    }

    fun findBiome(stack: ItemStack, predicate: Predicate<RegistryEntry<Biome>>): ActionResult {
        this.equipStack(EquipmentSlot.MAINHAND, stack.split(1))
        if (!world.isClient) {
            val sworld = world as? ServerWorld ?: return ActionResult.success(world.isClient)
            val pos = sworld.locateBiome(predicate, blockPos, 6400, 32, 64)?.first
            if (pos == null) {
                this.dropStack(this.getStackInHand(Hand.MAIN_HAND))
                this.no()
                return ActionResult.success(world.isClient)
            }
            destination = pos
            playSound(SoundEvents.ENTITY_WANDERING_TRADER_YES, this.soundVolume, this.soundPitch)
        }
        return ActionResult.success(world.isClient)
    }

    fun no() {
        this.headRollingTimeLeft = 40
        if (!world.isClient()) playSound(SoundEvents.ENTITY_WANDERING_TRADER_NO, this.soundVolume, this.soundPitch)
    }

    override fun createChild(world: ServerWorld, entity: PassiveEntity): PassiveEntity? = null

    override fun isLeveledMerchant() = false

    override fun afterUsing(offer: TradeOffer) = Unit

    override fun fillRecipes() = Unit

    override fun canImmediatelyDespawn(distanceSquared: Double) = false

    override fun getAmbientSound() = SoundEvents.ENTITY_WANDERING_TRADER_AMBIENT

    override fun getHurtSound(source: DamageSource) = SoundEvents.ENTITY_WANDERING_TRADER_HURT

    override fun getDeathSound() = SoundEvents.ENTITY_WANDERING_TRADER_DEATH

    fun getSubdestination(): Vec3d? {
        val destination = destination ?: return null
        val direction = Vec3d.ofBottomCenter(destination).subtract(pos).normalize()
        val to = BlockPos(direction.multiply(40.0).add(pos))
        val toWG = to.withY(world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, to.x, to.z))
        return Vec3d.ofBottomCenter(toWG)
    }

    override fun stopRiding() {
        stopWalking()
        super.stopRiding()
    }

    private fun stopWalking() {
        navigation.stop()
        goalSelector.runningGoals.toList().forEach(PrioritizedGoal::stop)
    }

    class WaitingGoal(private val traveller: TravellerEntity) : Goal() {

        init {
            setControls(EnumSet.of(Control.MOVE));
        }

        override fun canStart(): Boolean {
            return traveller.navigation.isIdle && traveller.destination != null && traveller.waiting && !traveller.completed
        }

        override fun shouldContinue(): Boolean {
            return !traveller.navigation.isIdle && traveller.destination != null && traveller.waiting && !traveller.completed
        }

        override fun start() {

        }

    }

    class MoveTowardsDestinationGoal(private val traveller: TravellerEntity, private val speed: Double) : Goal() {

        init {
            setControls(EnumSet.of(Control.MOVE));
        }

        override fun canStart(): Boolean {
            return traveller.navigation.isIdle && traveller.destination != null && !traveller.waiting && !traveller.completed
        }

        override fun shouldContinue(): Boolean {
            return !traveller.navigation.isIdle && traveller.destination != null && !traveller.waiting && !traveller.completed
        }

        override fun start() {
            val sdestination = traveller.getSubdestination() ?: return
            traveller.navigation.startMovingTo(sdestination.x, sdestination.y, sdestination.z, speed)
        }

    }

    class CelebrateGoal(private val traveller: TravellerEntity) : Goal() {

        init {
            setControls(EnumSet.of(Control.MOVE, Control.JUMP));
        }

        override fun canStart(): Boolean {
            return traveller.navigation.isIdle && traveller.completed
        }

        override fun shouldContinue(): Boolean {
            return !traveller.navigation.isIdle && traveller.completed
        }

        override fun start() {
            traveller.jumpControl.setActive()
        }

    }

    companion object {
        val waiting = DataTracker.registerData(TravellerEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
        val completed = DataTracker.registerData(TravellerEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
    }

}