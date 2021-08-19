package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.inventory.container.OilFiredGeneratorScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate

class OilFiredGeneratorBlock(settings: Settings) : FiredGeneratorBlock(settings) {

    override val slots = 6
    override val maxOutput = 4f

    override fun getBurnTime(stack: ItemStack): Int? {
        if (stack.isEmpty) return null
        return when (stack.item) {
            LCCItems.fuel_bucket -> 4000
            LCCItems.oil_bucket -> 12000
            else -> null
        }
    }

    override fun getSteam(stack: ItemStack) = when (stack.item) {
        LCCItems.fuel_bucket -> 14f
        else -> 2f
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: LCCInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate) = OilFiredGeneratorScreenHandler(syncId, inv, inventory, propertyDelegate)

}
