package com.joshmanisdabomb.lcc.abstracts

import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.EntityDamageSource
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.Tag
import kotlin.math.pow

enum class ToolEffectivity(val effective: Tag<Block>, val required: Tag<Block>, val equipment: Tag<Item>) {

    WASTELAND(LCCTags.wasteland_effective, LCCTags.wasteland_required, LCCTags.wasteland_equipment);

    fun isTool(stack: ItemStack, param: BlockState) = (stack.item as? LCCContentItemTrait)?.lcc_content_isTool(stack, param, this) == true
    fun isWeapon(stack: ItemStack, param: Entity) = (stack.item as? LCCContentItemTrait)?.lcc_content_isWeapon(stack, param, this) == true

    fun isRequired(state: BlockState, param: ItemStack) = (state.block as? LCCContentBlockTrait)?.lcc_content_isToolRequired(state, param, this) == true
    fun isEffective(state: BlockState, param: ItemStack) = (state.block as? LCCContentBlockTrait)?.lcc_content_isToolEffective(state, param, this) == true && (state.block as? LCCContentBlockTrait)?.lcc_content_isToolRequired(state, param, this) != true

    fun isToolInsufficient(state: BlockState, stack: ItemStack) = !isTool(stack, state) && isRequired(state, stack)

    fun reduceDamage(source: DamageSource, amount: Float) = Companion.reduceDamage(source, amount, this)

    companion object {
        fun reduceDamage(source: DamageSource, amount: Float, vararg effectivities: ToolEffectivity): Float {
            if (source::class == EntityDamageSource::class && (source.name == "player" || source.name == "mob")) {
                val entity = source.source as? LivingEntity
                val stack = entity?.mainHandStack
                val item = stack?.item as? LCCContentItemTrait ?: return amount.plus(1).pow(0.75f).minus(1).times(0.8f)
                if (effectivities.any { item.lcc_content_isWeapon(stack, entity, it) }) {
                    return amount
                } else {
                    return amount.plus(1).pow(0.75f).minus(1).times(0.8f)
                }
            } else {
                return amount.times(0.75f)
            }
        }
    }

}