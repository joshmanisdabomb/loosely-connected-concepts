package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.to
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler

class CoalFiredGeneratorBlock(settings: Settings) : FiredGeneratorBlock(settings) {

    override val slots = 3
    override val maxMultiplier = 2f

    override fun getBurnTime(stack: ItemStack): Int? {
        if (stack.isEmpty) return null
        return FuelRegistry.INSTANCE.get(stack.item)
    }

    override fun getSteam(stack: ItemStack) = LooseEnergy.fromCoals(stack.isIn(LCCTags.furnace_generator_x2).to(2f, 1f))

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: RefiningInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler {
        TODO("Not yet implemented")
    }

}
