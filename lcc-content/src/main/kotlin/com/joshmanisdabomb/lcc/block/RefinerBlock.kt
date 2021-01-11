package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import com.joshmanisdabomb.lcc.inventory.container.RefinerScreenHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.text.TranslatableText

class RefinerBlock(settings: Settings) : RefiningBlock(settings) {

    override val availableProcesses get() = arrayOf(RefiningProcess.MIXING, RefiningProcess.ENRICHING)

    override val maxEnergy = LooseEnergy.toStandard(4000f)

    override val defaultDisplayName = TranslatableText("container.lcc.refiner")

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: DefaultInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate) = RefinerScreenHandler(syncId, inv, inventory, propertyDelegate)

}