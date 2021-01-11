package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.TranslatableText

class CompositeManufactoryBlock(settings: Settings) : RefiningBlock(settings) {

    override val availableProcesses get() = RefiningProcess.all

    override val maxEnergy = 8000f

    override val inputSlotCount = 9
    override val outputSlotCount = 9

    override val defaultDisplayName = TranslatableText("container.lcc.composite_manufactory")

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: DefaultInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler? {
        TODO("Not yet implemented")
    }

}