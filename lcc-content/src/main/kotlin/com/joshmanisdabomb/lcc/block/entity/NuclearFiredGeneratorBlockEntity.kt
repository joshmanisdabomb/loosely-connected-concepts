package com.joshmanisdabomb.lcc.block.entity

import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.energy.EnergyTransaction
import com.joshmanisdabomb.lcc.energy.EnergyUnit
import com.joshmanisdabomb.lcc.energy.LooseEnergy
import com.joshmanisdabomb.lcc.energy.base.EnergyHandler
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyContext
import com.joshmanisdabomb.lcc.energy.stack.StackEnergyHandler
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyContext
import com.joshmanisdabomb.lcc.energy.world.WorldEnergyStorage
import com.joshmanisdabomb.lcc.extensions.*
import com.joshmanisdabomb.lcc.inventory.LCCInventory
import com.joshmanisdabomb.lcc.inventory.container.NuclearFiredGeneratorScreenHandler
import com.joshmanisdabomb.lcc.utils.DecimalTransport
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.PacketByteBuf
import net.minecraft.potion.PotionUtil
import net.minecraft.potion.Potions
import net.minecraft.screen.PropertyDelegate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.state.property.Properties.LIT
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt

class NuclearFiredGeneratorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(LCCBlockEntities.nuclear_generator, pos, state), ExtendedScreenHandlerFactory, SidedInventory, WorldEnergyStorage {

    val inventory by lazy { object : LCCInventory(5) {

        override fun isValid(slot: Int, stack: ItemStack) = when (slot) {
            0 -> stack.isOf(Items.TNT)
            1 -> stack.isOf(LCCItems.enriched_uranium_nugget)
            2 -> stack.isOf(LCCItems.nuclear_fuel)
            3 -> getCoolantValue(stack) != null
            else -> StackEnergyHandler.containsPower(stack)
        }

    }.apply {
        addSegment("starter", 1)
        addSegment("startup", 1)
        addSegment("fuel", 1)
        addSegment("coolant", 1)
        addSegment("power", 1)
        addListener { this@NuclearFiredGeneratorBlockEntity.markDirty() }
    } }

    val propertyDelegate = object : PropertyDelegate {
        override fun get(index: Int) = when (index) {
            0 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.first
            1 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.second
            2 -> this@NuclearFiredGeneratorBlockEntity.outputDisplay.first
            3 -> this@NuclearFiredGeneratorBlockEntity.outputDisplay.second
            4 -> this@NuclearFiredGeneratorBlockEntity.fuelDisplay.first
            5 -> this@NuclearFiredGeneratorBlockEntity.fuelDisplay.second
            6 -> this@NuclearFiredGeneratorBlockEntity.coolantDisplay.first
            7 -> this@NuclearFiredGeneratorBlockEntity.coolantDisplay.second
            8 -> this@NuclearFiredGeneratorBlockEntity.waterLevel
            else -> 0
        }

        override fun set(index: Int, value: Int) = when (index) {
            0 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.first = value
            1 -> this@NuclearFiredGeneratorBlockEntity.energyDisplay.second = value
            2 -> this@NuclearFiredGeneratorBlockEntity.outputDisplay.first = value
            3 -> this@NuclearFiredGeneratorBlockEntity.outputDisplay.second = value
            4 -> this@NuclearFiredGeneratorBlockEntity.fuelDisplay.first = value
            5 -> this@NuclearFiredGeneratorBlockEntity.fuelDisplay.second = value
            6 -> this@NuclearFiredGeneratorBlockEntity.coolantDisplay.first = value
            7 -> this@NuclearFiredGeneratorBlockEntity.coolantDisplay.second = value
            8 -> this@NuclearFiredGeneratorBlockEntity.waterLevel = value
            else -> Unit
        }

        override fun size() = 9
    }

    var customName: Text? = null

    var working = false
    var output = 0f
    var fuel = 0f
    var coolant = 0f
    private var waterLevel = 0

    private val outputDisplay = DecimalTransport(::output)
    private val fuelDisplay = DecimalTransport(::fuel)
    private val coolantDisplay = DecimalTransport(::coolant)

    val steam get() = output * waterLevel.div(3f)

    override var rawEnergy: Float? = 0f
    override val rawEnergyMaximum get() = LooseEnergy.toStandard(NuclearFiredGeneratorBlockEntity.maxEnergy)
    private var energy: Float
        get() = rawEnergy ?: 0f
        set(value) { rawEnergy = value }

    private val energyDisplay = DecimalTransport(::energy)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = NuclearFiredGeneratorScreenHandler(syncId, inv, inventory, propertyDelegate)

    override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) {
        buf.writeBlockPos(pos)
    }

    override fun getDisplayName() = customName ?: TranslatableText("container.lcc.${LCCBlocks[LCCBlocks.nuclear_generator].name}")

    override fun readNbt(tag: CompoundTag) {
        super.readNbt(tag)

        if (tag.contains("Energy", NBT_FLOAT)) rawEnergy = tag.getFloat("Energy")
        output = tag.getFloat("Output")
        fuel = tag.getFloat("Fuel")
        coolant = tag.getFloat("Coolant")
        if (tag.contains("CustomName", NBT_STRING)) customName = Text.Serializer.fromJson(tag.getString("CustomName"))

        working = tag.getBoolean("Working")

        inventory.apply { clear(); Inventories.readNbt(tag, list) }
    }

    override fun writeNbt(tag: CompoundTag): CompoundTag {
        super.writeNbt(tag)

        rawEnergy?.apply { tag.putFloat("Energy", this) }
        tag.putFloat("Output", output)
        tag.putFloat("Fuel", fuel)
        tag.putFloat("Coolant", coolant)
        customName?.apply { tag.putString("CustomName", Text.Serializer.toJson(this)) }

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
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = isValid(slot, stack)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = false

    fun activate(by: LivingEntity?): Boolean {
        val world = world ?: return false
        if (world.isClient) return false
        if (working) return false
        if (!canStartup(getEnergy(LooseEnergy, WorldEnergyContext(world, pos, null, null)) ?: 0f, inventory)) return false
        inventory[0].decrement(1)
        inventory[1].decrement(1)
        removeEnergyDirect(maxEnergy, LooseEnergy, WorldEnergyContext(world, pos, null, null))
        world.setBlockState(pos, world.getBlockState(pos).with(LIT, true))
        working = true
        return true
    }

    fun meltdown(): Boolean {
        val world = world ?: return false
        if (world.isClient) return false
        if (!working) return false
        world.setBlockState(pos, LCCBlocks.failing_nuclear_generator.defaultState.with(HORIZONTAL_FACING, cachedState.get(HORIZONTAL_FACING)))
        return true
    }

    override fun removeEnergy(target: EnergyHandler<*>, amount: Float, unit: EnergyUnit, context: WorldEnergyContext) = 0f

    companion object {
        const val maxEnergy = 200f
        const val maxSafeOutput = 200f
        const val maxChanceOutput = 400f

        const val maxFuel = 16f
        const val maxCoolant = 256f

        const val fuelCoefficient = 0.13f
        const val coolantCoefficient = 11.0f

        fun serverTick(world: World, pos: BlockPos, state: BlockState, entity: NuclearFiredGeneratorBlockEntity) {
            val working = entity.working
            entity.waterLevel = LCCBlocks.nuclear_generator.getWaterMultiplier(world, pos, state).times(3f).roundToInt()
            if (entity.working) {
                if (!entity.inventory[2].isEmpty) {
                    val take = min(entity.inventory[2].count, floor(maxFuel.minus(entity.fuel)).toInt())
                    if (take > 0) {
                        entity.fuel = entity.fuel.coerceAtLeast(0f) + take
                        entity.inventory[2].decrement(take)
                    }
                }
                if (!entity.inventory[3].isEmpty) {
                    val coolant = getCoolantValue(entity.inventory[3])
                    if (coolant != null) {
                        val take = min(entity.inventory[3].count, floor(maxCoolant.minus(entity.coolant).div(coolant)).toInt())
                        if (take > 0) {
                            entity.coolant += coolant.times(take)
                            entity.inventory[3].decrement(take)
                            if (entity.inventory[3].isEmpty) entity.inventory[3].item.recipeRemainder?.stack(take) ?: ItemStack.EMPTY
                        }
                    }
                }

                if (entity.fuel <= 0f && entity.output <= 0f) {
                    entity.working = false
                } else {
                    if (entity.fuel >= 0f) {
                        entity.output += entity.fuel.times(fuelCoefficient)
                        entity.fuel = entity.fuel.minus(0.005f).minus(MathHelper.sqrt(maxFuel - entity.fuel).times(0.001f)).coerceAtLeast(0f)
                    }

                    entity.output = entity.output.times(0.995f.pow(entity.coolant.div(maxCoolant).let { it*it*it*it }.times(4).minus(3) + entity.coolant.div(maxCoolant).times(coolantCoefficient))).minus(0.01f).coerceAtLeast(0f)
                    entity.coolant -= entity.output.times(0.000015f)
                }

                if (entity.output > 400f) {
                    entity.meltdown()
                } else if (entity.output > maxSafeOutput && world.random.nextFloat() < entity.output.minus(maxSafeOutput).div(maxChanceOutput - maxSafeOutput).let { it*it*it }) {
                    entity.meltdown()
                }
            }

            entity.coolant = entity.coolant.minus(0.01f).coerceAtLeast(0f)

            if (working != entity.working) world.setBlockState(pos, state.with(LIT, entity.working), 3)

            EnergyTransaction()
                .apply { entity.inventory.slotsIn("power")?.also { includeAll(it.filter { (it.item as? StackEnergyHandler)?.isEnergyUsable(StackEnergyContext(it)) == true }.map { stack -> { entity.extractEnergy(stack.item as StackEnergyHandler, it, LooseEnergy, WorldEnergyContext(world, pos, null, null)) { StackEnergyContext(stack) } } }) } }
                .include { entity.requestEnergy(WorldEnergyContext(world, pos, null, null), it, LooseEnergy, *Direction.values()) }
                .run(50f)
        }

        fun getCoolantValue(stack: ItemStack) = when (stack.item.asItem()) {
            Items.SNOWBALL -> 0.375f
            Items.SNOW_BLOCK -> 1.5f
            Items.SNOW -> 0.1875f
            Items.ICE -> 1.5f
            Items.PACKED_ICE -> 2.5f
            Items.BLUE_ICE -> 3.5f
            Items.WATER_BUCKET -> 1f
            Items.POTION -> (PotionUtil.getPotion(stack) == Potions.WATER).to(1/3f, null)
            Items.POWDER_SNOW_BUCKET -> 2f
            else -> null
        }

        fun canStartup(energy: Float, inventory: LCCInventory) = energy >= maxEnergy && inventory[0].isOf(Items.TNT) && inventory[1].isOf(LCCItems.enriched_uranium_nugget) && inventory[2].isOf(LCCItems.nuclear_fuel)

        fun approxEquilibrium(fuel: Float, coolant: Float): Float {
            val x = 0.995f.pow(coolant.div(maxCoolant).let { it*it*it*it }.times(4).minus(3) + coolant.div(maxCoolant).times(coolantCoefficient))
            val f = fuel.times(fuelCoefficient)
            return -f.times(100).plus(1).div(x.minus(1).times(100))
        }

    }

}