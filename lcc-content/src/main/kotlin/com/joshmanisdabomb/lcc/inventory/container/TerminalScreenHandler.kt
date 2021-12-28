package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.block.entity.TerminalBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.BlockPos

class TerminalScreenHandler(syncId: Int, protected val playerInventory: PlayerInventory, val properties: PropertyDelegate) : ScreenHandler(LCCScreenHandlers.terminal, syncId) {

    private var _pos: BlockPos? = null
    val pos get() = _pos!!

    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf) : this(syncId, playerInventory, ArrayPropertyDelegate(2)) {
        _pos = buf.readBlockPos()
    }

    init {
        checkDataCount(properties, 2)

        addPlayerSlots(playerInventory, 48, 149, ::addSlot)

        addProperties(properties)
    }

    override fun canUse(player: PlayerEntity) = true

    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[index]
        if (slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (index < 9) {
                if (!insertItem(originalStack, 9, 36, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, 9, false)) {
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

}