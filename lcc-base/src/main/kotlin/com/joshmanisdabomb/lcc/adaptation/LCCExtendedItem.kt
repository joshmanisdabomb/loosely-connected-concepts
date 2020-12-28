package com.joshmanisdabomb.lcc.adaptation

import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

interface LCCExtendedItem {

    @JvmDefault
    fun lcc_onEntitySwing(stack: ItemStack, entity: LivingEntity) = false

}