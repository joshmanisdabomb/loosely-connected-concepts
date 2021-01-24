package com.joshmanisdabomb.lcc.energy.stack

import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import net.minecraft.item.ItemStack

interface StackEnergyHandler : EnergyHandler<StackEnergyContext> {

    companion object {
        fun containsPower(stack: ItemStack) = (stack.item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(stack)) ?: false
    }

}