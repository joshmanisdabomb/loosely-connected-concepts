package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.utils.DefaultedDyeableItem
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.DyeableArmorItem
import net.minecraft.item.ItemStack

open class HazmatArmorItem(slot: EquipmentSlot, settings: Settings) : DyeableArmorItem(LCCArmorMaterials.HAZMAT, slot, settings), DefaultedDyeableItem {

    override fun defaultColor(stack: ItemStack) = 0x909090

}
