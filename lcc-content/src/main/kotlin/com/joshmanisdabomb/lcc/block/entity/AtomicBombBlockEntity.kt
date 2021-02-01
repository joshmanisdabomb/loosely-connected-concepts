package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.inventory.AtomicBombInventory
import com.joshmanisdabomb.lcc.inventory.container.AtomicBombScreenHandler
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class AtomicBombBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.atomic_bomb, pos, state), NamedScreenHandlerFactory, SidedInventory {

    val inventory by lazy { AtomicBombInventory().apply { addListener { this@AtomicBombBlockEntity.markDirty() } } }
    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = AtomicBombScreenHandler(syncId, inv, inventory)

    override fun getDisplayName() = customName ?: TranslatableText("container.lcc.atomic_bomb")

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)

        if (tag.contains("CustomName", 8)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        inventory.apply { clear(); Inventories.fromTag(tag, list) }
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)

        if (customName != null) tag.putString("CustomName", Text.Serializer.toJson(customName))

        Inventories.toTag(tag, inventory.list)

        return tag
    }

    override fun clear() = inventory.clear()
    override fun size() = inventory.size()
    override fun isEmpty() = inventory.isEmpty
    override fun getStack(slot: Int) = inventory.getStack(slot)
    override fun removeStack(slot: Int, amount: Int) = inventory.removeStack(slot, amount)
    override fun removeStack(slot: Int) = inventory.removeStack(slot)
    override fun setStack(slot: Int, stack: ItemStack) = inventory.setStack(slot, stack)
    override fun canPlayerUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun isValid(slot: Int, stack: ItemStack) = inventory.isValid(slot, stack)

    override fun getAvailableSlots(side: Direction) = inventory.slotInts
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = true
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = true

    fun detonate(player: PlayerEntity?): Boolean {
        if (world?.isClient != false || !inventory.canDetonate) return false
        return true
    }

}
