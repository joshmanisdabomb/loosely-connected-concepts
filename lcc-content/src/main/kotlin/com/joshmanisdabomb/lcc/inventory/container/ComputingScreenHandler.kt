package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.extensions.insertItemWithInventoryMaxStack
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.BlockPos
import kotlin.text.Typography.half

class ComputingScreenHandler(syncId: Int, protected val playerInventory: PlayerInventory, val properties: PropertyDelegate) : ScreenHandler(LCCScreenHandlers.computing, syncId) {

    private var _pos: BlockPos? = null
    val pos get() = _pos!!
    private var _top: Boolean? = null
    val top get() = _top!!

    lateinit var inventory: LCCInventory

    val listener = InventoryChangedListener(::onContentChanged)

    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf) : this(syncId, playerInventory, ArrayPropertyDelegate(2)) {
        val pos = buf.readBlockPos()
        val top = buf.readBoolean()

        val be = playerInventory.player.world.getBlockEntity(pos) as? ComputingBlockEntity ?: return
        val half = be.getHalf(top) ?: return
        initHalf(half)
    }

    init {
        checkDataCount(properties, 2)

        addProperties(properties)
    }

    fun initHalf(half: ComputingBlockEntity.ComputingHalf) : ComputingScreenHandler {
        _pos = half.be.pos
        _top = half.top
        inventory = half.inventory!!

        checkSize(inventory, half.module.expectedInventorySize)
        inventory.onOpen(playerInventory.player)
        inventory.addListener(listener)
        onContentChanged(inventory)

        half.module.initScreenHandler(this, this::addSlot, half, inventory, playerInventory)

        return this
    }

    override fun close(player: PlayerEntity) {
        super.close(player)
        inventory.onClose(player)
        inventory.removeListener(listener)
    }

    override fun canUse(player: PlayerEntity) = inventory.canPlayerUse(player) ?: false

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

    @Environment(EnvType.CLIENT)
    fun powerAmount() = DecimalTransport.from(properties.get(0), properties.get(1))

}