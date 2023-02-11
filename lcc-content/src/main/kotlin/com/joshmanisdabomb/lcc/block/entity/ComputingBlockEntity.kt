package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.abstracts.computing.storage.StorageDisk
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.abstracts.computing.session.ComputingSessionExecuteContext
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.base.EnergyStorage
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyHandler
import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.network.ComputingNetwork
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.enums.SlabType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.DoubleInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.screen.PropertyDelegate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class ComputingBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.computing, pos, state), SidedInventory, WorldEnergyHandler, EnergyStorage<WorldEnergyContext> {

    private var top: ComputingHalf? = null
    private var bottom: ComputingHalf? = null

    private val doubleInventory get() = DoubleInventory(top?.inventory, bottom?.inventory)
    private val inventories get() = when {
        top?.inventory != null && bottom?.inventory != null -> doubleInventory
        top?.inventory != null -> top?.inventory
        bottom?.inventory != null -> bottom?.inventory
        else -> null
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        top =  if (nbt.contains("Top", NBT_COMPOUND)) ComputingHalf(nbt.getCompound("Top"), true) else null
        bottom = if (nbt.contains("Bottom", NBT_COMPOUND)) ComputingHalf(nbt.getCompound("Bottom"), false) else null
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)

        top?.also { NbtCompound().apply { it.writeNbt(this); nbt.put("Top", this) } }
        bottom?.also { NbtCompound().apply { it.writeNbt(this); nbt.put("Bottom", this) } }
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt() = createNbt()

    fun getHalf(top: Boolean) = top.transform(this.top, this.bottom)

    fun getHalves(filter: SlabType = SlabType.DOUBLE) = when (filter) {
        SlabType.TOP -> listOf(top)
        SlabType.BOTTOM -> listOf(bottom)
        else -> listOf(top, bottom)
    }.filterNotNull()

    fun setHalf(half: ComputingHalf) {
        when (half.top) {
            true -> this.top = half
            else -> this.bottom = half
        }
    }

    fun removeHalf(top: Boolean) {
        when (top) {
            true -> this.top = null
            else -> this.bottom = null
        }
    }

    companion object {
        fun clientTick(world: World, pos: BlockPos, state: BlockState, entity: ComputingBlockEntity) {
            entity.getHalves().forEach { it.module.clientTick(it) }
        }

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: ComputingBlockEntity) {
            entity.getHalves().forEach { it.module.serverTick(it) }
        }
    }

    override fun clear() { inventories?.clear() }
    override fun size() = inventories?.size() ?: 0
    override fun isEmpty() = inventories?.isEmpty ?: true
    override fun getStack(slot: Int) = inventories?.getStack(slot) ?: ItemStack.EMPTY
    override fun removeStack(slot: Int, amount: Int) = inventories?.removeStack(slot, amount) ?: ItemStack.EMPTY
    override fun removeStack(slot: Int) = inventories?.removeStack(slot) ?: ItemStack.EMPTY
    override fun setStack(slot: Int, stack: ItemStack) { inventories?.setStack(slot, stack) }
    override fun canPlayerUse(player: PlayerEntity) = inventories?.canPlayerUse(player) ?: false

    override fun getAvailableSlots(side: Direction): IntArray {
        val ti = top?.inventory
        val bi = bottom?.inventory
        if (ti == null) return bi?.slotInts ?: intArrayOf()
        if (bi == null) return ti.slotInts
        val bottomSlots = bi.slotInts.map { it.plus(ti.size()) }.toIntArray()
        return when (side) {
            Direction.UP -> ti.slotInts
            Direction.DOWN -> bottomSlots
            else -> bottomSlots + ti.slotInts
        }
    }

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = when (dir) {
        Direction.UP -> top?.inventory?.isValid(slot, stack) ?: false
        else -> bottom?.inventory?.isValid(slot.minus(top?.inventory?.size() ?: 0), stack) ?: false
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = dir == Direction.DOWN && bottom?.inventory != null

    override fun getRawEnergy(context: WorldEnergyContext) = when (context.side) {
        Direction.UP -> getHalf(true)?.rawEnergy
        Direction.DOWN -> getHalf(false)?.rawEnergy
        else -> null
    }

    override fun setRawEnergy(context: WorldEnergyContext, amount: Float) {
        when (context.side) {
            Direction.UP -> getHalf(true)?.rawEnergy = amount
            Direction.DOWN -> getHalf(false)?.rawEnergy = amount
            else -> {}
        }
    }

    override fun getRawEnergyMaximum(context: WorldEnergyContext) = when (context.side) {
        Direction.UP -> getHalf(true)?.rawEnergyMaximum
        Direction.DOWN -> getHalf(false)?.rawEnergyMaximum
        else -> null
    }

    override fun addEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext): Float {
        val side = context.side
        if (side == null || side.isHorizontal) {
            return EnergyTransaction()
                .include { addEnergyDirect(amount, unit, context.copy(side = Direction.UP)) }
                .include { addEnergyDirect(amount, unit, context.copy(side = Direction.DOWN)) }
                .run(amount)
        }
        return addEnergyDirect(amount, unit, context)
    }

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    inner class ComputingHalf(val module: ComputerModule, val direction: Direction, val color: Int, val top: Boolean) : ExtendedScreenHandlerFactory, ComputingSessionExecuteContext {

        val be get() = this@ComputingBlockEntity

        var extra: NbtCompound? = module.createExtraData()
        val inventory = module.createInventory()?.apply { addListener { this@ComputingBlockEntity.markDirty() } }
        val rawEnergyMaximum get() = module.rawEnergyMaximum
        var rawEnergy: Float? = rawEnergyMaximum?.times(0)
        private var energy: Float
            get() = rawEnergy ?: 0f
            set(value) { rawEnergy = value }

        var customName: Text? = null

        val propertyDelegate = object : PropertyDelegate {
            override fun get(index: Int) = when (index) {
                0 -> this@ComputingHalf.energyDisplay.first
                1 -> this@ComputingHalf.energyDisplay.second
                else -> 0
            }

            override fun set(index: Int, value: Int) = when (index) {
                0 -> this@ComputingHalf.energyDisplay.first = value
                1 -> this@ComputingHalf.energyDisplay.second = value
                else -> Unit
            }

            override fun size() = 2
        }
        private val energyDisplay = DecimalTransport(::energy)

        constructor(nbt: NbtCompound, top: Boolean) : this(LCCRegistries.computer_modules[Identifier(nbt.getString("Module"))], Direction.fromHorizontal(nbt.getByte("Direction").toInt()), nbt.getInt("Color"), top) {
            if (nbt.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(nbt.getString("CustomName"))
            if (nbt.contains("Energy", NBT_FLOAT)) rawEnergy = nbt.getFloat("Energy")
            if (nbt.contains("Extra", NBT_COMPOUND)) extra = nbt.getCompound("Extra")
            inventory?.apply { clear(); Inventories.readNbt(nbt, list) }
        }

        fun writeNbt(nbt: NbtCompound) {
            nbt.putString("Module", module.id.toString())
            nbt.putByte("Direction", direction.horizontal.toByte())
            nbt.putInt("Color", color)
            if (customName != null) nbt.putString("CustomName", Text.Serializer.toJson(customName))
            rawEnergy?.apply { nbt.putFloat("Energy", this) }
            extra?.apply { nbt.put("Extra", this) }
            inventory?.apply { Inventories.writeNbt(nbt, list) }
        }

        override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = ComputingScreenHandler(syncId, inv, propertyDelegate).initHalf(this)

        override fun getDisplayName() = customName ?: Text.translatable("container.lcc.${module.id.path}")

        override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
            buf.writeBlockPos(pos)
            buf.writeBoolean(top)
        }

        fun drop(world: ServerWorld) {
            world.server.lootManager.getTable(module.lootTableId).generateLoot(LootContext.Builder(world).random(world.random).build(LootContextTypes.EMPTY)).forEach {
                Block.dropStack(world, pos, it)
            }
        }

        fun connectsTo(other: ComputingHalf) = this.color == other.color

        fun connectsAbove(): ComputingHalf? {
            if (top) {
                val above = (world?.getBlockEntity(pos.up()) as? ComputingBlockEntity)?.getHalf(false) ?: return null
                return if (connectsTo(above)) above else null
            } else {
                val above = getHalf(true) ?: return null
                return if (connectsTo(above)) above else null
            }
        }

        fun connectsBelow(): ComputingHalf? {
            if (top) {
                val below = getHalf(false) ?: return null
                return if (connectsTo(below)) below else null
            } else {
                val below = (world?.getBlockEntity(pos.down()) as? ComputingBlockEntity)?.getHalf(true) ?: return null
                return if (connectsTo(below)) below else null
            }
        }

        fun onUse(state: BlockState, player: PlayerEntity, hand: Hand, hit: BlockHitResult) = module.onUse(this, state, player, hand, hit)

        fun dirtyUpdate() {
            be.markDirty()
            if (world?.isClient == false) {
                val sworld = be.world as? ServerWorld ?: return
                sworld.chunkManager.markForUpdate(be.pos)
            }
        }

        fun getInternalDisks(): Set<StorageDisk> {
            val inv = inventory ?: return emptySet()
            return module.getInternalDisks(inv)
        }

        override fun getSession() = (module as? ComputerComputerModule)?.getSession(this)

        override fun getSessionToken() = extra?.getUuidOrNull("Session")

        override fun setErrorCode(code: Int) {
            (module as? ComputerComputerModule)?.setErrorCode(this, code)
        }

        override fun reboot() {
            (module as? ComputerComputerModule)?.reboot(this)
        }

        override fun shutdown() {
            (module as? ComputerComputerModule)?.shutdown(this)
        }

        override fun getAccessibleDisks(): Set<StorageDisk> {
            val world = be.world ?: return emptySet()
            val result = ComputingNetwork.wired.discover(world, be.pos to top)

            val list = mutableSetOf<StorageDisk>()
            for (half in ComputingNetwork.retrieveHalves(world, result.traversablesAssoc)) {
                list.addAll(half.getInternalDisks())
            }
            return list
        }

        override fun markDirty() = be.markDirty()

        override fun getWorldFromContext() = world!!

    }

}