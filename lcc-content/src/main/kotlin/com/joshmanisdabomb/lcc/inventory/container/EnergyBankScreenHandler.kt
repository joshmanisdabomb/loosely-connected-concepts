package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.inventory.PredicatedSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate

class EnergyBankScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: LCCInventory, properties: PropertyDelegate) : BatteryScreenHandler(LCCScreenHandlers.energy_bank, syncId, playerInventory, inventory, properties) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, LCCInventory(LCCBlocks.energy_bank.slotCount).apply {
        addSegment("input", LCCBlocks.energy_bank.inputs)
        addSegment("output", LCCBlocks.energy_bank.outputs)
    }, ArrayPropertyDelegate(3))

    override val block get() = LCCBlocks.energy_bank

    init {
        addSlots(inventory, 15, 37, 3, 1, ::addSlot, start = 0) { inv, index, x, y -> PredicatedSlot(inv, index, x, y, StackEnergyHandler.Companion::containsPower) }
        addSlots(inventory, 108, 37, 3, 1, ::addSlot, start = 3) { inv, index, x, y -> PredicatedSlot(inv, index, x, y, StackEnergyHandler.Companion::containsPower) }

        addPlayerSlots(playerInventory, 8, 68, ::addSlot)
    }

}
