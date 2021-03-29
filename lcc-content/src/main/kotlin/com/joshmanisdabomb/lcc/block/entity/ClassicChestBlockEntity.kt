package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.enums.ChestType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.DoubleInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.state.property.Properties.CHEST_TYPE
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class ClassicChestBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.classic_chest, pos, state), NamedScreenHandlerFactory, SidedInventory {

    val other: ClassicChestBlockEntity? get() {
        if (cachedState[CHEST_TYPE] != ChestType.SINGLE) {
            return world?.getBlockEntity(pos.offset(LCCBlocks.classic_chest.getDirectionToAttached(cachedState))) as? ClassicChestBlockEntity
        } else {
            return null
        }
    }
    val double get() = other != null

    val inventory = LCCInventory(27).apply { addListener { this@ClassicChestBlockEntity.markDirty() } }
    val otherInventory get() = other?.inventory
    val leftInventory get() = when (cachedState[CHEST_TYPE]) {
        ChestType.LEFT -> otherInventory
        else -> inventory
    }
    val rightInventory get() = when (cachedState[CHEST_TYPE]) {
        ChestType.LEFT -> inventory
        else -> otherInventory
    }
    val doubleInventory get() = DoubleInventory(leftInventory, rightInventory)

    var customName: Text? = null
    val otherName get() = other?.customName
    val doubleName get() = when (cachedState[CHEST_TYPE]) {
        ChestType.LEFT -> otherName ?: customName
        else -> customName ?: otherName
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): GenericContainerScreenHandler? {
        if (double) {
            return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, doubleInventory)
        }
        return GenericContainerScreenHandler.createGeneric9x3(syncId, inv, inventory)
    }

    override fun getDisplayName(): Text {
        if (double) {
            return doubleName ?: TranslatableText("container.chest")
        }
        return customName ?: TranslatableText("container.chest")
    }

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)

        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: NbtCompound): NbtCompound {
        super.writeNbt(tag)

        if (customName != null) tag.putString("CustomName", Text.Serializer.toJson(customName))

        Inventories.writeNbt(tag, inventory.list)

        return tag
    }

    override fun clear() = if (double) doubleInventory.clear() else inventory.clear()
    override fun size() = if (double) doubleInventory.size() else inventory.size()
    override fun isEmpty() = if (double) doubleInventory.isEmpty else inventory.isEmpty
    override fun getStack(slot: Int) = if (double) doubleInventory.getStack(slot) else inventory.getStack(slot)
    override fun removeStack(slot: Int, amount: Int) = if (double) doubleInventory.removeStack(slot, amount) else inventory.removeStack(slot, amount)
    override fun removeStack(slot: Int) = if (double) doubleInventory.removeStack(slot) else inventory.removeStack(slot)
    override fun setStack(slot: Int, stack: ItemStack) = if (double) doubleInventory.setStack(slot, stack) else inventory.setStack(slot, stack)
    override fun canPlayerUse(player: PlayerEntity) = if (double) doubleInventory.canPlayerUse(player) else inventory.canPlayerUse(player)

    override fun getAvailableSlots(side: Direction) = (0 until size()).toList().toIntArray()
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = true
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = true

}