package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.inventory.PredicatedSlot
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate

class RefinerScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: RefiningInventory, properties: PropertyDelegate) : RefiningScreenHandler(LCCScreenHandlers.refiner, syncId, playerInventory, inventory, properties) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, RefiningInventory(LCCBlocks.refiner), ArrayPropertyDelegate(LCCBlocks.refiner.propertyCount))

    override val block get() = LCCBlocks.refiner

    init {
        addSlots(inventory, 17, 17, 3, 2, ::addSlot, start = 0)
        addSlots(inventory, 107, 17, 3, 2, ::addSlot, start = 6, ::PredicatedSlot)
        addSlots(inventory, 35, 57, 3, 1, ::addSlot, start = 12) { inv, index, x, y -> PredicatedSlot(inv, index, x, y, StackEnergyHandler::containsPower) }

        addPlayerSlots(playerInventory, 8, 90, ::addSlot)
    }

}
