package com.joshmanisdabomb.lcc.item

import com.google.common.collect.ImmutableMultimap
import com.google.common.collect.Multimap
import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class KnifeItem(settings: Settings) : Item(settings), LCCContentItemTrait {

    val modifiers = ImmutableMultimap.builder<EntityAttribute, EntityAttributeModifier>()
        .put(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 3.0, EntityAttributeModifier.Operation.ADDITION))
        .put(EntityAttributes.GENERIC_ATTACK_SPEED, EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", 1.0, EntityAttributeModifier.Operation.ADDITION))
        .build()

    override fun lcc_content_isEffectiveWeapon(stack: ItemStack, entity: Entity, effectivity: ToolEffectivity) = effectivity == ToolEffectivity.WASTELAND

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity) = !miner.isCreative

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<EntityAttribute, EntityAttributeModifier> {
        return when (slot) {
            EquipmentSlot.MAINHAND -> modifiers
            else -> super.getAttributeModifiers(slot)
        }
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        stack.damage(1, attacker) { it.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
        target.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, 100, 1))
        return true
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        if (state.isOf(Blocks.BAMBOO) || state.isOf(Blocks.BAMBOO_SAPLING)) return 999999.0f
        if (state.isOf(Blocks.COBWEB)) return 15.0f
        val material = state.material
        return if (material == Material.PLANT || material == Material.REPLACEABLE_PLANT || state.isIn(BlockTags.LEAVES) || material == Material.GOURD) 1.5f else 1.0f
    }

    override fun canRepair(stack: ItemStack, ingredient: ItemStack) = ingredient.isOf(LCCItems.iron_oxide) || super.canRepair(stack, ingredient)

}
