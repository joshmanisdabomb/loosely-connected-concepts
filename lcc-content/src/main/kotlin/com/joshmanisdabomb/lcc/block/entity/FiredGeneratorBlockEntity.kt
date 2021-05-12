package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.FiredGeneratorBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.lib.inventory.LCCInventory
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.predicate.entity.EntityPredicates
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.state.property.Properties.LIT
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import kotlin.math.roundToInt

class FiredGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.generator, pos, state), NamedScreenHandlerFactory, SidedInventory {

    val generatorBlock get() = cachedState.block as? FiredGeneratorBlock

    val inventory by lazy { object : LCCInventory(generatorBlock!!.slots) {

        override fun isValid(slot: Int, stack: ItemStack): Boolean {
            return generatorBlock!!.getBurnTime(stack) != null
        }

    }.apply { addListener { this@FiredGeneratorBlockEntity.markDirty() } } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@FiredGeneratorBlockEntity.burn
            1 -> this@FiredGeneratorBlockEntity.maxBurn
            2 -> this@FiredGeneratorBlockEntity.outputDisplay.first
            3 -> this@FiredGeneratorBlockEntity.outputDisplay.second
            4 -> this@FiredGeneratorBlockEntity.outputCeilDisplay.first
            5 -> this@FiredGeneratorBlockEntity.outputCeilDisplay.second
            6 -> this@FiredGeneratorBlockEntity.waterLevel
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@FiredGeneratorBlockEntity.burn = value
            1 -> this@FiredGeneratorBlockEntity.maxBurn = value
            2 -> this@FiredGeneratorBlockEntity.outputDisplay.first = value
            3 -> this@FiredGeneratorBlockEntity.outputDisplay.second = value
            4 -> this@FiredGeneratorBlockEntity.outputCeilDisplay.first = value
            5 -> this@FiredGeneratorBlockEntity.outputCeilDisplay.second = value
            6 -> this@FiredGeneratorBlockEntity.waterLevel = value
            else -> Unit
        }

        override fun size() = 7
    }

    private var burn = 0
    private var maxBurn = 0
    private var output = 0f
    private var outputCeil = 0f
    private var waterLevel = 0

    private var working = false

    val steam get() = output

    var customName: Text? = null

    private val outputDisplay = DecimalTransport(::output)
    private val outputCeilDisplay = DecimalTransport(::outputCeil)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = generatorBlock!!.createMenu(syncId, inv, inventory, player, propertyDelegate)

    override fun getDisplayName() = customName ?: generatorBlock?.defaultDisplayName

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)

        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        burn = tag.getInt("Burn")
        maxBurn = tag.getInt("MaxBurn")
        output = tag.getFloat("Output")
        outputCeil = tag.getFloat("OutputCeiling")

        working = tag.getBoolean("Working")

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: NbtCompound): NbtCompound {
        super.writeNbt(tag)

        customName?.apply { tag.putString("CustomName", Text.Serializer.toJson(this)) }

        tag.putInt("Burn", burn)
        tag.putInt("MaxBurn", burn)
        tag.putFloat("Output", output)
        tag.putFloat("OutputCeiling", outputCeil)

        tag.putBoolean("Working", working)

        Inventories.writeNbt(tag, inventory.list)

        return tag
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
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = !isValid(slot, getStack(slot))

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: FiredGeneratorBlockEntity) {
            val working = entity.working
            entity.waterLevel = entity.generatorBlock!!.getWaterMultiplier(world, pos, state).times(3f).roundToInt()

            if (entity.burn > 0) {
                entity.working = true
                val ceiling = entity.outputCeil * entity.waterLevel.div(3f)
                entity.output = entity.output.minus(ceiling).times(0.987f).plus(ceiling).plus(0.005f).coerceAtMost(ceiling)
                entity.burn--
            } else {
                entity.working = false
                for ((k, v) in entity.inventory.withIndex()) {
                    if (v.isEmpty) continue;
                    val stack = v.copy()
                    val burn = entity.generatorBlock!!.getBurnTime(v) ?: continue
                    v.decrement(1)
                    if (v.isEmpty) entity.inventory.setStack(k, stack.item?.recipeRemainder?.defaultStack ?: ItemStack.EMPTY)
                    entity.maxBurn = burn
                    entity.burn += entity.maxBurn
                    entity.outputCeil = entity.generatorBlock!!.getSteam(stack)
                    entity.working = true
                    break
                }
                entity.output = entity.output.times(0.99f).minus(0.06f).coerceAtLeast(0f)
            }

            if (working != entity.working) world.setBlockState(pos, state.with(LIT, entity.working), 3)

            if (entity.working && entity.waterLevel > 0) {
                world.getEntitiesByClass(LivingEntity::class.java, Box(pos).offset(0.0, 1.0, 0.0).contract(0.125), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and { !it.isFireImmune }).forEach {
                    it.damage(LCCDamage.boiled, 3.0F)
                }
            }
        }
    }

}