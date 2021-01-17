package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCTags
import com.joshmanisdabomb.lcc.extensions.to
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import com.joshmanisdabomb.lcc.inventory.container.CoalFiredGeneratorScreenHandler
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate

class CoalFiredGeneratorBlock(settings: Settings) : FiredGeneratorBlock(settings) {

    override val slots = 3
    override val maxOutput = 2f

    override fun getBurnTime(stack: ItemStack): Int? {
        if (stack.isEmpty) return null
        return FuelRegistry.INSTANCE.get(stack.item)
    }

    override fun getSteam(stack: ItemStack) = stack.isIn(LCCTags.furnace_generator_double).to(2f, 1f)

    override fun createMenu(syncId: Int, inv: PlayerInventory, inventory: DefaultInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate) = CoalFiredGeneratorScreenHandler(syncId, inv, inventory, propertyDelegate)

}
