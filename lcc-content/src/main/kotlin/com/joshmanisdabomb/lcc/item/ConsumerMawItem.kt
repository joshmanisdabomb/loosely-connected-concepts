package com.joshmanisdabomb.lcc.item

import com.google.common.collect.ImmutableMultimap
import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.entity.ConsumerTongueEntity
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait
import com.joshmanisdabomb.lcc.trait.LCCItemTrait
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class ConsumerMawItem(settings: Settings) : Item(settings), LCCItemTrait, LCCContentItemTrait {

    val modifiers = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 8.0, EntityAttributeModifier.Operation.ADDITION))
        .put(EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -3.55, EntityAttributeModifier.Operation.ADDITION))
        .apply(ToolEffectivity.WASTELAND::addToolModifiers)
        .build()

    override fun lcc_onEntitySwing(stack: ItemStack, entity: LivingEntity): Boolean {
        entity.playSound(LCCSounds.consumer_attack, 1.0f, entity.random.nextFloat().times(0.2f).plus(0.9f))
        return false
    }

    override fun lcc_content_isEffectiveWeapon(stack: ItemStack, entity: Entity, effectivity: ToolEffectivity) = effectivity == ToolEffectivity.WASTELAND

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity) = !miner.isCreative

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        val entity = ConsumerTongueEntity(world, user)
        val x = -MathHelper.sin(user.yaw * (Math.PI.toFloat() / 180)) * MathHelper.cos(user.pitch * (Math.PI.toFloat() / 180))
        val y = -MathHelper.sin(user.pitch * (Math.PI.toFloat() / 180))
        val z = MathHelper.cos(user.yaw * (Math.PI.toFloat() / 180)) * MathHelper.cos(user.pitch * (Math.PI.toFloat() / 180))
        entity.setVelocity(x.toDouble(), y.toDouble(), z.toDouble(), ConsumerTongueEntity.tongueSpeed, 0.8f)
        val h = entity.velocity.normalize().multiply(0.01)
        entity.setPosition(user.x + h.x, entity.getTargetY()!! + h.y, user.z + h.z)
        user.playSound(LCCSounds.consumer_tongue_shoot, 2.5f, user.random.nextFloat().times(0.2f).plus(0.9f))
        world.spawnEntity(entity)
        user.itemCooldownManager.set(this, 2)
        return TypedActionResult.consume(stack)
    }

    override fun canRepair(stack: ItemStack, ingredient: ItemStack) = ingredient.isOf(Items.CHARCOAL) || super.canRepair(stack, ingredient)

}
