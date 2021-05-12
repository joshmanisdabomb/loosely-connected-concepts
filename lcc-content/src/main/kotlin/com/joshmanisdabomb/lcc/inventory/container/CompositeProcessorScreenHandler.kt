package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.lib.inventory.PredicatedSlot
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate

class CompositeProcessorScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: RefiningInventory, properties: PropertyDelegate) : RefiningScreenHandler(LCCScreenHandlers.composite_processor, syncId, playerInventory, inventory, properties) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, RefiningInventory(LCCBlocks.composite_processor), ArrayPropertyDelegate(LCCBlocks.composite_processor.propertyCount))

    override val block get() = LCCBlocks.composite_processor

    init {
        addSlots(inventory, 17, 17, 3, 3, ::addSlot, start = 0)
        addSlots(inventory, 107, 17, 3, 3, ::addSlot, start = 9, ::PredicatedSlot)
        addSlots(inventory, 35, 75, 3, 1, ::addSlot, start = 18) { inv, index, x, y -> PredicatedSlot(inv, index, x, y, StackEnergyHandler::containsPower) }

        addPlayerSlots(playerInventory, 8, 108, ::addSlot)
    }

}
