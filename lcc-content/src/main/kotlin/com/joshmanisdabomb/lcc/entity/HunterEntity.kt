package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.directory.LCCAttributes
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.tags.LCCBiomeTags
import net.minecraft.entity.EntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.ZombieEntity
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.entity.passive.MerchantEntity
import net.minecraft.entity.passive.TurtleEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class HunterEntity(type: EntityType<out HunterEntity>, world: World) : ZombieEntity(type, world) {

    override fun initCustomGoals() {
        goalSelector.add(1, BreakDoorGoal(this) { true })
        goalSelector.add(2, ZombieAttackGoal(this, 1.0, false))
        goalSelector.add(6, MoveThroughVillageGoal(this, 1.0, false, 4) { canBreakDoors() })
        goalSelector.add(7, WanderAroundFarGoal(this, 1.0))
        targetSelector.add(1, RevengeGoal(this).setGroupRevenge())
        targetSelector.add(2, ActiveTargetGoal(this, PlayerEntity::class.java, false))
        targetSelector.add(3, ActiveTargetGoal(this, MerchantEntity::class.java, false))
        targetSelector.add(3, ActiveTargetGoal(this, IronGolemEntity::class.java, false))
        targetSelector.add(5, ActiveTargetGoal(this, TurtleEntity::class.java, 10, false, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER))
    }

    override fun canSpawn(world: WorldAccess, spawnReason: SpawnReason) = true

    override fun getPathfindingFavor(pos: BlockPos, world: WorldView): Float {
        if (!world.getBiome(pos).isIn(LCCBiomeTags.wasteland)) return -100.0f
        return 0.0f
    }

    override fun getMovementSpeed(): Float {
        val speed = super.getMovementSpeed()
        if (target == null) return speed
        return speed.times(this.squaredDistanceTo(target).toFloat().div(1225.0f).coerceIn(0.0f, 1.0f).times(3.0f).plus(1.0f))
    }

    override fun setCanBreakDoors(canBreakDoors: Boolean) = Unit

    override fun burnsInDaylight() = false

    override fun getAmbientSound() = SoundEvents.ENTITY_HUSK_AMBIENT

    override fun getHurtSound(source: DamageSource) = SoundEvents.ENTITY_HUSK_HURT

    override fun getDeathSound() = SoundEvents.ENTITY_HUSK_DEATH

    override fun getStepSound() = SoundEvents.ENTITY_HUSK_STEP

    override fun canConvertInWater() = false

    override fun getSkull() = ItemStack.EMPTY

    override fun initEquipment(random: Random, localDifficulty: LocalDifficulty) {
        repeat(3) {
            super.initEquipment(random, localDifficulty)
        }
        for (slot in EquipmentSlot.values()) {
            val stack = this.getEquippedStack(slot)
            if (!stack.isEmpty) {
                val item = when (stack.item) {
                    Items.IRON_SWORD -> LCCItems.rusty_iron_sword
                    Items.IRON_SHOVEL -> LCCItems.rusty_iron_shovel
                    Items.LEATHER_HELMET, Items.GOLDEN_HELMET -> LCCItems.woodlouse_helmet
                    Items.CHAINMAIL_HELMET, Items.IRON_HELMET -> LCCItems.rusty_iron_helmet
                    Items.DIAMOND_HELMET -> LCCItems.sapphire_helmet
                    Items.LEATHER_CHESTPLATE, Items.GOLDEN_CHESTPLATE -> LCCItems.woodlouse_chestplate
                    Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE -> LCCItems.rusty_iron_chestplate
                    Items.DIAMOND_CHESTPLATE -> LCCItems.sapphire_chestplate
                    Items.LEATHER_LEGGINGS, Items.GOLDEN_LEGGINGS -> LCCItems.woodlouse_leggings
                    Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS -> LCCItems.rusty_iron_leggings
                    Items.DIAMOND_LEGGINGS -> LCCItems.sapphire_leggings
                    Items.LEATHER_BOOTS, Items.GOLDEN_BOOTS -> LCCItems.woodlouse_boots
                    Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS -> LCCItems.rusty_iron_boots
                    Items.DIAMOND_BOOTS -> LCCItems.sapphire_boots
                    else -> continue
                }
                val new = ItemStack(item, stack.count)
                new.nbt = stack.nbt
                equipStack(slot, new)
            }
        }
    }

    override fun initAttributes() {
        this.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)?.baseValue = 0.0
    }

    companion object {

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(LCCAttributes.wasteland_damage, 1.0).add(LCCAttributes.wasteland_protection, 1.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
        }

    }

}
