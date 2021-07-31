package com.joshmanisdabomb.lcc.trait;

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack

interface LCCContentItemTrait {

    @JvmDefault
    fun lcc_content_isTool(stack: ItemStack, state: BlockState, effectivity: ToolEffectivity) = stack.isIn(effectivity.equipment)

}