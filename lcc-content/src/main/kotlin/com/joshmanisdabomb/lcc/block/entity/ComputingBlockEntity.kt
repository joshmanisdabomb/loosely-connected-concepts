package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.inventory.container.ComputingScreenHandler
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.enums.SlabType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class ComputingBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.computing, pos, state) {

    private var top: ComputingHalf? = null
    private var bottom: ComputingHalf? = null

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        if (nbt.contains("Top", NBT_COMPOUND)) top = ComputingHalf(nbt.getCompound("Top"), true)
        if (nbt.contains("Bottom", NBT_COMPOUND)) bottom = ComputingHalf(nbt.getCompound("Bottom"), false)
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
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: ComputingBlockEntity) {
            /*println(entity.top)
            println(entity.bottom)*/
        }
    }

    inner class ComputingHalf(var module: ComputerModule, var direction: Direction, var color: Int, val top: Boolean) {

        val inventory = module.createInventory()?.apply { addListener { this@ComputingBlockEntity.markDirty() } }

        var customName: Text? = null

        constructor(nbt: NbtCompound, top: Boolean) : this(LCCRegistries.computer_modules[Identifier(nbt.getString("Module"))], Direction.fromHorizontal(nbt.getByte("Direction").toInt()), nbt.getInt("Color"), top) {
            if (nbt.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(nbt.getString("CustomName"))
            inventory?.apply { clear(); Inventories.readNbt(nbt, list) }
        }

        fun writeNbt(nbt: NbtCompound) {
            nbt.putString("Module", module.id.toString())
            nbt.putByte("Direction", direction.horizontal.toByte())
            nbt.putInt("Color", color)
            if (customName != null) nbt.putString("CustomName", Text.Serializer.toJson(customName))
            inventory?.apply { Inventories.writeNbt(nbt, list) }
        }

        fun createScreenHandlerFactory(be: ComputingBlockEntity) = object : ExtendedScreenHandlerFactory {
            override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = ComputingScreenHandler(syncId, inv).initHalf(be, this@ComputingHalf)

            override fun getDisplayName() = customName ?: TranslatableText("container.lcc.${module.id.path}")

            override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
                buf.writeBlockPos(be.pos)
                buf.writeBoolean(top)
            }
        }

        fun drop(world: ServerWorld, pos: BlockPos) {
            world.server.lootManager.getTable(module.lootTableId).generateLoot(LootContext.Builder(world).random(world.random).build(LootContextTypes.EMPTY)).forEach {
                Block.dropStack(world, pos, it)
            }
        }

        fun connectsTo(other: ComputingHalf) = this.color == other.color

        fun connectsAbove(be: ComputingBlockEntity): Boolean {
            if (top) {
                val above = (be.world?.getBlockEntity(be.pos.up()) as? ComputingBlockEntity)?.getHalf(false) ?: return false
                return connectsTo(above)
            } else {
                val above = be.getHalf(true) ?: return false
                return connectsTo(above)
            }
        }

        fun connectsBelow(be: ComputingBlockEntity): Boolean {
            if (top) {
                val below = be.getHalf(false) ?: return false
                return connectsTo(below)
            } else {
                val below = (be.world?.getBlockEntity(be.pos.down()) as? ComputingBlockEntity)?.getHalf(true) ?: return false
                return connectsTo(below)
            }
        }

        fun onUse(be: ComputingBlockEntity, state: BlockState, player: PlayerEntity, hand: Hand, hit: BlockHitResult) = module.onUse(be, this, state, player, hand, hit)

    }

}