package com.joshmanisdabomb.lcc.inventory

import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

class DungeonTableInventory : DefaultInventory {

    override val inventory = DefaultedList.ofSize(48, ItemStack.EMPTY)
    override val listeners = mutableListOf<InventoryChangedListener>()

}
