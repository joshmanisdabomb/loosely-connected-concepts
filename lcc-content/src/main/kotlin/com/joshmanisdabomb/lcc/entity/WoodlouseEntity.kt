package com.joshmanisdabomb.lcc.entity

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCEntities
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.directory.tags.LCCBiomeTags
import com.joshmanisdabomb.lcc.entity.ai.EscapeSunlightAlwaysGoal
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityGroup
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldView

class WoodlouseEntity(type: EntityType<out WoodlouseEntity>, world: World) : AnimalEntity(type, world), LCCContentEntityTrait {

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(1, AvoidSunlightGoal(this))
        goalSelector.add(2, EscapeDangerGoal(this, 2.0))
        goalSelector.add(3, EscapeSunlightAlwaysGoal(this, 1.0))
        goalSelector.add(4, AnimalMateGoal(this, 1.0))
        goalSelector.add(5, TemptGoal(this, 1.2, Ingredient.ofItems(Items.APPLE), false))
        goalSelector.add(6, FollowParentGoal(this, 1.1))
        goalSelector.add(7, WanderAroundFarGoal(this, 1.0))
        goalSelector.add(8, LookAtEntityGoal(this, PlayerEntity::class.java, 6.0f))
        goalSelector.add(9, LookAroundGoal(this))
    }

    override fun getPathfindingFavor(pos: BlockPos, world: WorldView): Float {
        if (!world.getBiome(pos).isIn(LCCBiomeTags.wasteland)) return 0.5f - world.getBrightness(pos)
        return 100.0f
    }

    override fun createChild(world: ServerWorld, entity: PassiveEntity) = LCCEntities.woodlouse.create(world)

    override fun isBreedingItem(stack: ItemStack) = stack.isOf(Items.APPLE)

    override fun isClimbing() = !world.isClient && horizontalCollision

    override fun getAmbientSound() = LCCSounds.consumer_ambient

    override fun getHurtSound(source: DamageSource) = LCCSounds.consumer_hurt

    override fun getDeathSound() = LCCSounds.consumer_death

    override fun playStepSound(pos: BlockPos, state: BlockState) {
        playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15f, 1.0f)
    }

    override fun damage(source: DamageSource, amount: Float): Boolean {
        return super.damage(source, ToolEffectivity.WASTELAND.reduceDamageTaken(this, source, amount))
    }

    override fun lcc_content_applyDamageThroughArmor(attacked: LivingEntity, after: Float, armor: Float, toughness: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original)
    }

    override fun lcc_content_applyDamageThroughProtection(attacked: LivingEntity, after: Float, protection: Float, original: Float): Float {
        return ToolEffectivity.WASTELAND.increaseDamageGiven(this, attacked, after, original, 1f)
    }

    override fun getGroup() = EntityGroup.ARTHROPOD

    companion object {

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0).add(EntityAttributes.GENERIC_ARMOR, 10.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
        }

    }

}
