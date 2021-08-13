package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.EntityDamageSource
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.Tag
import kotlin.math.pow

enum class ToolEffectivity(val effective: Tag<Block>, val required: Tag<Block>, val equipment: Tag<Item>, val combat: Tag<EntityType<*>>, val resistant: Tag<EntityType<*>>) {

    WASTELAND(LCCTags.wasteland_effective, LCCTags.wasteland_required, LCCTags.wasteland_equipment, LCCTags.wasteland_combat, LCCTags.wasteland_resistant);

    fun isTool(stack: ItemStack, against: BlockState) = (stack.item as? LCCContentItemTrait)?.lcc_content_isEffectiveTool(stack, against, this) == true
    fun isWeapon(stack: ItemStack, against: Entity) = (stack.item as? LCCContentItemTrait)?.lcc_content_isEffectiveWeapon(stack, against, this) == true
    fun isArmor(stack: ItemStack, against: Entity) = (stack.item as? LCCContentItemTrait)?.lcc_content_isEffectiveArmor(stack, against, this) == true

    fun isRequired(state: BlockState, param: ItemStack) = (state.block as? LCCContentBlockTrait)?.lcc_content_isToolRequired(state, param, this) == true
    fun isEffective(state: BlockState, param: ItemStack) = (state.block as? LCCContentBlockTrait)?.lcc_content_isToolEffective(state, param, this) == true && (state.block as? LCCContentBlockTrait)?.lcc_content_isToolRequired(state, param, this) != true

    fun isToolInsufficient(state: BlockState, stack: ItemStack) = !isTool(stack, state) && isRequired(state, stack)
    fun isToolSufficient(state: BlockState, stack: ItemStack) = isTool(stack, state) && isRequired(state, stack)

    fun isResistant(check: Entity, against: LivingEntity) = (check as? LCCContentEntityTrait)?.lcc_content_isCombatResistant(against, this) == true
    fun isCombat(check: Entity, against: LivingEntity) = (check as? LCCContentEntityTrait)?.lcc_content_isCombatEffective(against, this) == true

    fun reduceDamageTaken(receiver: LivingEntity, source: DamageSource, amount: Float) = Companion.reduceDamageTaken(receiver, source, amount, this)
    fun increaseDamageGiven(attacker: LivingEntity, attacked: LivingEntity, after: Float, original: Float, modifier: Float = 1.5f) = Companion.increaseDamageGiven(attacker, attacked, after, original, modifier, this)

    companion object {
        fun reduceDamageTaken(receiver: LivingEntity, source: DamageSource, amount: Float, vararg effectivities: ToolEffectivity): Float {
            if (effectivities.isEmpty()) return amount
            if (source::class == EntityDamageSource::class && (source.name == "player" || source.name == "mob")) {
                val entity = source.source ?: return amount.plus(1).pow(0.75f).minus(1).times(0.8f)
                if (effectivities.any { it.isResistant(entity, receiver) || (it.isWeapon((entity as? LivingEntity)?.mainHandStack ?: ItemStack.EMPTY, entity)) }) {
                    return amount
                } else {
                    return amount.plus(1).pow(0.75f).minus(1).times(0.8f)
                }
            }
            return amount.times(0.75f)
        }

        fun increaseDamageGiven(attacker: LivingEntity, attacked: LivingEntity, after: Float, original: Float, modifier: Float, vararg effectivities: ToolEffectivity): Float {
            if (effectivities.isEmpty()) return after
            if (effectivities.any { it.isResistant(attacked, attacker) }) return after
            val valids = attacked.armorItems.count { a -> effectivities.any { it.isArmor(a, attacker) } }
            when (valids) {
                0 -> return (original.times(0.85f) + after.times(0.15f)).times(modifier)
                1 -> return (original.times(0.75f) + after.times(0.25f)).times(modifier.minus(1).times(0.75f).plus(1))
                2 -> return (original.times(0.65f) + after.times(0.35f)).times(modifier.minus(1).times(0.5f).plus(1))
                3 -> return (original.times(0.5f) + after.times(0.5f)).times(modifier.minus(1).times(0.25f).plus(1))
                else -> return after
            }
        }
    }

}