package com.joshmanisdabomb.lcc.abstracts.computing.module

import com.joshmanisdabomb.lcc.block.entity.ComputingBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.prefix
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.recipe.refining.special.PolymerRefiningRecipe.Companion.state
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ComputerModule {

    val id get() = LCCRegistries.computer_modules.getKey(this).orElseThrow(::RuntimeException).value

    val lootTableId get() = id.prefix("lcc/computer_module/", "")

    abstract val expectedInventorySize: Int
    open val rawEnergyMaximum: Float? = null

    open fun onUse(half: ComputingBlockEntity.ComputingHalf, state: BlockState, player: PlayerEntity, hand: Hand, hit: BlockHitResult) : ActionResult? {
        player.openHandledScreen(half)
        return ActionResult.SUCCESS
    }

    open fun serverTick(half: ComputingBlockEntity.ComputingHalf) = Unit

    open fun createInventory() = if (expectedInventorySize > 0) LCCInventory(expectedInventorySize) else null

    abstract fun initScreenHandler(handler: ComputingScreenHandler, slotAdder: (slot: Slot) -> Unit, half: ComputingBlockEntity.ComputingHalf, inv: LCCInventory, playerInv: PlayerInventory)

    @Environment(EnvType.CLIENT)
    abstract fun createScreen(handler: ComputingScreenHandler, playerInv: PlayerInventory, text: Text) : HandledScreen<ComputingScreenHandler>?

}