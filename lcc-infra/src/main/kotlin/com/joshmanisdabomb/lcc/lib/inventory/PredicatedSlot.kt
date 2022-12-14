package com.joshmanisdabomb.lcc.lib.inventory

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

open class PredicatedSlot(inventory: Inventory, index: Int, x: Int, y: Int, val predicate: (stack: ItemStack) -> Boolean = { false }) : Slot(inventory, index, x, y) {

    override fun canInsert(stack: ItemStack) = predicate(stack)

}