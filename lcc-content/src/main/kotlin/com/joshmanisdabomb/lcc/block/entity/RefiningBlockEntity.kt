package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCRecipeTypes
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyStorage
import com.joshmanisdabomb.lcc.extensions.NBT_FLOAT
import com.joshmanisdabomb.lcc.extensions.NBT_STRING
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import com.joshmanisdabomb.lcc.recipe.refining.RefiningRecipe
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import kotlin.math.min

class RefiningBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.refining, pos, state), NamedScreenHandlerFactory, SidedInventory, WorldEnergyStorage {

    val refiningBlock get() = cachedState.block as? RefiningBlock

    val inventory by lazy { RefiningInventory(refiningBlock!!).apply { addListener { this@RefiningBlockEntity.markDirty() } } }

    var currentRecipe: RefiningRecipe? = null
    var currentRecipeDelegate: Identifier? = null

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@RefiningBlockEntity.energyDisplay.first
            1 -> this@RefiningBlockEntity.energyDisplay.second
            2 -> this@RefiningBlockEntity.progress
            3 -> this@RefiningBlockEntity.boostDisplay.first
            4 -> this@RefiningBlockEntity.boostDisplay.second
            5 -> this@RefiningBlockEntity.maxProgress
            6 -> this@RefiningBlockEntity.maxBoostDisplay.first
            7 -> this@RefiningBlockEntity.maxBoostDisplay.second
            8 -> this@RefiningBlockEntity.icon
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@RefiningBlockEntity.energyDisplay.first = value
            1 -> this@RefiningBlockEntity.energyDisplay.second = value
            2 -> this@RefiningBlockEntity.progress = value
            3 -> this@RefiningBlockEntity.boostDisplay.first = value
            4 -> this@RefiningBlockEntity.boostDisplay.second = value
            5 -> this@RefiningBlockEntity.maxProgress = value
            6 -> this@RefiningBlockEntity.maxBoostDisplay.first = value
            7 -> this@RefiningBlockEntity.maxBoostDisplay.second = value
            8 -> this@RefiningBlockEntity.icon = value
            else -> Unit
        }

        override fun size() = 9
    }

    var customName: Text? = null

    private var progress = 0
    private var boost = 0f
    private var maxProgress = 0
    private var maxBoost = 0f
    private var working = false

    private val boostDisplay = DecimalTransport(::boost)
    private val maxBoostDisplay = DecimalTransport(::maxBoost)

    private var icon = -1

    protected val inputRange by lazy { (0 until (refiningBlock?.inputSlotCount ?: 6)).toList().toIntArray() }
    protected val outputRange by lazy { (refiningBlock?.run { inputSlotCount until outputSlotCount.plus(inputSlotCount) } ?: 6 until 12).toList().toIntArray() }
    protected val fuelRange by lazy { (refiningBlock?.run { inputSlotCount+outputSlotCount until fuelSlotCount.plus(outputSlotCount).plus(inputSlotCount) } ?: 6 until 12).toList().toIntArray() }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = refiningBlock?.createMenu(syncId, inv, inventory, player, propertyDelegate)

    override fun getDisplayName() = customName ?: refiningBlock?.defaultDisplayName ?: Text.translatable("container.lcc.refiner")

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)

        if (tag.contains("Energy", NBT_FLOAT)) rawEnergy = tag.getFloat("Energy")
        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))
        if (tag.contains("CurrentRecipe", NBT_STRING)) currentRecipeDelegate = Identifier.tryParse(tag.getString("CurrentRecipe"))
        working = tag.getBoolean("Working")
        progress = tag.getInt("Progress")
        boost = tag.getFloat("Boost")

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: NbtCompound) {
        super.writeNbt(tag)

        rawEnergy?.apply { tag.putFloat("Energy", this) }
        customName?.apply { tag.putString("CustomName", Text.Serializer.toJson(this)) }
        currentRecipe?.apply { tag.putString("CurrentRecipe", this.id.toString()) }
        tag.putBoolean("Working", working)
        tag.putInt("Progress", progress)
        tag.putFloat("Boost", boost)

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
        Direction.UP -> inputRange
        Direction.DOWN -> outputRange
        else -> fuelRange
    }
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = isValid(slot, stack)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = dir == Direction.DOWN

    override var rawEnergy: Float? = 0f
    override val rawEnergyMaximum get() = refiningBlock?.maxEnergy
    private var energy: Float
        get() = rawEnergy ?: 0f
        set(value) { rawEnergy = value }

    private val energyDisplay = DecimalTransport(::energy)

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    protected fun setWorkingRecipe(recipe: RefiningRecipe?, boost: (boost: Float) -> Float) {
        currentRecipe = recipe
        icon = recipe?.icon ?: -1
        progress = 0
        this.boost = min(boost(this.boost), recipe?.getMaxSpeedGainPerTick() ?: 0f)
        maxProgress = calculateMaxProgress(recipe)
        maxBoost = recipe?.getMaxSpeedGainPerTick() ?: 0f
        markDirty()
    }

    protected fun regress(recipe: RefiningRecipe, forget: Boolean = true) {
        progress = progress.minus(10).coerceAtLeast(0)
        boost = boost.times(0.99f).minus(0.012f).coerceAtLeast(0f)
        maxProgress = calculateMaxProgress(recipe)
        if (progress > 0 || boost > 0f) {
            markDirty()
        } else if (forget) {
            setWorkingRecipe(null) { 0f }
        }
    }

    protected fun progress(recipe: RefiningRecipe) {
        progress += 1
        boost = boost.plus(recipe.getSpeedGainPerTick()).coerceAtMost(recipe.getMaxSpeedGainPerTick())
        maxProgress = calculateMaxProgress(recipe)
        while (progress >= maxProgress) {
            progress -= maxProgress
            maxProgress = calculateMaxProgress(recipe)
            generate(recipe)
        }
        removeEnergyDirect(recipe.getEnergyPerTick(), LooseEnergy, WorldEnergyContext(world, pos, null, null))
        markDirty()
    }

    protected fun calculateMaxProgress(recipe: RefiningRecipe?) = recipe?.getSpeed()?.div(boost.div(100f).plus(1))?.toInt() ?: 0

    protected fun generate(recipe: RefiningRecipe) {
        val consumed = recipe.input(inventory) ?: return
        val stacks = recipe.generate(consumed, inventory, world?.random ?: return)
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
        val required = recipe.generateMaximum(inventory)
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
        const val energyPerTick = 100f

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
                val energy = entity.getEnergy(LooseEnergy, WorldEnergyContext(world, pos, null, null)) ?: 0f
                if (energy >= recipe.getEnergyPerTick() && entity.hasSpace(recipe)) {
                    entity.working = true
                    entity.progress(recipe)
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

            EnergyTransaction()
                .apply { entity.inventory.slotsIn("fuels")?.also { includeAll(it.filter { (it.item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(it)) == true }.map { stack -> { entity.extractEnergy(stack.item as StackEnergyHandler, it, LooseEnergy, WorldEnergyContext(world, pos, null, null)) { StackEnergyContext(stack) } } }) } }
                .include { entity.requestEnergy(WorldEnergyContext(world, pos, null, null), it, LooseEnergy, *Direction.values()) }
                .run(energyPerTick)
        }
    }

}