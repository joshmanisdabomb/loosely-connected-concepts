package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult

class CasingComputerModule : ComputerModule() {

    override val expectedInventorySize = 0

    override fun onUse(be: ComputingBlockEntity, half: ComputingBlockEntity.ComputingHalf, state: BlockState, player: PlayerEntity, hand: Hand, hit: BlockHitResult) = ActionResult.PASS

    override fun initScreenHandler(handler: ComputingScreenHandler, slotAdder: (slot: Slot) -> Unit, half: ComputingBlockEntity.ComputingHalf, inv: LCCInventory, playerInv: PlayerInventory) = Unit

    override fun createScreen(handler: ComputingScreenHandler, playerInv: PlayerInventory, text: Text) = null

}