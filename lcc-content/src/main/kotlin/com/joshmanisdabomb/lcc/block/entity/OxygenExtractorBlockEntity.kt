package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.abstracts.oxygen.OxygenStorage
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyStorage
import com.joshmanisdabomb.lcc.inventory.container.OxygenExtractorScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
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

class OxygenExtractorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.oxygen_extractor, pos, state), NamedScreenHandlerFactory, SidedInventory, WorldEnergyStorage {

    val inventory by lazy { object : LCCInventory(4) {

        override fun isValid(slot: Int, stack: ItemStack) = when (slot) {
            3 -> StackEnergyHandler.containsPower(stack)
            else -> (stack.item as? OxygenStorage)?.isFull(stack) == false
        }

    }.apply { addListener { this@OxygenExtractorBlockEntity.markDirty() } } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@OxygenExtractorBlockEntity.energyDisplay.first
            1 -> this@OxygenExtractorBlockEntity.energyDisplay.second
            2 -> this@OxygenExtractorBlockEntity.oxygenTotalDisplay.first
            3 -> this@OxygenExtractorBlockEntity.oxygenTotalDisplay.second
            4 -> world?.let { getWorldOxygenModifier(it) } ?: 0
            5 -> world?.let { this@OxygenExtractorBlockEntity.getOxygenAmount(it, Direction.UP).ordinal } ?: 0
            6 -> world?.let { this@OxygenExtractorBlockEntity.getOxygenAmount(it, Direction.NORTH).ordinal } ?: 0
            7 -> world?.let { this@OxygenExtractorBlockEntity.getOxygenAmount(it, Direction.EAST).ordinal } ?: 0
            8 -> world?.let { this@OxygenExtractorBlockEntity.getOxygenAmount(it, Direction.SOUTH).ordinal } ?: 0
            9 -> world?.let { this@OxygenExtractorBlockEntity.getOxygenAmount(it, Direction.WEST).ordinal } ?: 0
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@OxygenExtractorBlockEntity.energyDisplay.first = value
            1 -> this@OxygenExtractorBlockEntity.energyDisplay.second = value
            2 -> this@OxygenExtractorBlockEntity.oxygenTotalDisplay.first = value
            3 -> this@OxygenExtractorBlockEntity.oxygenTotalDisplay.second = value
            else -> Unit
        }

        override fun size() = 10
    }

    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = OxygenExtractorScreenHandler(syncId, inv, inventory, propertyDelegate)

    override fun getDisplayName() = customName ?: Text.translatable("container.lcc.oxygen_extractor")

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)

        Inventories.writeNbt(tag, inventory.list)
    }

    fun getOxygenAmount() = world!!.run { Direction.values().filter { it != Direction.DOWN }.sumOf { getOxygenAmount(this, it).oxygen.times(getDirectionOxygenModifier(it)).toDouble() }.toFloat().div(getWorldOxygenModifier(this)) }

    fun getOxygenAmount(world: World, side: Direction): OxygenThroughput {
        val pos = pos.offset(side)
        val state = world.getBlockState(pos)
        if (!state.fluidState.isEmpty) return OxygenThroughput.BLOCKED
        if (state.isSideSolidFullSquare(world, pos, side.opposite)) return OxygenThroughput.BLOCKED
        if (!state.getCollisionShape(world, pos).isEmpty) return OxygenThroughput.OBSTRUCTED
        if (!state.isAir) return OxygenThroughput.SUBOPTIMAL
        return OxygenThroughput.OPTIMAL
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
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = dir == Direction.DOWN

    override var rawEnergy: Float? = 0f
    override val rawEnergyMaximum get() = LCCBlocks.oxygen_extractor.max
    private var energy: Float
        get() = rawEnergy ?: 0f
        set(value) { rawEnergy = value }
    private val energyDisplay = DecimalTransport(::energy)

    private var oxygenTotal get() = getOxygenAmount(); set(_) { }
    private val oxygenTotalDisplay = DecimalTransport(::oxygenTotal)

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: OxygenExtractorBlockEntity) {
            val energy = entity.getEnergy(LooseEnergy, WorldEnergyContext(world, pos, null, null)) ?: 0f
            val stacks = (0 until 3).map { entity.inventory[it] }.filter {
                val item = it.item as? OxygenStorage ?: return@filter false
                if (item.isFull(it)) return@filter false
                true
            }
            if (stacks.isNotEmpty() && energy >= stacks.size) {
                entity.removeEnergyDirect(stacks.size.toFloat().times(6f), LooseEnergy, WorldEnergyContext(world, pos, null, null))
                val oxygen = entity.getOxygenAmount().div(stacks.size)
                stacks.forEach { (it.item as? OxygenStorage)?.addOxygen(it, oxygen) }
            }

            EnergyTransaction()
                .apply {
                    if ((entity.inventory[3].item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(entity.inventory[3])) == true) {
                        include { entity.extractEnergy(entity.inventory[3].item as StackEnergyHandler, it, LooseEnergy, WorldEnergyContext(world, pos, null, null)) { StackEnergyContext(entity.inventory[3]) } }
                    }
                }
                .include { entity.requestEnergy(WorldEnergyContext(world, pos, null, null), it, LooseEnergy, *Direction.values()) }
                .run(50f)
        }

        fun getWorldOxygenModifier(world: World) = when (world.registryKey) {
            World.OVERWORLD -> 1
            else -> 4
        }

        fun getDirectionOxygenModifier(side: Direction) = when (side) {
            Direction.UP -> 1.0f
            else -> 0.25f
        }
    }

    enum class OxygenThroughput(val oxygen: Float) {
        OPTIMAL(1f),
        SUBOPTIMAL(0.35f),
        OBSTRUCTED(0.15f),
        BLOCKED(0f)
    }

}