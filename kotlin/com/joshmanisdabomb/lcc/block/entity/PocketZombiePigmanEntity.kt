package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.entity.ai.ClassicMeleeAttackGoal
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
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
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.World

class PocketZombiePigmanEntity(entityType: EntityType<out PocketZombiePigmanEntity>, world: World) : ZombieEntity(entityType, world) {

    constructor(world: World) : this(LCCEntities.pocket_zombie_pigman, world)

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
        targetSelector.add(2, FollowTargetGoal(this, PlayerEntity::class.java, true))
    }

    override fun initEquipment(difficulty: LocalDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, ItemStack(Items.GOLDEN_SWORD))
    }

    override fun getSkull() = ItemStack.EMPTY

    override fun isBaby() = false

    override fun setBaby(baby: Boolean) = super.setBaby(false)

    override fun canBreakDoors() = false

    override fun canConvertInWater() = false

    override fun getAmbientSound() = LCCSounds.pocket_zombie_pigman_ambient

    override fun getHurtSound(source: DamageSource) = LCCSounds.pocket_zombie_pigman_hurt

    override fun getDeathSound() = LCCSounds.pocket_zombie_pigman_death

    companion object {

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0)
        }

    }

}