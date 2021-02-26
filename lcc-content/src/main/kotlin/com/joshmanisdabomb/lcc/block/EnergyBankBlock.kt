package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.container.EnergyBankScreenHandler
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.PropertyDelegate

class EnergyBankBlock(settings: Settings) : BatteryBlock(settings) {

    override val max = LooseEnergy.fromCoals(32f)
    override val inputs = 3
    override val outputs = 3

    override fun alterEnergy(current: Float) = current.minus(0.003f).times(0.999999f)

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: LCCInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate) = EnergyBankScreenHandler(syncId, inv, inventory, propertyDelegate)

}
