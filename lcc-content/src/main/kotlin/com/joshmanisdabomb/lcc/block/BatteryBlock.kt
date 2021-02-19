package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.BatteryBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.extensions.directionalFacePlacement
import com.joshmanisdabomb.lcc.inventory.LCCInventory
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
import net.minecraft.state.property.Properties.FACING
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

abstract class BatteryBlock(settings: Settings) : BlockWithEntity(settings) {

    abstract val max: Float
    abstract val inputs: Int
    abstract val outputs: Int
    val slotCount get() = inputs + outputs

    abstract fun alterEnergy(current: Float): Float

    open val defaultDisplayName by lazy { TranslatableText("container.lcc.${LCCBlocks[this].name}") }

    init {
        defaultState = stateManager.defaultState.with(FACING, Direction.UP)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = directionalFacePlacement(context)

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = BatteryBlockEntity(pos, state)

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomName()) (world.getBlockEntity(pos) as? BatteryBlockEntity)?.customName = stack.name
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? BatteryBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) = ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.battery, BatteryBlockEntity::serverTick) else null

    abstract fun createMenu(syncId: Int, inv: PlayerInventory, inventory: LCCInventory, player: PlayerEntity, propertyDelegate: PropertyDelegate): ScreenHandler

}