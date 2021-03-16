package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import com.joshmanisdabomb.lcc.inventory.container.RefinerScreenHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.PropertyDelegate

class RefinerBlock(settings: Settings) : RefiningBlock(settings) {

    override val availableProcesses get() = arrayOf(RefiningProcess.MIXING, RefiningProcess.ENRICHING, RefiningProcess.TREATING, RefiningProcess.ARC_SMELTING, RefiningProcess.DRYING, RefiningProcess.PRESSING)

    override val maxEnergy = LooseEnergy.toStandard(4000f)

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: RefiningInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate) = RefinerScreenHandler(syncId, inv, inventory, propertyDelegate)

}