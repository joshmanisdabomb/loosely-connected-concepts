package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.abstracts.computing.DiskInfo
import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.prefix
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.network.BlockNetwork
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos

abstract class ComputerModule {

    val id get() = LCCRegistries.computer_modules.getKey(this).orElseThrow(::RuntimeException).value

    val lootTableId get() = id.prefix("lcc/computer_module/", "")

    abstract val expectedInventorySize: Int
    open val rawEnergyMaximum: Float? = null

    open fun onUse(half: ComputingBlockEntity.ComputingHalf, state: BlockState, player: PlayerEntity, hand: Hand, hit: BlockHitResult) : ActionResult? {
        player.openHandledScreen(half)
        return ActionResult.SUCCESS
    }

    open fun clientTick(half: ComputingBlockEntity.ComputingHalf) = Unit

    open fun serverTick(half: ComputingBlockEntity.ComputingHalf) = Unit

    open fun getInternalDisks(inv: LCCInventory): Set<DiskInfo> = emptySet()

    open fun createExtraData(): NbtCompound? = null

    open fun onUpdateNetwork(half: ComputingBlockEntity.ComputingHalf, network: BlockNetwork<Pair<BlockPos, Boolean?>>.NetworkResult) = Unit

    open fun getNetworkNodeTags(half: ComputingBlockEntity.ComputingHalf): Set<String> {
        val inv = half.inventory ?: return emptySet()
        val set = mutableSetOf<String>()
        for (disk in getInternalDisks(inv)) {
            val id = disk.id
            if (id != null) {
                set.add("disk-$id")
            } else {
                set.add("disk-null")
            }
            for (partition in disk.partitions) {
                val pid = partition.id
                if (pid != null) {
                    set.add("diskpart-$pid")
                } else {
                    set.add("diskpart-null")
                }
            }
        }
        return set
    }

    open fun createInventory() = if (expectedInventorySize > 0) LCCInventory(expectedInventorySize) else null

    abstract fun initScreenHandler(handler: ComputingScreenHandler, slotAdder: (slot: Slot) -> Unit, half: ComputingBlockEntity.ComputingHalf, inv: LCCInventory, playerInv: PlayerInventory)

    @Environment(EnvType.CLIENT)
    abstract fun createScreen(handler: ComputingScreenHandler, playerInv: PlayerInventory, text: Text) : HandledScreen<ComputingScreenHandler>?

}