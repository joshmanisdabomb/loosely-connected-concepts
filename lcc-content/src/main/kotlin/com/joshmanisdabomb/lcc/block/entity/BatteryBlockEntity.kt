package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.BatteryBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyStorage
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyStorage
import com.joshmanisdabomb.lcc.extensions.NBT_FLOAT
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class BatteryBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.battery, pos, state), NamedScreenHandlerFactory, SidedInventory, WorldEnergyStorage {

    val batteryBlock get() = cachedState.block as? BatteryBlock

    val inventory by lazy { object : LCCInventory(batteryBlock!!.slotCount) {

        override fun isValid(slot: Int, stack: ItemStack) = StackEnergyHandler.containsPower(stack)

    }.apply {
        addListener { this@BatteryBlockEntity.markDirty() }
        addSegment("input", batteryBlock!!.inputs)
        addSegment("output", batteryBlock!!.outputs)
    } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@BatteryBlockEntity.energyDisplay.first
            1 -> this@BatteryBlockEntity.energyDisplay.second
            2 -> this@BatteryBlockEntity.energyDisplay.third
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@BatteryBlockEntity.energyDisplay.first = value
            1 -> this@BatteryBlockEntity.energyDisplay.second = value
            2 -> this@BatteryBlockEntity.energyDisplay.third = value
            else -> Unit
        }

        override fun size() = 3
    }

    var customName: Text? = null

    override var rawEnergy: Float? = 0f
    override val rawEnergyMaximum get() = batteryBlock?.max
    private var energy: Float
        get() = rawEnergy ?: 0f
        set(value) { rawEnergy = value }

    private val energyDisplay = DecimalTransport(::energy)

    override fun addEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        if (context.side != null && context.side == context.state?.get(Properties.FACING)) return 0f
        return addEnergyDirect(amount, unit, context)
    }

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        if (context.side != null && context.side != context.state?.get(Properties.FACING)) return 0f
        return removeEnergyDirect(amount, unit, context)
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = batteryBlock!!.createMenu(syncId, inv, inventory, player, propertyDelegate)

    override fun getDisplayName() = customName ?: batteryBlock?.defaultDisplayName

    override fun readNbt(tag: CompoundTag) {
        super.readNbt(tag)

        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        if (tag.contains("Energy", NBT_FLOAT)) rawEnergy = tag.getFloat("Energy")

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: CompoundTag): CompoundTag {
        super.writeNbt(tag)

        customName?.apply { tag.putString("CustomName", Text.Serializer.toJson(this)) }

        rawEnergy?.apply { tag.putFloat("Energy", this) }

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

    override fun getAvailableSlots(side: Direction) = when (side) {
        Direction.UP -> inventory.getSegmentSlots("input")
        Direction.DOWN -> inventory.slotInts
        else -> inventory.getSegmentSlots("output")
    }
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = true
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        val econtext = StackEnergyContext(stack)
        if (inventory.getSegmentRange("input")?.contains(slot) == true) {
            return (stack.item as? StackEnergyStorage)?.run { getEnergy(LooseEnergy, econtext) ?: 0f <= 0f } ?: true
        } else if (inventory.getSegmentRange("output")?.contains(slot) == true) {
            return (stack.item as? StackEnergyStorage)?.run { getEnergy(LooseEnergy, econtext) ?: 0f >= (getMaximumEnergy(LooseEnergy, econtext) ?: return true) } ?: true
        }
        return true
    }

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: BatteryBlockEntity) {
            val from = EnergyTransaction()
                .apply { entity.inventory.slotsIn("input")?.also { includeAll(it.filter { (it.item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(it)) == true }.map { stack -> { entity.extractEnergy(stack.item as StackEnergyHandler, it, LooseEnergy, WorldEnergyContext(world, pos, null, null)) { StackEnergyContext(stack) } } }) } }
                .include { entity.requestEnergy(WorldEnergyContext(world, pos, null, null), it, LooseEnergy, *Direction.values().filter { it != state[Properties.FACING] }.toTypedArray()) }
                .run(800f)

            val to = EnergyTransaction()
                .apply { entity.inventory.slotsIn("output")?.also { includeAll(it.filter { (it.item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(it)) == true }.map { stack -> { entity.insertEnergy(stack.item as StackEnergyHandler, it, LooseEnergy, WorldEnergyContext(world, pos, null, null)) { StackEnergyContext(stack) } } }) } }
                .run(800f)

            if (entity.energy > 0f && from <= 0f && to <= 0f) {
                entity.energy = entity.batteryBlock?.alterEnergy(entity.energy) ?: entity.energy
            }
        }
    }

}