package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.abstracts.computing.module.ComputerModule
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRegistries
import com.joshmanisdabomb.lcc.extensions.NBT_COMPOUND
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.recipe.refining.special.PolymerRefiningRecipe.Companion.state
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.enums.SlabType
import net.minecraft.inventory.Inventories.writeNbt
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextParameters
import net.minecraft.loot.context.LootContextTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.server.world.ServerWorld
import net.minecraft.tag.RequiredTagListRegistry.forEach
import net.minecraft.util.Identifier
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

    data class ComputingHalf(var module: ComputerModule, var direction: Direction, var color: Int, val top: Boolean) {

        constructor(nbt: NbtCompound, top: Boolean) : this(LCCRegistries.computer_modules[Identifier(nbt.getString("Module"))], Direction.fromHorizontal(nbt.getByte("Direction").toInt()), nbt.getInt("Color"), top)

        fun writeNbt(nbt: NbtCompound) {
            nbt.putString("Module", module.id.toString())
            nbt.putByte("Direction", direction.horizontal.toByte())
            nbt.putInt("Color", color)
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

    }

}