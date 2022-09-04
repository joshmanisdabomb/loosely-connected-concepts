package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCBiomes
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.getCompoundOrNull
import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.*
import net.minecraft.entity.passive.MerchantEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.village.TradeOffer
import net.minecraft.world.Heightmap
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import java.util.*

class TravellerEntity(entityType: EntityType<out TravellerEntity>, world: World) : MerchantEntity(entityType, world) {

    var destination: BlockPos? = null

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
        goalSelector.add(2, MoveTowardsDestinationGoal(this, 0.45))
        goalSelector.add(3, WanderAroundFarGoal(this, 0.35))
        goalSelector.add(4, LookAtEntityGoal(this, MobEntity::class.java, 8.0f))
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        destination = nbt.getCompoundOrNull("Destination")?.run(NbtHelper::toBlockPos)
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        if (destination != null) nbt.put("Destination", NbtHelper.fromBlockPos(destination))
    }

    override fun interactMob(player: PlayerEntity, hand: Hand): ActionResult {
        if (!this.getStackInHand(Hand.MAIN_HAND).isEmpty) return ActionResult.PASS

        val stack = player.getStackInHand(hand)
        if (stack.isOf(LCCBlocks.cracked_mud.asItem())) {
            this.equipStack(EquipmentSlot.MAINHAND, stack.split(1))
            if (!world.isClient) this.findBiome(LCCBiomes.wasteland)
            return ActionResult.success(world.isClient)
        }

        return super.interactMob(player, hand)
    }

    override fun tick() {
        super.tick()
        if (this.headRollingTimeLeft > 0) {
            this.headRollingTimeLeft--
            this.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY)
        }
    }

    fun findBiome(biome: Biome) {
        val sworld = world as? ServerWorld ?: return
        val pos = sworld.locateBiome({ it.key.orElseThrow() == BuiltinRegistries.BIOME.getKey(biome).orElseThrow() }, blockPos, 6400, 32, 64)?.first
        if (pos == null) {
            this.dropStack(this.getStackInHand(Hand.MAIN_HAND))
            return this.no()
        }
        destination = pos
        playSound(SoundEvents.ENTITY_WANDERING_TRADER_YES, this.soundVolume, this.soundPitch)
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

    class MoveTowardsDestinationGoal(private val traveller: TravellerEntity, private val speed: Double) : Goal() {

        init {
            setControls(EnumSet.of(Control.MOVE));
        }

        override fun canStart(): Boolean {
            return traveller.navigation.isIdle && traveller.destination != null
        }

        override fun shouldContinue(): Boolean {
            return !traveller.navigation.isIdle
        }

        override fun start() {
            val destination = traveller.destination ?: return
            val direction = Vec3d.ofBottomCenter(destination).subtract(traveller.pos).normalize()
            val to = BlockPos(direction.multiply(40.0).add(traveller.pos))
            val toWG = to.withY(traveller.world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, to.x, to.z))
            traveller.navigation.startMovingTo(toWG.x.plus(0.5), toWG.y.toDouble(), toWG.z.plus(0.5), speed)
        }

    }

}