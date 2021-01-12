package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.energy.EnergyHandler
import com.joshmanisdabomb.lcc.energy.EnergyStorage
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.extensions.NBT_FLOAT
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import com.joshmanisdabomb.lcc.recipe.RefiningRecipe
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World
import kotlin.math.min

class RefiningBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.refining, pos, state), NamedScreenHandlerFactory, SidedInventory, EnergyStorage {

    val refiningBlock get() = cachedState.block as? RefiningBlock

    val inventory by lazy { RefiningInventory(refiningBlock!!).apply { addListener { this@RefiningBlockEntity.markDirty() } } }

    var currentRecipe: RefiningRecipe? = null

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@RefiningBlockEntity.energyDisplay
            1 -> this@RefiningBlockEntity.progress
            2 -> this@RefiningBlockEntity.boostDisplay
            3 -> this@RefiningBlockEntity.maxProgress
            4 -> this@RefiningBlockEntity.maxBoostDisplay
            5 -> this@RefiningBlockEntity.icon
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@RefiningBlockEntity.energyDisplay = value
            1 -> this@RefiningBlockEntity.progress = value
            2 -> this@RefiningBlockEntity.boostDisplay = value
            3 -> this@RefiningBlockEntity.maxProgress = value
            4 -> this@RefiningBlockEntity.maxBoostDisplay = value
            5 -> this@RefiningBlockEntity.icon = value
            else -> Unit
        }

        override fun size() = 6
    }

    var customName: Text? = null

    private var progress = 0
    private var boost = 0f
    private var maxProgress = 0
    private var maxBoost = 0f

    private var boostDisplay: Int
        get() = boost.times(1000f).toInt()
        set(value) { boost = value.div(1000f) }
    private var maxBoostDisplay: Int
        get() = maxBoost.times(1000f).toInt()
        set(value) { maxBoost = value.div(1000f) }

    private var icon = -1

    protected val inputRange by lazy { (0 until (refiningBlock?.inputSlotCount ?: 6)).toList().toIntArray() }
    protected val outputRange by lazy { (refiningBlock?.run { inputSlotCount until outputSlotCount.plus(inputSlotCount) } ?: 6 until 12).toList().toIntArray() }
    protected val fuelRange by lazy { (refiningBlock?.run { inputSlotCount+outputSlotCount until fuelSlotCount.plus(outputSlotCount).plus(inputSlotCount) } ?: 6 until 12).toList().toIntArray() }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = refiningBlock?.createMenu(syncId, inv, inventory, player, propertyDelegate)

    override fun getDisplayName() = customName ?: refiningBlock?.defaultDisplayName ?: TranslatableText("container.lcc.refiner")

    override fun fromTag(tag: CompoundTag) {
        super.fromTag(tag)

        if (tag.contains("Energy", NBT_FLOAT)) rawEnergy = tag.getFloat("Energy")
        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))
        if (tag.contains("CurrentRecipe", NBT_STRING)) setWorkingRecipe(world?.recipeManager?.get(Identifier.tryParse(tag.getString("CurrentRecipe")))?.orElse(null) as? RefiningRecipe) { 0f }

        inventory.apply { clear(); Inventories.fromTag(tag, inventory) }
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)

        rawEnergy?.apply { tag.putFloat("Energy", this) }
        if (customName != null) tag.putString("CustomName", Text.Serializer.toJson(customName))
        if (currentRecipe != null) tag.putString("CurrentRecipe", currentRecipe!!.id.toString())

        Inventories.toTag(tag, inventory.inventory)

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

    override fun getAvailableSlots(side: Direction) = when (side) {
        Direction.UP -> inputRange
        Direction.DOWN -> outputRange
        else -> fuelRange
    }
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = isValid(slot, stack)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = dir == Direction.DOWN

    override var rawEnergy: Float? = 0f
    override val rawEnergyBounds by lazy { 0f..(refiningBlock?.maxEnergy ?: 800f) }

    private var energyDisplay: Int
        get() = (rawEnergy ?: 0f).times(1000f).toInt()
        set(value) { rawEnergy = value.div(1000f) }

    override fun removeEnergy(amount: Float, unit: EnergyUnit, from: EnergyHandler?, world: BlockView?, home: BlockPos?, away: BlockPos?, side: Direction?) = 0f

    private fun setWorkingRecipe(recipe: RefiningRecipe?, boost: (boost: Float) -> Float) {
        currentRecipe = recipe
        icon = recipe?.icon ?: -1
        progress = 0
        this.boost = min(boost(this.boost), recipe?.maxGain ?: 0f)
        maxProgress = recipe?.ticks?.div(this.boost.plus(1))?.toInt() ?: 0
        maxBoost = recipe?.maxGain ?: 0f
    }

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RefiningBlockEntity) {
            Direction.values().forEach {
                EnergyHandler.worldExtract(entity, world, pos, state, it, 50f, LooseEnergy)
            }

            val recipe = world.recipeManager.getFirstMatch(LCCRecipeTypes.refining, entity.inventory, world).orElse(null)
            if (recipe == null) { //cool down and possibly forget current recipe
                entity.currentRecipe?.also {
                    entity.progress = entity.progress.minus(10).coerceAtLeast(0)
                    entity.boost = entity.boost.times(0.96f).minus(0.005f).coerceAtLeast(0f)
                    entity.maxProgress = it.ticks.div(entity.boost.div(100f).plus(1)).toInt()
                    if (entity.progress <= 0 && entity.boost <= 0f) {
                        entity.setWorkingRecipe(null) { 0f }
                    }
                }
            } else if (recipe == entity.currentRecipe) { //try use current recipe
                val energy = entity.getEnergy(LooseEnergy, null)
                if (energy >= recipe.energy) {
                    entity.setEnergy(energy - recipe.energy, LooseEnergy, null, world, pos, null, null)
                    entity.progress += 1
                    entity.boost = entity.boost.plus(recipe.gain).coerceAtMost(recipe.maxGain)
                    entity.maxProgress = recipe.ticks.div(entity.boost.div(100f).plus(1)).toInt()
                    while (entity.progress >= entity.maxProgress) {
                        entity.progress -= entity.maxProgress
                        entity.maxProgress = recipe.ticks.div(entity.boost.div(100f).plus(1)).toInt()
                    }
                } else {
                    entity.progress = entity.progress.minus(10).coerceAtLeast(0)
                    entity.boost = entity.boost.times(0.96f).minus(0.005f).coerceAtLeast(0f)
                    entity.maxProgress = recipe.ticks.div(entity.boost.div(100f).plus(1)).toInt()
                }
            } else { //changing current recipe
                entity.setWorkingRecipe(recipe) { it.times(0.5f) }
            }
        }

        fun isValidFuel(stack: ItemStack) = stack.isOf(Items.REDSTONE)
    }

}