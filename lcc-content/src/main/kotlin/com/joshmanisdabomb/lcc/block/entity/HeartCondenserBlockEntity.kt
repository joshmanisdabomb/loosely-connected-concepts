package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.inventory.container.HeartCondenserScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class HeartCondenserBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.heart_condenser, pos, state), NamedScreenHandlerFactory, SidedInventory {

    val inventory by lazy { object : LCCInventory(3) {

        override fun isValid(slot: Int, stack: ItemStack) = when (slot) {
            0 -> stack.isIn(LCCItemTags.hearts)
            1 -> stack.isIn(LCCItemTags.heart_condenser_fuel)
            else -> false
        }

    }.apply {
        addListener { this@HeartCondenserBlockEntity.markDirty() }
        addSegment("input", 1)
        addSegment("fuel", 1)
        addSegment("output", 1)
    } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> burn
            1 -> burnMax
            2 -> cook
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> burn = value
            1 -> burnMax = value
            2 -> cook = value
            else -> Unit
        }

        override fun size() = 2
    }

    private var burn = 0
    private var burnMax = 0
    private var cook = 0

    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = HeartCondenserScreenHandler(syncId, inv, inventory, propertyDelegate)

    override fun getDisplayName() = customName ?: Text.translatable("container.lcc.heart_condenser")

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)

        Inventories.writeNbt(tag, inventory.list)
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

    override fun getAvailableSlots(side: Direction) = when (side) {
        Direction.UP -> inventory.getSegmentSlots("input")
        Direction.DOWN -> inventory.getSegmentSlots("output")
        else -> inventory.getSegmentSlots("fuel")
    }
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = isValid(slot, stack)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = dir == Direction.DOWN

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: HeartCondenserBlockEntity) {
            println(entity.inventory)
        }
    }

}
