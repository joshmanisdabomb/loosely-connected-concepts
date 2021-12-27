package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.abstracts.computing.medium.LCCDigitalMediums
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.extensions.addPlayerSlots
import com.joshmanisdabomb.lcc.extensions.addSlots
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.gui.screen.ComputerScreen
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.item.DigitalMediumItem
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.lib.inventory.PredicatedSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.slot.Slot
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.Direction

class ComputerComputerModule : ComputerModule() {

    override val expectedInventorySize = 10

    override val rawEnergyMaximum = LooseEnergy.toStandard(2000f)

    override fun serverTick(half: ComputingBlockEntity.ComputingHalf) {
        if (getCurrentErrorCode(half) == 0) {
            val inventory = half.inventory ?: return
            val removed = half.be.removeEnergyDirect(9f, LooseEnergy, WorldEnergyContext(half.be.world, half.be.pos, null, half.top.transform(Direction.UP, Direction.DOWN)))
            val code = generateErrorCode(inventory, removed)
            if (code > 0) {
                half.extra?.putInt("ErrorCode", code)
            }
        }

        EnergyTransaction()
            .apply { half.inventory?.slotsIn("fuels")?.also { includeAll(it.filter { (it.item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(it)) == true }.map { stack -> { half.be.extractEnergy(stack.item as StackEnergyHandler, it, LooseEnergy, WorldEnergyContext(half.be.world, half.be.pos, null, null)) { StackEnergyContext(stack) } } }) } }
            .include { half.be.requestEnergy(WorldEnergyContext(half.be.world, half.be.pos, null, null), it, LooseEnergy, *Direction.values().filterNot { it == half.top.transform(Direction.DOWN, Direction.UP) }.toTypedArray()) }
            .run(50f)
    }

    override fun createExtraData() = NbtCompound()

    fun power(half: ComputingBlockEntity.ComputingHalf, player: ServerPlayerEntity) : Boolean? {
        val sworld = half.be.world as? ServerWorld ?: return null
        val inventory = half.inventory ?: return null
        val power = half.rawEnergy ?: 0f

        val current = getCurrentErrorCode(half)
        if (current != null) {
            half.extra?.remove("Working")
            half.extra?.remove("ErrorCode")
            half.extra?.remove("Reading")
            half.be.markDirty()
            sworld.chunkManager.markForUpdate(half.be.pos)
            return true
        }

        val potential = generateErrorCode(inventory, power)
        if (potential != 0) {
            half.extra?.putInt("ErrorCode", potential)
            half.be.markDirty()
            sworld.chunkManager.markForUpdate(half.be.pos)
            return false
        }

        half.extra?.putBoolean("Working", true)
        half.be.markDirty()
        sworld.chunkManager.markForUpdate(half.be.pos)
        return true
    }

    fun getCurrentErrorCode(half: ComputingBlockEntity.ComputingHalf): Int? {
        val data = half.extra ?: return null
        val error = data.getInt("ErrorCode")
        if (error > 0) return error
        return data.getBoolean("Working").transform(0, null)
    }

    fun generateErrorCode(inventory: LCCInventory, power: Float) = when {
        power < 9f -> 1
        !inventory[0].isOf(LCCItems.cpu) -> 2
        inventory.list.subList(1,4).none { it.isOf(LCCItems.ram) } -> 2
        else -> 0
    }

    fun isReading(half: ComputingBlockEntity.ComputingHalf) = half.extra?.getBoolean("Reading") ?: false

    override fun initScreenHandler(handler: ComputingScreenHandler, slotAdder: (slot: Slot) -> Unit, half: ComputingBlockEntity.ComputingHalf, inv: LCCInventory, playerInv: PlayerInventory) {
        slotAdder(object : PredicatedSlot(inv, 0, 17, 27, { it.isOf(LCCItems.cpu) }) {
            override fun getMaxItemCount() = 1
        })
        handler.addSlots(inv, 41, 27, 4, 1, slotAdder, 1) { n, i, x, y ->
            object : PredicatedSlot(n, i, x, y, { it.isOf(LCCItems.ram) }) {
                override fun getMaxItemCount() = 1
            }
        }
        slotAdder(object : PredicatedSlot(inv, 5, 119, 27, { it.isOf(LCCItems.gpu) }) {
            override fun getMaxItemCount() = 1
        })
        slotAdder(object : PredicatedSlot(inv, 6, 143, 27, { (it.item as? DigitalMediumItem)?.medium == LCCDigitalMediums.m2 }) {
            override fun getMaxItemCount() = 1
        })

        handler.addSlots(inv, 68, 61, 3, 1, slotAdder, start = 7) { n, i, x, y -> PredicatedSlot(n, i, x, y, StackEnergyHandler::containsPower) }

        handler.addPlayerSlots(playerInv, 8, 96, slotAdder)
    }

    override fun createInventory() = super.createInventory()?.apply {
        addSegment("cpu", 1)
        addSegment("ram", 4)
        addSegment("gpu", 1)
        addSegment("m2", 1)
        addSegment("fuels", 3)
    }

    override fun createScreen(handler: ComputingScreenHandler, playerInv: PlayerInventory, text: Text) = ComputerScreen(handler, playerInv, text)

}