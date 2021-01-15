package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler

class OilFiredGeneratorBlock(settings: Settings) : FiredGeneratorBlock(settings) {

    override val slots = 6
    override val maxMultiplier = 2f

    override fun getBurnTime(stack: ItemStack): Int? {
        if (stack.isEmpty) return null
        return when (stack.item) {
            LCCItems.oil_bucket -> 8000
            else -> null
        }
    }

    override fun getSteam(stack: ItemStack) = 6f

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: RefiningInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler {
        TODO("Not yet implemented")
    }

}
