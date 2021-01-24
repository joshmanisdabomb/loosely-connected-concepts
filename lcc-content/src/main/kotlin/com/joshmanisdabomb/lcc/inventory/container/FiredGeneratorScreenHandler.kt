package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.FiredGeneratorBlock
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

abstract class FiredGeneratorScreenHandler(type: ScreenHandlerType<out ScreenHandler>, syncId: Int, protected val playerInventory: PlayerInventory, val inventory: LCCInventory, val properties: PropertyDelegate) : ScreenHandler(type, syncId) {

    abstract val block: FiredGeneratorBlock

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, block.slots)
        checkDataCount(properties, 7)

        inventory.addListener(listener)
        inventory.onOpen(playerInventory.player)

        addProperties(properties)
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.onClose(player)
        inventory.removeListener(listener)
    }

    override fun onContentChanged(inventory: Inventory) {
        super.onContentChanged(inventory)
    }

    override fun canUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (index < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }

            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }

    @Environment(EnvType.CLIENT)
    fun burnAmount() = properties.get(0)

    @Environment(EnvType.CLIENT)
    fun maxBurnAmount() = properties.get(1)

    @Environment(EnvType.CLIENT)
    fun outputAmount() = DecimalTransport.from(properties.get(2), properties.get(3))

    @Environment(EnvType.CLIENT)
    fun outputCeilingAmount() = DecimalTransport.from(properties.get(4), properties.get(5))

    @Environment(EnvType.CLIENT)
    fun waterAmount() = properties.get(6)

}
