package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.energy.EnergyHandler
import com.joshmanisdabomb.lcc.energy.EnergyStorage
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

class RefiningBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.refining, pos, state), NamedScreenHandlerFactory, SidedInventory, EnergyStorage {

    val refiningBlock get() = cachedState.block as? RefiningBlock

    val inventory by lazy { object : DefaultInventory(refiningBlock?.slotCount ?: 15) {

        override fun isValid(slot: Int, stack: ItemStack): Boolean {
            if (slot >= refiningBlock?.slotCount?.minus(refiningBlock!!.fuelSlotCount) ?: 12) return stack.item == Items.REDSTONE
            if (slot >= refiningBlock?.slotCount?.minus(refiningBlock!!.fuelSlotCount)?.minus(refiningBlock!!.outputSlotCount) ?: 6) return false
            return true
        }

    }.apply { addListener { this@RefiningBlockEntity.markDirty() } } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@RefiningBlockEntity.energy
            1 -> this@RefiningBlockEntity.cookTime
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@RefiningBlockEntity.energy = value
            1 -> this@RefiningBlockEntity.cookTime = value
            else -> Unit
        }

        override fun size() = 2
    }

    private var cookTime = 0

    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = refiningBlock?.createMenu(syncId, inv, inventory, player, propertyDelegate)

    override fun getDisplayName() = customName ?: refiningBlock?.defaultDisplayName ?: TranslatableText("container.lcc.refiner")

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
        Direction.UP -> inputRange
        Direction.DOWN -> outputRange
        else -> fuelRange
    }
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = isValid(slot, stack)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = dir == Direction.DOWN

    private var energy: Int
        get() = (rawEnergy ?: 0f).times(1000).toInt()
        set(value) { rawEnergy = value.div(1000).toFloat() }

    override var rawEnergy: Float? = 0f
    override val rawEnergyBounds by lazy { 0f..(refiningBlock?.maxEnergy ?: 800f) }

    override fun removeEnergy(amount: Float, unit: EnergyUnit, from: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?) = 0f

    companion object {
        val inputRange = (0 until 9).toList().toIntArray()
        val outputRange = (9 until 18).toList().toIntArray()
        val fuelRange = (18 until 21).toList().toIntArray()

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RefiningBlockEntity) {
            println(entity.getEnergy(LooseEnergy, null))
            Direction.values().forEach {
                EnergyHandler.worldExtract(entity, world, pos, state, it, 50f, LooseEnergy)
            }
        }
    }

}