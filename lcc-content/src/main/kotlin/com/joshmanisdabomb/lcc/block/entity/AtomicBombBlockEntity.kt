package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.AtomicBombInventory
import com.joshmanisdabomb.lcc.inventory.container.AtomicBombScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class AtomicBombBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.atomic_bomb, pos, state), ExtendedScreenHandlerFactory, SidedInventory {

    val inventory by lazy { AtomicBombInventory().apply { addListener { this@AtomicBombBlockEntity.markDirty() } } }
    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = AtomicBombScreenHandler(syncId, inv, inventory)

    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
        buf.writeBlockPos(pos)
    }

    override fun getDisplayName() = customName ?: Text.translatable("container.lcc.atomic_bomb")

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)

        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    public override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)

        if (customName != null) tag.putString("CustomName", Text.Serializer.toJson(customName))

        Inventories.writeNbt(tag, inventory.list)
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
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = true
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = true

    fun detonate(by: LivingEntity?): Boolean {
        val world = world ?: return false
        if (world.isClient || !inventory.canDetonate) return false
        val facing = cachedState[Properties.HORIZONTAL_FACING]

        AtomicBombEntity(world, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, facing, this, by).also(world::spawnEntity)
        markRemoved()

        world.setBlockState(pos.offset(facing), Blocks.AIR.defaultState, 18)
        world.setBlockState(pos.offset(facing.opposite), Blocks.AIR.defaultState, 18)
        world.setBlockState(pos, Blocks.AIR.defaultState, 18)
        return true
    }

}
