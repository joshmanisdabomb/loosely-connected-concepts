package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.RefiningBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import com.joshmanisdabomb.lcc.inventory.DefaultInventory
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

abstract class RefiningBlock(settings: Settings) : BlockWithEntity(settings) {

    private lateinit var p: EnumProperty<RefiningProcess>
    val processes get() = p

    protected abstract val availableProcesses: Array<RefiningProcess>

    abstract val maxEnergy: Float

    abstract val defaultDisplayName: Text

    open val inputSlotCount = 6
    open val outputSlotCount = 6
    open val fuelSlotCount = 3

    open val propertyCount = 2

    val slotCount get() = inputSlotCount + outputSlotCount + fuelSlotCount

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH).with(p, RefiningProcess.NONE)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        p = EnumProperty.of("process", RefiningProcess::class.java, RefiningProcess.NONE, *availableProcesses)
        builder.add(HORIZONTAL_FACING, p)
    }

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = RefiningBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.refining, RefiningBlockEntity::serverTick) else null

    abstract fun createMenu(syncId: Int, inv: PlayerInventory, inventory: DefaultInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler?

    enum class RefiningProcess(val light: Int) : StringIdentifiable {
        NONE(0),
        MIXING(0),
        ENRICHING(0);

        override fun asString() = name.toLowerCase()

        val nullableName get() = if (this == NONE) null else asString()

        companion object {
            val all = values().drop(1).toTypedArray()
        }
    }

}