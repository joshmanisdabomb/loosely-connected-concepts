package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.insertItemWithInventoryMaxStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler

class ComputingScreenHandler(syncId: Int, protected val playerInventory: PlayerInventory) : ScreenHandler(LCCScreenHandlers.computing, syncId) {

    private var _half: ComputingBlockEntity.ComputingHalf? = null
    val half get() = _half!!

    val listener = InventoryChangedListener(::onContentChanged)

    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf) : this(syncId, playerInventory) {
        val pos = buf.readBlockPos()
        val top = buf.readBoolean()

        val be = playerInventory.player.world.getBlockEntity(pos) as? ComputingBlockEntity ?: return
        val half = be.getHalf(top) ?: return
        initHalf(half)
    }

    fun initHalf(half: ComputingBlockEntity.ComputingHalf) : ComputingScreenHandler {
        _half = half
        val inventory = half.inventory!!

        checkSize(inventory, half.module.expectedInventorySize)
        inventory.onOpen(playerInventory.player)
        inventory.addListener(listener)
        onContentChanged(inventory)

        half.module.initScreenHandler(this, this::addSlot, half, inventory, playerInventory)

        return this
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        half.inventory?.onClose(player)
        half.inventory?.removeListener(listener)
    }

    override fun canUse(player: PlayerEntity) = half.inventory?.canPlayerUse(player) ?: false

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[index]
        val inventory = half.inventory!!
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