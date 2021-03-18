package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.PredicatedSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler

class NuclearFiredGeneratorScreenHandler(syncId: Int, protected val playerInventory: PlayerInventory, val inventory: LCCInventory, val properties: PropertyDelegate) : ScreenHandler(LCCScreenHandlers.nuclear_generator, syncId) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, LCCInventory(4), ArrayPropertyDelegate(1))

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, 4)
        checkDataCount(properties, 1)

        addSlot(PredicatedSlot(inventory, 0, 12, 38) { it.isOf(Items.TNT) })
        addSlot(PredicatedSlot(inventory, 1, 42, 38) { it.isOf(LCCItems.enriched_uranium_nugget) })
        addSlot(PredicatedSlot(inventory, 2, 72, 38) { it.isOf(LCCItems.nuclear_fuel) })
        addSlot(PredicatedSlot(inventory, 3, 72, 59) { it.isOf(Items.ICE) })

        addPlayerSlots(playerInventory, 8, 90, ::addSlot)

        inventory.addListener(listener)
        inventory.onOpen(playerInventory.player)

        addProperties(properties)
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.onClose(player)
        inventory.removeListener(listener)
    }

    override fun onContentChanged(inventory: Inventory) {
        super.onContentChanged(inventory)
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
        return newStack
    }

}