package com.joshmanisdabomb.lcc.energy.stack

import com.joshmanisdabomb.lcc.energy.base.EnergyContext
import net.minecraft.item.ItemStack

data class StackEnergyContext(val stack: ItemStack) : EnergyContext() {

    override fun defaultOther() = throw RuntimeException("Cannot create a default energy context for item stacks.")

}