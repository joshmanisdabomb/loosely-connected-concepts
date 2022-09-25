package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class EnhancingChamberBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.enhancing_chamber, pos, state), SidedInventory {

    val inventory = LCCInventory(2)

    fun enhance(pyre: ItemStack, player: PlayerEntity): Boolean {
        inventory.setStack(1, pyre)
        val recipe = world?.recipeManager?.getFirstMatch(LCCRecipeTypes.enhancing, inventory, world)?.orElse(null) ?: return false
        val output = recipe.craft(inventory)
        if (player.isSurvival) pyre.decrement(1)
        inventory.setStack(0, output)
        inventory.removeStack(1)
        markDirty()
        return true
    }

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)
        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)
        inventory.removeStack(1)
        Inventories.writeNbt(tag, inventory.list)
    }

    override fun toUpdatePacket() = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt() = createNbt()

    override fun clear() = inventory.clear()
    override fun size() = inventory.size()
    override fun isEmpty() = inventory.isEmpty
    override fun getStack(slot: Int) = inventory.getStack(slot)
    override fun removeStack(slot: Int, amount: Int) = inventory.removeStack(slot, amount)
    override fun removeStack(slot: Int) = inventory.removeStack(slot)
    override fun setStack(slot: Int, stack: ItemStack) = inventory.setStack(slot, stack)
    override fun canPlayerUse(player: PlayerEntity) = inventory.canPlayerUse(player)

    override fun isValid(slot: Int, stack: ItemStack) = inventory.isValid(slot, stack)

    override fun getAvailableSlots(side: Direction) = intArrayOf(0)

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = slot == 0
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = slot == 0 && dir == Direction.DOWN

}
