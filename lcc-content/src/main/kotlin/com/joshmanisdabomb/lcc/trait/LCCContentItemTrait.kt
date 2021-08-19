package com.joshmanisdabomb.lcc.trait;

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolItem

interface LCCContentItemTrait {

    @JvmDefault
    fun lcc_content_isEffectiveTool(stack: ItemStack, state: BlockState, effectivity: ToolEffectivity, vanilla: Boolean) = stack.isIn(effectivity.equipment) && (this is ToolItem) && vanilla

    @JvmDefault
    fun lcc_content_isEffectiveWeapon(stack: ItemStack, entity: Entity, effectivity: ToolEffectivity) = stack.isIn(effectivity.equipment) && (this is ToolItem)

    @JvmDefault
    fun lcc_content_isEffectiveArmor(stack: ItemStack, entity: Entity, effectivity: ToolEffectivity) = stack.isIn(effectivity.equipment) && (this is ArmorItem)

}