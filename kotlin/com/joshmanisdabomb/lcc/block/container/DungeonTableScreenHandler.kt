package com.joshmanisdabomb.lcc.block.container

import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler

class DungeonTableScreenHandler(syncId: Int, playerInventory: PlayerInventory, val inventory: Inventory) : ScreenHandler(LCCScreenHandlers.spawner_table, syncId) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, SimpleInventory(48))

    init {
        checkSize(inventory, 48)
        inventory.onOpen(playerInventory.player)

        addSlots(inventory, 44, 18, 6, 1, ::addSlot, start = 0)
        addSlots(inventory, 26, 36, 8, 1, ::addSlot, start = 6)
        addSlots(inventory, 8, 54, 10, 1, ::addSlot, start = 14)
        addSlots(inventory, 8, 72, 10, 1, ::addSlot, start = 24)
        addSlots(inventory, 26, 90, 8, 1, ::addSlot, start = 34)
        addSlots(inventory, 44, 108, 6, 1, ::addSlot, start = 42)

        addPlayerSlots(playerInventory, 48, 140, ::addSlot)
    }

    override fun canUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (index < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }
            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }

        return newStack!!
    }

}
