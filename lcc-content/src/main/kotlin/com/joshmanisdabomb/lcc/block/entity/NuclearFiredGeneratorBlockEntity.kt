package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyStorage
import com.joshmanisdabomb.lcc.extensions.NBT_FLOAT
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.container.NuclearFiredGeneratorScreenHandler
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.PropertyDelegate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.Properties.LIT
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class NuclearFiredGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.nuclear_generator, pos, state), ExtendedScreenHandlerFactory, SidedInventory, WorldEnergyStorage {

    val inventory by lazy { object : LCCInventory(5) {

        override fun isValid(slot: Int, stack: ItemStack) = when (slot) {
            0 -> stack.isOf(Items.TNT)
            1 -> stack.isOf(LCCItems.enriched_uranium_nugget)
            2 -> stack.isOf(LCCItems.nuclear_fuel)
            3 -> stack.isOf(Items.ICE)
            else -> StackEnergyHandler.containsPower(stack)
        }

    }.apply {
        addSegment("starter", 1)
        addSegment("startup", 1)
        addSegment("fuel", 1)
        addSegment("coolant", 1)
        addSegment("power", 1)
        addListener { this@NuclearFiredGeneratorBlockEntity.markDirty() }
    } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.first
            1 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.second
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.first = value
            1 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.second = value
            else -> Unit
        }

        override fun size() = 1
    }

    var customName: Text? = null

    var working = false
    var output = 0f

    override var rawEnergy: Float? = 0f
    override val rawEnergyMaximum get() = LooseEnergy.toStandard(200f)
    private var energy: Float
        get() = rawEnergy ?: 0f
        set(value) { rawEnergy = value }

    private val energyDisplay = DecimalTransport(::energy)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = NuclearFiredGeneratorScreenHandler(syncId, inv, inventory, propertyDelegate)

    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
        buf.writeBlockPos(pos)
    }

    override fun getDisplayName() = customName ?: TranslatableText("container.lcc.${LCCBlocks[LCCBlocks.nuclear_generator].name}")

    override fun readNbt(tag: CompoundTag) {
        super.readNbt(tag)

        if (tag.contains("Energy", NBT_FLOAT)) rawEnergy = tag.getFloat("Energy")
        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: CompoundTag): CompoundTag {
        super.writeNbt(tag)

        rawEnergy?.apply { tag.putFloat("Energy", this) }
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

    fun toggle(by: LivingEntity?): Boolean {
        val world = world ?: return false
        if (world.isClient) return false
        if (!working) {
            if (!canStartup()) return false
            inventory[0].decrement(1)
            inventory[1].decrement(1)
            removeEnergyDirect(200f, LooseEnergy, WorldEnergyContext(world, pos, null, null))
            world.setBlockState(pos, world.getBlockState(pos).with(LIT, true))
        } else {
            if (output > 0f) return false
            world.setBlockState(pos, world.getBlockState(pos).with(LIT, false))
        }
        working = !working
        return true
    }

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    private fun canStartup() = (getEnergy(LooseEnergy, WorldEnergyContext(world, pos, null, null)) ?: 0f) >= 200f && inventory[0].isOf(Items.TNT) && inventory[1].isOf(LCCItems.enriched_uranium_nugget)

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: NuclearFiredGeneratorBlockEntity) {
            EnergyTransaction()
                .apply { entity.inventory.slotsIn("power")?.also { includeAll(it.filter { (it.item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(it)) == true }.map { stack -> { entity.extractEnergy(stack.item as StackEnergyHandler, it, LooseEnergy, WorldEnergyContext(world, pos, null, null)) { StackEnergyContext(stack) } } }) } }
                .include { entity.requestEnergy(WorldEnergyContext(world, pos, null, null), it, LooseEnergy, *Direction.values()) }
                .run(50f)
        }
    }

}