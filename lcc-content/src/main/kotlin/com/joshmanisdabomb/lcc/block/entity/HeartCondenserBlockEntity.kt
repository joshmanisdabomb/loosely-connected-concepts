package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.HeartCondenserBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.inventory.container.HeartCondenserScreenHandler
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.PropertyDelegate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class HeartCondenserBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.heart_condenser, pos, state), ExtendedScreenHandlerFactory, SidedInventory {

    val inventory by lazy { object : LCCInventory(3) {

        override fun isValid(slot: Int, stack: ItemStack) = when (slot) {
            0 -> stack.isIn(LCCItemTags.hearts)
            1 -> stack.isIn(LCCItemTags.heart_condenser_fuel)
            else -> false
        }

    }.apply {
        addListener { this@HeartCondenserBlockEntity.markDirty() }
        addSegment("input", 1)
        addSegment("fuel", 1)
        addSegment("output", 1)
    } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> burn
            1 -> burnMax
            2 -> cook
            3 -> cookMax
            4 -> health
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> burn = value
            1 -> burnMax = value
            2 -> cook = value
            3 -> cookMax = value
            4 -> health = value
            else -> Unit
        }

        override fun size() = 5
    }

    private var burn = 0
    private var burnMax = 0
    private var cook = 0
    private var cookMax = 0
    private var health = 0

    var customName: Text? = null

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = HeartCondenserScreenHandler(syncId, inv, inventory, propertyDelegate)

    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
        buf.writeBlockPos(pos)
    }

    override fun getDisplayName() = customName ?: Text.translatable("container.lcc.heart_condenser")

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)

        burn = tag.getByte("Burn").toInt()
        burnMax = tag.getByte("BurnMax").toInt()
        cook = tag.getByte("Cook").toInt()
        cookMax = tag.getByte("CookMax").toInt()
        health = tag.getByte("Health").toInt()

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)

        tag.putByte("Burn", burn.toByte())
        tag.putByte("BurnMax", burnMax.toByte())
        tag.putByte("Cook", cook.toByte())
        tag.putByte("CookMax", cookMax.toByte())
        tag.putByte("Health", health.toByte())

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

    override fun getAvailableSlots(side: Direction) = when (side) {
        Direction.UP -> inventory.getSegmentSlots("input")
        Direction.DOWN -> inventory.getSegmentSlots("output")
        else -> inventory.getSegmentSlots("fuel")
    }
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = isValid(slot, stack)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = dir == Direction.DOWN

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: HeartCondenserBlockEntity) {
            val heart = entity.inventory[0]
            if (heart.isEmpty) {
                entity.cook = 0
                entity.markDirty()
            } else {
                val output = entity.inventory[2]
                val recipe = world.recipeManager.getFirstMatch(LCCRecipeTypes.heart_condenser, entity.inventory, world).orElse(null)
                val currType = state.get(HeartCondenserBlock.type)
                val hp = recipe?.getHealth(heart)
                if (hp == null || (!output.isEmpty && !output.isOf(recipe.output.item)) || (currType != HeartCondenserBlock.HeartState.NONE && currType != recipe.type)) {
                    entity.cook = 0
                    entity.markDirty()
                } else if (entity.burn > 0) {
                    entity.cookMax = hp.times(5)
                    entity.cook += 1
                    if (entity.cook >= entity.cookMax) {
                        heart.decrement(1)
                        entity.cook -= entity.cookMax
                        entity.health += hp
                        if (currType == HeartCondenserBlock.HeartState.NONE) world.setBlockState(pos, state.with(HeartCondenserBlock.type, recipe.type))
                        while (entity.health >= 20) {
                            if (entity.inventory[2].isEmpty) {
                                entity.inventory[2] = recipe.output.copy()
                            } else {
                                output.increment(1)
                            }
                            entity.health -= 20
                            if (entity.health <= 0) {
                                world.setBlockState(pos, state.with(HeartCondenserBlock.type, HeartCondenserBlock.HeartState.NONE))
                            }
                        }
                    }
                } else {
                    val fuel = entity.inventory[1]
                    if (fuel.isIn(LCCItemTags.heart_condenser_fuel) && !heart.isEmpty) {
                        fuel.decrement(1)
                        entity.burnMax = 100
                        entity.burn = entity.burnMax + 1
                    }
                }
            }
            if (entity.burn > 0) {
                entity.burn -= 1
                entity.markDirty()
            } else {
                entity.cook = entity.cook.minus(1).coerceAtLeast(0)
            }
        }
    }

}
