package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.gui.screen.DriveScreen
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.inventory.PredicatedSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Items
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text

class DriveComputerModule : ComputerModule() {

    override val expectedInventorySize = 1

    override fun initScreenHandler(handler: ComputingScreenHandler, slotAdder: (slot: Slot) -> Unit, half: ComputingBlockEntity.ComputingHalf, inv: LCCInventory, playerInv: PlayerInventory) {
        slotAdder(object : PredicatedSlot(inv, 0, 80, 22, { it.isOf(Items.TNT) }) {
            override fun getMaxItemCount() = 1
        })

        handler.addPlayerSlots(playerInv, 8, 58, slotAdder)
    }

    override fun createScreen(handler: ComputingScreenHandler, playerInv: PlayerInventory, text: Text) = DriveScreen(handler, playerInv, text)

}