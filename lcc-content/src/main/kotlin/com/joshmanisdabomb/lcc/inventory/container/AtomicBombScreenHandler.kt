package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.extensions.insertItemWithInventoryMaxStack
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.PredicatedSlot
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.ScreenHandler

class AtomicBombScreenHandler(syncId: Int, private val playerInventory: PlayerInventory, val inventory: LCCInventory) : ScreenHandler(LCCScreenHandlers.atomic_bomb, syncId) {

    constructor(syncId: Int, playerInventory: PlayerInventory) : this(syncId, playerInventory, object : LCCInventory(7) {
        override fun getMaxCountPerStack() = 1
    })

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, 7)
        inventory.onOpen(playerInventory.player)
        inventory.addListener(listener)
        onContentChanged(inventory)

        addSlot(PredicatedSlot(inventory, 0, 14, 22) { it.isOf(Items.TNT) })
        addSlot(PredicatedSlot(inventory, 1, 44, 22) { it.isOf(LCCItems.enriched_uranium_nugget) })
        addSlots(inventory, 74, 22, 5, 1, ::addSlot, start = 2) { inv, index, x, y -> PredicatedSlot(inv, index, x, y) { it.isOf(LCCItems.enriched_uranium) || it.isOf(LCCBlocks.enriched_uranium_block.asItem()) } }

        addPlayerSlots(playerInventory, 8, 89, ::addSlot)
    }

    val uraniumCount get() = inventory.map { when (it.item) {
        LCCItems.enriched_uranium -> 1
        LCCBlocks.enriched_uranium_block.asItem() -> 9
        else -> 0
    }.times(it.count) }.sum()
    val canDetonate get() = inventory[0].count > 0 && inventory[0].item == Blocks.TNT.asItem() && inventory[1].count > 0 && inventory[1].item == LCCItems.enriched_uranium_nugget && uraniumCount > 0

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.removeListener(listener)
        inventory.onClose(player)
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

    override fun insertItem(stack: ItemStack, startIndex: Int, endIndex: Int, fromLast: Boolean) = insertItemWithInventoryMaxStack(stack, startIndex, endIndex, fromLast)

}
