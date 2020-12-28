package com.joshmanisdabomb.lcc.item

import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack

interface LCCExtendedItem {

    @JvmDefault
    fun lcc_onEntitySwing(stack: ItemStack, entity: LivingEntity) = false

}