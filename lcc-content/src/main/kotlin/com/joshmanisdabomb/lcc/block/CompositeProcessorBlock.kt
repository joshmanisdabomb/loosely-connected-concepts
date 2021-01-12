package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler

class CompositeProcessorBlock(settings: Settings) : RefiningBlock(settings) {

    override val availableProcesses get() = RefiningProcess.all

    override val maxEnergy = 8000f

    override val inputWidth = 3
    override val inputHeight = 3
    override val outputSlotCount = 9

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: RefiningInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler? {
        TODO("Not yet implemented")
    }

}