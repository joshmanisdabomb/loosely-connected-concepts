package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import com.joshmanisdabomb.lcc.inventory.PredicatedSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate

class CoalFiredGeneratorScreenHandler(syncId: Int, playerInventory: PlayerInventory, inventory: DefaultInventory, properties: PropertyDelegate) : FiredGeneratorScreenHandler(LCCScreenHandlers.coal_generator, syncId, playerInventory, inventory, properties) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, DefaultInventory(LCCBlocks.coal_generator.slots), ArrayPropertyDelegate(6))

    override val block get() = LCCBlocks.coal_generator

    init {
        addSlots(inventory, 42, 20, 3, 1, ::addSlot, start = 0) { inv, index, x, y -> PredicatedSlot(inv, index, x, y) { LCCBlocks.coal_generator.getBurnTime(it) != null } }

        addPlayerSlots(playerInventory, 8, 51, ::addSlot)
    }

}
