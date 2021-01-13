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
    var currentRecipeDelegate: Identifier? = null

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
    private var working = false

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
        if (tag.contains("CurrentRecipe", NBT_STRING)) currentRecipeDelegate = Identifier.tryParse(tag.getString("CurrentRecipe"))
        working = tag.getBoolean("Working")
        progress = tag.getInt("Progress")
        boost = tag.getFloat("Boost")

        inventory.apply { clear(); Inventories.fromTag(tag, inventory) }
    }

    override fun toTag(tag: CompoundTag): CompoundTag {
        super.toTag(tag)

        rawEnergy?.apply { tag.putFloat("Energy", this) }
        customName?.apply { tag.putString("CustomName", Text.Serializer.toJson(this)) }
        currentRecipe?.apply { tag.putString("CurrentRecipe", this.id.toString()) }
        tag.putBoolean("Working", working)
        tag.putInt("Progress", progress)
        tag.putFloat("Boost", boost)

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

    protected fun setWorkingRecipe(recipe: RefiningRecipe?, boost: (boost: Float) -> Float) {
        currentRecipe = recipe
        icon = recipe?.icon ?: -1
        progress = 0
        this.boost = min(boost(this.boost), recipe?.maxGain ?: 0f)
        maxProgress = calculateMaxProgress(recipe)
        maxBoost = recipe?.maxGain ?: 0f
        markDirty()
    }

    protected fun regress(recipe: RefiningRecipe, forget: Boolean = true) {
        progress = progress.minus(10).coerceAtLeast(0)
        boost = boost.times(0.96f).minus(0.005f).coerceAtLeast(0f)
        maxProgress = calculateMaxProgress(recipe)
        if (progress > 0 || boost > 0f) {
            markDirty()
        } else if (forget) {
            setWorkingRecipe(null) { 0f }
        }
    }

    protected fun progress(recipe: RefiningRecipe, energy: Float = getEnergy(LooseEnergy, null)) {
        progress += 1
        boost = boost.plus(recipe.gain).coerceAtMost(recipe.maxGain)
        maxProgress = calculateMaxProgress(recipe)
        while (progress >= maxProgress) {
            progress -= maxProgress
            maxProgress = calculateMaxProgress(recipe)
            generate(recipe)
        }
        setEnergy(energy - recipe.energy, LooseEnergy, null, world, pos, null, null)
        markDirty()
    }

    private fun calculateMaxProgress(recipe: RefiningRecipe?) = recipe?.ticks?.div(boost.div(100f).plus(1))?.toInt() ?: 0

    protected fun generate(recipe: RefiningRecipe) {
        recipe.input(inventory)

        val stacks = recipe.generate(world?.random ?: return)
        next@for (s in stacks) {
            val stack = s.copy()
            for (i in outputRange) {
                val s2 = inventory.getStack(i)
                if (s2.isEmpty) {
                    inventory.setStack(i, stack.copy().apply { count = stack.count.coerceAtMost(stack.maxCount) })
                    stack.decrement(stack.maxCount)
                } else if (ItemStack.canCombine(stack, s2)) {
                    val remaining = s2.maxCount - s2.count
                    val moving = stack.count.coerceAtMost(remaining)
                    s2.count += moving
                    stack.decrement(moving)
                }
                if (stack.isEmpty || stack.count <= 0) continue@next
            }
        }
    }

    protected fun hasSpace(recipe: RefiningRecipe): Boolean {
        val required = recipe.maximum
        next@for (s in required) {
            val stack = s.copy()
            for (i in outputRange) {
                val s2 = inventory.getStack(i)
                if (s2.isEmpty) {
                    stack.decrement(stack.maxCount)
                } else if (ItemStack.canCombine(stack, s2)) {
                    stack.decrement(s2.maxCount - s2.count)
                }
                if (stack.isEmpty || stack.count <= 0) continue@next
            }
            return false
        }
        return true
    }

    companion object {
        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: RefiningBlockEntity) {
            entity.currentRecipeDelegate?.also {
                val recipe = world.recipeManager[it].orElse(null) as? RefiningRecipe
                recipe.apply {
                    val boost = entity.boost
                    entity.setWorkingRecipe(recipe) { boost }
                    entity.maxProgress = entity.calculateMaxProgress(recipe)
                }
                entity.currentRecipeDelegate = null
            }

            val working = entity.working
            val recipe = world.recipeManager.getFirstMatch(LCCRecipeTypes.refining, entity.inventory, world).orElse(null)
            if (recipe == null) { //cool down and possibly forget current recipe
                entity.working = false
                entity.currentRecipe?.also {
                    entity.regress(it)
                }
            } else if (recipe == entity.currentRecipe) { //try use current recipe
                val energy = entity.getEnergy(LooseEnergy, null)
                if (energy >= recipe.energy && entity.hasSpace(recipe)) {
                    entity.working = true
                    entity.progress(recipe, energy)
                } else {
                    entity.working = false
                    entity.regress(recipe, false)
                }
            } else { //changing current recipe
                entity.working = false
                entity.setWorkingRecipe(recipe) { it.times(0.5f) }
            }
            if (working != entity.working) {
                if (entity.working) world.setBlockState(pos, state.with(entity.refiningBlock!!.processes, recipe?.state ?: RefiningBlock.RefiningProcess.NONE), 3)
                else world.setBlockState(pos, state.with(entity.refiningBlock!!.processes, RefiningBlock.RefiningProcess.NONE), 3)
            }

            Direction.values().forEach {
                EnergyHandler.worldExtract(entity, world, pos, state, it, 50f, LooseEnergy)
            }
        }

        fun isValidFuel(stack: ItemStack) = stack.isOf(Items.REDSTONE)
    }

}