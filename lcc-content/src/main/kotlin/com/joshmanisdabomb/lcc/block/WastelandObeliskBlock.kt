package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.WastelandObeliskBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.extensions.isSurvival
import com.joshmanisdabomb.lcc.extensions.transformInt
import com.joshmanisdabomb.lcc.item.CrowbarItem
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties.BOTTOM
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.InvalidIdentifierException
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class WastelandObeliskBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(BOTTOM, false).with(charge, 0).with(CrowbarItem.salvage, true)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(BOTTOM, charge, CrowbarItem.salvage).let {}

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state[BOTTOM]) bottom_outline else top_outline

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state[BOTTOM]) bottom_collision else top_collision

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = if (state[BOTTOM]) bottom_shape else top_shape

    override fun getCameraCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = if (state[BOTTOM]) bottom_shape else top_shape

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = if (state[BOTTOM]) WastelandObeliskBlockEntity(pos, state) else null

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val stack = player.getStackInHand(hand)
        if (state[charge] == 0 && stack.isOf(LCCItems.enhancing_dust_beta)) {
            if (player.isSurvival) stack.decrement(1)
            setTallState(world, pos, state, 5)
            world.playSound(null, hit.pos.x, hit.pos.y, hit.pos.z, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat().times(0.2f).plus(0.9f))
            return ActionResult.SUCCESS
        } else if (state[charge] < 4 && stack.isOf(Items.ENDER_EYE)) {
            if (player.isSurvival) stack.decrement(1)
            setTallState(world, pos, state, state[charge] + 1)
            world.playSound(null, hit.pos.x, hit.pos.y, hit.pos.z, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat().times(0.2f).plus(0.9f))
            return ActionResult.SUCCESS
        } else if (state[charge] >= 4) {
            if (player.isSurvival && state[charge] == 4) {
                setTallState(world, pos, state, 0)
            }
            if (!world.isClient) {
                (world.getBlockEntity(pos.down(state[BOTTOM].transformInt(0, 1))) as? WastelandObeliskBlockEntity)?.activate(world as ServerWorld, state[charge] == 5)
            }
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

    private fun setTallState(world: World, pos: BlockPos, state: BlockState, charge: Int) {
        world.setBlockState(pos, state.with(Companion.charge, charge), 19)
        val pos2 = pos.up()
        val state2 = world.getBlockState(pos2)
        if (state[BOTTOM] && state2 == state.with(BOTTOM, false)) {
            world.setBlockState(pos2, state2.with(Companion.charge, charge), 19)
        }
        val pos3 = pos.down()
        val state3 = world.getBlockState(pos3)
        if (!state[BOTTOM] && state3 == state.with(BOTTOM, true)) {
            world.setBlockState(pos3, state3.with(Companion.charge, charge), 19)
        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        val down = ctx.blockPos.down()
        val state2 = ctx.world.getBlockState(down)
        if (state2.isOf(this)) {
            return defaultState.with(BOTTOM, true).with(CrowbarItem.salvage, false)
        } else if (state2.isOf(Blocks.SPAWNER)) {
            return try {
                defaultState.with(BOTTOM, false).with(CrowbarItem.salvage, false)
            } catch (e: InvalidIdentifierException) {
                null
            }
        } else {
            val up = ctx.blockPos.up()
            if (ctx.world.getBlockState(up).isAir) {
                return defaultState.with(BOTTOM, true).with(CrowbarItem.salvage, false)
            }
        }
        return null
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        if (state[BOTTOM]) {
            val up = pos.up()
            world.setBlockState(up, state.with(BOTTOM, false))
        }
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, fromState: BlockState, world: WorldAccess, pos: BlockPos, fromPos: BlockPos): BlockState {
        return if (state[BOTTOM] && direction == Direction.UP && fromState != state.with(BOTTOM, false)) {
            Blocks.AIR.defaultState
        } else if (!state[BOTTOM] && direction == Direction.DOWN && fromState != state.with(BOTTOM, true)) {
            Blocks.AIR.defaultState
        } else {
            state
        }
    }

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.wasteland_obelisk, WastelandObeliskBlockEntity::serverTick) else null

    companion object {
        val charge = IntProperty.of("charge", 0, 5)

        val bottom_collision = VoxelShapes.union(
            createCuboidShape(1.0, 0.0, 1.0, 15.0, 4.0, 15.0),
            createCuboidShape(4.0, 4.0, 4.0, 12.0, 16.0, 12.0)
        )
        val top_collision = VoxelShapes.union(
            createCuboidShape(4.0, 0.0, 4.0, 12.0, 4.0, 12.0),
            createCuboidShape(2.0, 4.0, 2.0, 14.0, 7.0, 14.0),
            createCuboidShape(5.0, 7.0, 5.0, 11.0, 13.0, 11.0),
            createCuboidShape(3.0, 13.0, 3.0, 13.0, 16.0, 13.0)
        )
        val bottom_shape = VoxelShapes.union(
            createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.0, 15.0),
            createCuboidShape(2.0, 1.0, 2.0, 14.0, 2.0, 14.0),
            createCuboidShape(1.0, 2.0, 1.0, 15.0, 3.0, 15.0),
            createCuboidShape(3.0, 3.0, 3.0, 13.0, 4.0, 13.0),
            createCuboidShape(4.0, 4.0, 4.0, 12.0, 16.0, 12.0)
        )
        val top_shape = VoxelShapes.union(
            createCuboidShape(4.0, 0.0, 4.0, 12.0, 4.0, 12.0),
            createCuboidShape(3.0, 4.0, 3.0, 13.0, 5.0, 13.0),
            createCuboidShape(4.0, 5.0, 4.0, 12.0, 6.0, 12.0),
            createCuboidShape(2.0, 6.0, 2.0, 14.0, 7.0, 14.0),
            createCuboidShape(6.0, 7.0, 6.0, 10.0, 13.0, 10.0),
            createCuboidShape(5.0, 8.0, 5.0, 11.0, 12.0, 11.0),
            createCuboidShape(3.0, 13.0, 3.0, 13.0, 14.0, 13.0),
            createCuboidShape(5.0, 14.0, 5.0, 11.0, 15.0, 11.0),
            createCuboidShape(7.0, 15.0, 7.0, 9.0, 16.0, 9.0)
        )
        val bottom_outline = VoxelShapes.union(
            createCuboidShape(1.0, 0.0, 1.0, 15.0, 1.0, 15.0),
            createCuboidShape(2.0, 1.0, 2.0, 14.0, 2.0, 14.0),
            createCuboidShape(1.0, 2.0, 1.0, 15.0, 3.0, 15.0),
            createCuboidShape(3.0, 3.0, 3.0, 13.0, 4.0, 13.0),
            createCuboidShape(4.0, 4.0, 4.0, 12.0, 20.0, 12.0),
            createCuboidShape(3.0, 20.0, 3.0, 13.0, 21.0, 13.0),
            createCuboidShape(4.0, 21.0, 4.0, 12.0, 22.0, 12.0),
            createCuboidShape(2.0, 22.0, 2.0, 14.0, 23.0, 14.0),
            createCuboidShape(6.0, 23.0, 6.0, 10.0, 29.0, 10.0),
            createCuboidShape(5.0, 24.0, 5.0, 11.0, 28.0, 11.0),
            createCuboidShape(3.0, 29.0, 3.0, 13.0, 30.0, 13.0),
            createCuboidShape(5.0, 30.0, 5.0, 11.0, 31.0, 11.0),
            createCuboidShape(7.0, 31.0, 7.0, 9.0, 32.0, 9.0)
        )
        val top_outline = VoxelShapes.union(
            createCuboidShape(1.0, -16.0, 1.0, 15.0, -15.0, 15.0),
            createCuboidShape(2.0, -15.0, 2.0, 14.0, -14.0, 14.0),
            createCuboidShape(1.0, -14.0, 1.0, 15.0, -13.0, 15.0),
            createCuboidShape(3.0, -13.0, 3.0, 13.0, -12.0, 13.0),
            createCuboidShape(4.0, -12.0, 4.0, 12.0, 4.0, 12.0),
            createCuboidShape(3.0, 4.0, 3.0, 13.0, 5.0, 13.0),
            createCuboidShape(4.0, 5.0, 4.0, 12.0, 6.0, 12.0),
            createCuboidShape(2.0, 6.0, 2.0, 14.0, 7.0, 14.0),
            createCuboidShape(6.0, 7.0, 6.0, 10.0, 13.0, 10.0),
            createCuboidShape(5.0, 8.0, 5.0, 11.0, 12.0, 11.0),
            createCuboidShape(3.0, 13.0, 3.0, 13.0, 14.0, 13.0),
            createCuboidShape(5.0, 14.0, 5.0, 11.0, 15.0, 11.0),
            createCuboidShape(7.0, 15.0, 7.0, 9.0, 16.0, 9.0)
        )
    }

}