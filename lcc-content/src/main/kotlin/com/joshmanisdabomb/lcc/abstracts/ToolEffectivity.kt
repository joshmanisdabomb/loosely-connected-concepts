package com.joshmanisdabomb.lcc.abstracts

import com.google.common.collect.ImmutableMultimap
import com.joshmanisdabomb.lcc.directory.LCCAttributes
import com.joshmanisdabomb.lcc.directory.tags.LCCBlockTags
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait
import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey
import net.minecraft.util.math.MathHelper
import java.util.*
import kotlin.math.pow

enum class ToolEffectivity(val effective: TagKey<Block>, val required: TagKey<Block>, val equipment: TagKey<Item>, val damage: EntityAttribute, val protection: EntityAttribute) {

    WASTELAND(LCCBlockTags.wasteland_effective, LCCBlockTags.wasteland_required, LCCItemTags.wasteland_equipment, LCCAttributes.wasteland_damage, LCCAttributes.wasteland_protection);

    fun isTool(stack: ItemStack, against: BlockState, vanilla: Boolean) = (stack.item as? LCCContentItemTrait)?.lcc_content_isEffectiveTool(stack, against, this, vanilla) == true

    fun isRequired(state: BlockState, param: ItemStack) = (state.block as? LCCContentBlockTrait)?.lcc_content_isToolRequired(state, param, this) == true
    fun isEffective(state: BlockState, param: ItemStack) = (state.block as? LCCContentBlockTrait)?.lcc_content_isToolEffective(state, param, this) == true && (state.block as? LCCContentBlockTrait)?.lcc_content_isToolRequired(state, param, this) != true

    fun reduceDamageTaken(recipient: LivingEntity, defense: Double, player: PlayerEntity, offense: Double, amount: Float, original: Float): Float {
        val reduction = amount.plus(1).pow(0.75f).minus(1).times(0.8f)
        val diff = amount - reduction
        val factor = MathHelper.clamp(1.0 + offense - defense, 0.0, 1.0).toFloat()
        return reduction + (diff * factor)
    }

    fun increaseDamageGiven(attacker: LivingEntity, offense: Double, attacked: LivingEntity, defense: Double, after: Float, original: Float, modifier: Float): Float {
        val increase = original.times(0.5f).times(modifier) + after.times(0.5f)
        val diff = increase - after
        val factor = MathHelper.clamp(offense - defense, 0.0, 1.0).toFloat()
        return after + (diff * factor)
    }

    fun addToolModifiers(builder: ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier>) {
        builder.put(damage, EntityAttributeModifier(damage_modifier, "Weapon modifier", 1.0, EntityAttributeModifier.Operation.ADDITION))
    }

    companion object {
        val damage_modifier = UUID.fromString("b4f79aa9-2232-49c1-88b8-0c7c3e5e5cbb")
        val protection_modifier = UUID.fromString("7f384f82-c39d-450c-a435-ac9dca8a46ba")
    }

}