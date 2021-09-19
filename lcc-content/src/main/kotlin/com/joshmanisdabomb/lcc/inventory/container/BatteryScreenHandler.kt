package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.BatteryBlock
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyStorage
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

abstract class BatteryScreenHandler(type: ScreenHandlerType<out ScreenHandler>, syncId: Int, protected val playerInventory: PlayerInventory, val inventory: LCCInventory, val properties: PropertyDelegate) : ScreenHandler(type, syncId) {

    abstract val block: BatteryBlock

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, block.slotCount)
        checkDataCount(properties, 3)

        inventory.addListener(listener)
        inventory.onOpen(playerInventory.player)

        addProperties(properties)
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.onClose(player)
        inventory.removeListener(listener)
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
            } else {
                (originalStack.item as? StackEnergyStorage).also {
                    if (it == null) return ItemStack.EMPTY

                    val econtext = StackEnergyContext(originalStack)
                    if (!it.isEnergyUsable(econtext)) return ItemStack.EMPTY

                    if (it.getEnergy(LooseEnergy, econtext) ?: 0f > 0f) {
                        if (!insertItem(originalStack, inventory.getSegmentStart("input") ?: return@also, inventory.getSegmentEnd("input")?.plus(1) ?: return@also, false)) {
                            return ItemStack.EMPTY
                        }
                    } else {
                        if (!insertItem(originalStack, inventory.getSegmentStart("output") ?: return@also, inventory.getSegmentEnd("output")?.plus(1) ?: return@also, false)) {
                            return ItemStack.EMPTY
                        }
                    }
                }
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
    fun powerAmount() = DecimalTransport.from(properties.get(0), properties.get(1), properties.get(2))

}
