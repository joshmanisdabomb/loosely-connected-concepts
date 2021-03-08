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
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.container.OxygenExtractorScreenHandler
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
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
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
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@OxygenExtractorBlockEntity.energyDisplay.first = value
            1 -> this@OxygenExtractorBlockEntity.energyDisplay.second = value
            else -> Unit
        }

        override fun size() = 2
    }

    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = OxygenExtractorScreenHandler(syncId, inv, inventory, propertyDelegate)

    override fun getDisplayName() = customName ?: TranslatableText("container.lcc.oxygen_extractor")

    override fun readNbt(tag: CompoundTag) {
        super.readNbt(tag)

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: CompoundTag): CompoundTag {
        super.writeNbt(tag)

        Inventories.writeNbt(tag, inventory.list)

        return tag
    }

    fun getOxygenAmount() = world!!.run { Direction.values().filter { it != Direction.DOWN }.sumOf { getOxygenAmount(this, it).toDouble() }.toFloat().times(when (this.registryKey) {
        World.OVERWORLD -> 1f
        else -> 0.25f
    }) }
    fun getOxygenAmount(world: World, side: Direction): Float {
        val pos = pos.offset(side)
        val state = world.getBlockState(pos)
        if (!state.fluidState.isEmpty) return 0f
        if (state.isSideSolidFullSquare(world, pos, side.opposite)) return 0f
        if (!state.getCollisionShape(world, pos).isEmpty) return 0.15f
        if (!state.isAir) return 0.35f
        return 1f
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
                entity.removeEnergyDirect(stacks.size.toFloat().times(9f), LooseEnergy, WorldEnergyContext(world, pos, null, null))
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
                .run(10f)
        }
    }

}