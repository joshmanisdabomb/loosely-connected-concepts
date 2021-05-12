package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.joshmanisdabomb.lcc.block.entity.OxygenExtractorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.inventory.PredicatedSlot
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.Direction

class OxygenExtractorScreenHandler(syncId: Int, protected val playerInventory: PlayerInventory, val inventory: LCCInventory, val properties: PropertyDelegate) : ScreenHandler(LCCScreenHandlers.oxygen_extractor, syncId) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, LCCInventory(4), ArrayPropertyDelegate(10))

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, 4)
        checkDataCount(properties, 10)

        addSlots(inventory, 40, 38, 3, 1, ::addSlot, start = 0) { inv, index, x, y -> PredicatedSlot(inv, index, x, y) { (it.item as? OxygenStorage)?.isFull(it) == false } }
        addSlot(PredicatedSlot(inventory, 3, 120, 38) { StackEnergyHandler.containsPower(it) })
        addPlayerSlots(playerInventory, 8, 69, ::addSlot)

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
    fun powerAmount() = DecimalTransport.from(properties.get(0), properties.get(1))

    @Environment(EnvType.CLIENT)
    fun oxygenAmount() = DecimalTransport.from(properties.get(2), properties.get(3))

    @Environment(EnvType.CLIENT)
    fun oxygenModifier() = properties.get(4)

    @Environment(EnvType.CLIENT)
    fun oxygenAmount(side: Direction) = OxygenExtractorBlockEntity.OxygenThroughput.values()[properties.get(when (side) {
        Direction.UP -> 5
        else -> 6+side.opposite.horizontal
    })]

}
