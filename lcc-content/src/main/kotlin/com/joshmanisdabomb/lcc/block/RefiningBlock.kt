package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.RefiningBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import com.joshmanisdabomb.lcc.inventory.RefiningInventory
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.text.Text
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

abstract class RefiningBlock(settings: Settings) : BlockWithEntity(settings) {

    private lateinit var p: EnumProperty<RefiningProcess>
    val processes get() = p

    protected abstract val availableProcesses: Array<RefiningProcess>

    abstract val maxEnergy: Float

    open val defaultDisplayName by lazy { Text.translatable("container.lcc.${LCCBlocks[this].name}") }

    open val inputWidth = 3
    open val inputHeight = 2
    val inputSlotCount get() = inputWidth * inputHeight
    open val outputSlotCount = 6
    open val fuelSlotCount = 3

    open val propertyCount = 9

    val slotCount get() = inputSlotCount + outputSlotCount + fuelSlotCount

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(p, RefiningProcess.NONE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        p = EnumProperty.of("process", RefiningProcess::class.java, RefiningProcess.NONE, *availableProcesses)
        builder.add(HORIZONTAL_FACING, p)
    }

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(HORIZONTAL_FACING, rot.rotate(state[HORIZONTAL_FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = RefiningBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomName()) (world.getBlockEntity(pos) as? RefiningBlockEntity)?.customName = stack.name
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? RefiningBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) = ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.refining, RefiningBlockEntity::serverTick) else null

    abstract fun createMenu(syncId: Int, inv: PlayerInventory, inventory: RefiningInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler

    companion object {
        fun brightness(state: BlockState) = state.properties.filter { it is EnumProperty<*> && it.values.first() is RefiningProcess }.firstOrNull()?.run { state.get(this as EnumProperty<RefiningProcess>).light } ?: 0
    }

    enum class RefiningProcess(val light: Int) : StringIdentifiable {
        NONE(0),
        MIXING(0),
        ENRICHING(0),
        TREATING(12),
        ARC_SMELTING(15),
        DRYING(5),
        PRESSING(0),
        PURIFYING(0);

        override fun asString() = name.toLowerCase()

        val nullableName get() = if (this == NONE) null else asString()

        companion object {
            val all = values().drop(1).toTypedArray()

            fun find(name: String): RefiningProcess? = all.find { it.asString().equals(name, ignoreCase = true) }
        }
    }

}
