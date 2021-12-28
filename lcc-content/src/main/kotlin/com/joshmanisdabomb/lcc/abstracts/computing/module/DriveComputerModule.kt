package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.abstracts.computing.DiskInfo
import com.joshmanisdabomb.lcc.abstracts.computing.medium.DigitalMedium
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.gui.screen.DriveScreen
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.item.DigitalMediumItem
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.inventory.PredicatedSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text

class DriveComputerModule(vararg val mediums: DigitalMedium, val slotY: Int = 22) : ComputerModule() {

    override val expectedInventorySize = 1

    override fun getInternalDisks(inv: LCCInventory): Set<ItemStack> {
        val set = mutableSetOf<ItemStack>()
        for (stack in inv.list) {
            val item = stack.item as? DigitalMediumItem ?: continue
            if (mediums.contains(item.medium)) {
                set.add(stack)
            }
        }
        return set
    }

    override fun initScreenHandler(handler: ComputingScreenHandler, slotAdder: (slot: Slot) -> Unit, half: ComputingBlockEntity.ComputingHalf, inv: LCCInventory, playerInv: PlayerInventory) {
        slotAdder(object : PredicatedSlot(inv, 0, 80, slotY, { mediums.contains((it.item as? DigitalMediumItem)?.medium) }) {
            override fun getMaxItemCount() = 1
        })

        handler.addPlayerSlots(playerInv, 8, 58, slotAdder)
    }

    override fun createScreen(handler: ComputingScreenHandler, playerInv: PlayerInventory, text: Text) = DriveScreen(handler, playerInv, text)

}