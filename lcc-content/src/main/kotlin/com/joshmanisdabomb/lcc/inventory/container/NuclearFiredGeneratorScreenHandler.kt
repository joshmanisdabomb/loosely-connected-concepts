package com.joshmanisdabomb.lcc.inventory.container

import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.directory.LCCScreenHandlers
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.insertItemWithInventoryMaxStack
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.PredicatedSlot
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.InventoryChangedListener
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.BlockPos

class NuclearFiredGeneratorScreenHandler(syncId: Int, protected val playerInventory: PlayerInventory, val inventory: LCCInventory, val properties: PropertyDelegate) : ScreenHandler(LCCScreenHandlers.nuclear_generator, syncId) {

    private var _pos: BlockPos? = null
    val pos get() = _pos

    constructor(syncId: Int, playerInventory: PlayerInventory, buf: PacketByteBuf) : this(syncId, playerInventory, LCCInventory(5), ArrayPropertyDelegate(9)) {
        _pos = buf.readBlockPos()
    }

    val listener = InventoryChangedListener(::onContentChanged)

    init {
        checkSize(inventory, 5)
        checkDataCount(properties, 9)

        addSlot(object : PredicatedSlot(inventory, 0, 24, 63, { it.isOf(Items.TNT) }) {
            override fun getMaxItemCount() = 1
        })
        addSlot(object : PredicatedSlot(inventory, 1, 54, 63, { it.isOf(LCCItems.enriched_uranium_nugget) }) {
            override fun getMaxItemCount() = 1
        })
        addSlot(PredicatedSlot(inventory, 2, 84, 63) { it.isOf(LCCItems.nuclear_fuel) })
        addSlot(PredicatedSlot(inventory, 3, 84, 38) { NuclearFiredGeneratorBlockEntity.getCoolantValue(it) != null })
        addSlot(PredicatedSlot(inventory, 4, 54, 38, StackEnergyHandler.Companion::containsPower))

        addPlayerSlots(playerInventory, 18, 94, ::addSlot)

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

    override fun insertItem(stack: ItemStack, startIndex: Int, endIndex: Int, fromLast: Boolean) = insertItemWithInventoryMaxStack(stack, startIndex, endIndex, fromLast)

    @Environment(EnvType.CLIENT)
    fun powerAmount() = DecimalTransport.from(properties.get(0), properties.get(1))

    @Environment(EnvType.CLIENT)
    fun outputAmount() = DecimalTransport.from(properties.get(2), properties.get(3))

    @Environment(EnvType.CLIENT)
    fun fuelAmount() = DecimalTransport.from(properties.get(4), properties.get(5))

    @Environment(EnvType.CLIENT)
    fun coolantAmount() = DecimalTransport.from(properties.get(6), properties.get(7))

    @Environment(EnvType.CLIENT)
    fun waterAmount() = properties.get(8)

}