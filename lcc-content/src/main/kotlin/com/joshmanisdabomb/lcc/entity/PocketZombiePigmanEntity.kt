package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.block.entity.NetherReactorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.entity.ai.ClassicMeleeAttackGoal
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.ZombieEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtHelper
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.World

class PocketZombiePigmanEntity(entityType: EntityType<out PocketZombiePigmanEntity>, world: World) : ZombieEntity(entityType, world) {

    constructor(world: World) : this(LCCEntities.pocket_zombie_pigman, world)

    var reactorWorld: ServerWorld? = null
    var reactorPos: BlockPos? = null

    override fun initGoals() {
        goalSelector.add(8, LookAtEntityGoal(this, PlayerEntity::class.java, 8.0f))
        goalSelector.add(8, LookAroundGoal(this))
        initCustomGoals()
    }

    override fun initCustomGoals() {
        goalSelector.add(1, SwimGoal(this))
        goalSelector.add(2, ClassicMeleeAttackGoal(this, 1.1))
        goalSelector.add(7, WanderAroundFarGoal(this, 1.0))
        targetSelector.add(1, RevengeGoal(this, *arrayOfNulls(0)))
        targetSelector.add(2, ActiveTargetGoal(this, PlayerEntity::class.java, true))
    }

    override fun initEquipment(difficulty: LocalDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack(Items.GOLDEN_SWORD))
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        server?.also {
            if (nbt.contains("ReactorWorld", NBT_STRING)) reactorWorld = it.getWorld(RegistryKey.of(Registry.WORLD_KEY, Identifier(nbt.getString("ReactorWorld"))))
            if (nbt.contains("ReactorPos", NBT_COMPOUND)) reactorPos = NbtHelper.toBlockPos(nbt.getCompound("ReactorPos"))
        }
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        reactorWorld?.also { nbt.putString("ReactorWorld", it.registryKey.value.toString()) }
        reactorPos?.also { nbt.put("ReactorPos", NbtHelper.fromBlockPos(it)) }
    }

    override fun getSkull() = ItemStack.EMPTY

    override fun isBaby() = false

    override fun setBaby(baby: Boolean) = super.setBaby(false)

    override fun canBreakDoors() = false

    override fun canConvertInWater() = false

    override fun getAmbientSound() = LCCSounds.pocket_zombie_pigman_ambient

    override fun getHurtSound(source: DamageSource) = LCCSounds.pocket_zombie_pigman_hurt

    override fun getDeathSound() = LCCSounds.pocket_zombie_pigman_death

    override fun onDeath(source: DamageSource) {
        val rworld = reactorWorld ?: return super.onDeath(source)
        val rpos = reactorPos ?: return super.onDeath(source)
        val adversary = this.primeAdversary as? ServerPlayerEntity ?: return super.onDeath(source)
        if (scoreAmount >= 0) {
            (rworld.getBlockEntity(rpos) as? NetherReactorBlockEntity)?.pigmanKilled(this)
        }
        super.onDeath(source)

    }

    override fun cannotDespawn() = super.cannotDespawn() || (reactorWorld != null && reactorPos != null)

    companion object {

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0)
        }

    }

}