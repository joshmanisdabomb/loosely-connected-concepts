package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import com.joshmanisdabomb.lcc.inventory.container.CompositeProcessorScreenHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.PropertyDelegate

class CompositeProcessorBlock(settings: Settings) : RefiningBlock(settings) {

    override val availableProcesses get() = RefiningProcess.all

    override val maxEnergy = 8000f

    override val inputWidth = 3
    override val inputHeight = 3
    override val outputSlotCount = 9

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: RefiningInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate) = CompositeProcessorScreenHandler(syncId, inv, inventory, propertyDelegate)

}