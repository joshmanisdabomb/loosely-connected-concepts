package com.joshmanisdabomb.lcc.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

interface DefaultInventory : Inventory {

    abstract val inventory: DefaultedList<ItemStack>
    abstract val listeners: MutableList<InventoryChangedListener>

    override fun clear() = inventory.clear()

    override fun size() = inventory.size

    override fun isEmpty() = inventory.all(ItemStack::isEmpty)

    override fun getStack(slot: Int) = inventory[slot]

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val result = Inventories.splitStack(inventory, slot, amount)
        if (!result.isEmpty) markDirty()
        return result
    }

    override fun removeStack(slot: Int) = Inventories.removeStack(inventory, slot)

    override fun setStack(slot: Int, stack: ItemStack) {
        inventory[slot] = stack
        stack.count = stack.count.coerceAtMost(maxCountPerStack)
        markDirty()
    }

    override fun canPlayerUse(player: PlayerEntity) = true

    fun addListener(listener: InventoryChangedListener) = listeners.add(listener)

    fun removeListener(listener: InventoryChangedListener) = listeners.remove(listener)

    override fun markDirty() {
        listeners.forEach { it.onInventoryChanged(this) }
    }

}