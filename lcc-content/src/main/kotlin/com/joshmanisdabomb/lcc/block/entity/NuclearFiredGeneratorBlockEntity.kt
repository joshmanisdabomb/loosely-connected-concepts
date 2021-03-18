package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.container.NuclearFiredGeneratorScreenHandler
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class NuclearFiredGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.nuclear_generator, pos, state), NamedScreenHandlerFactory, SidedInventory {

    val inventory by lazy { object : LCCInventory(4) {

        override fun isValid(slot: Int, stack: ItemStack) = when (slot) {
            0 -> stack.isOf(Items.TNT)
            1 -> stack.isOf(LCCItems.enriched_uranium_nugget)
            2 -> stack.isOf(LCCItems.nuclear_fuel)
            else -> stack.isOf(Items.ICE)
        }

    }.apply {
        addSegment("starter", 1)
        addSegment("startup", 1)
        addSegment("fuel", 1)
        addSegment("coolant", 1)
        addListener { this@NuclearFiredGeneratorBlockEntity.markDirty() }
    } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            else -> Unit
        }

        override fun size() = 1
    }

    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = NuclearFiredGeneratorScreenHandler(syncId, inv, inventory, propertyDelegate)

    override fun getDisplayName() = customName ?: TranslatableText("container.lcc.${LCCBlocks[LCCBlocks.nuclear_generator].name}")

    override fun readNbt(tag: CompoundTag) {
        super.readNbt(tag)

        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: CompoundTag): CompoundTag {
        super.writeNbt(tag)

        customName?.apply { tag.putString("CustomName", Text.Serializer.toJson(this)) }

        Inventories.writeNbt(tag, inventory.list)

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
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = isValid(slot, stack)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = false

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: NuclearFiredGeneratorBlockEntity) {

        }
    }

}