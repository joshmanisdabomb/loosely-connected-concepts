package com.joshmanisdabomb.lcc.trait;

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack

interface LCCContentItemTrait {

    @JvmDefault
    fun lcc_content_isTool(stack: ItemStack, state: BlockState, effectivity: ToolEffectivity) = stack.isIn(effectivity.equipment)

    @JvmDefault
    fun lcc_content_isWeapon(stack: ItemStack, entity: Entity, effectivity: ToolEffectivity) = stack.isIn(effectivity.equipment)

}