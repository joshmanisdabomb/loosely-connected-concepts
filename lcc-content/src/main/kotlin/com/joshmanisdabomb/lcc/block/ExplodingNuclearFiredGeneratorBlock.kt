package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.NuclearFiredGeneratorBlockEntity
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.extensions.horizontalPlacement
import com.joshmanisdabomb.lcc.extensions.isSurvival
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.HORIZONTAL_FACING
import net.minecraft.util.*
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion

class ExplodingNuclearFiredGeneratorBlock(settings: Settings) : BlockWithEntity(settings) {

    init {
        defaultState = stateManager.defaultState.with(HORIZONTAL_FACING, Direction.NORTH)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(HORIZONTAL_FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = horizontalPlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(HORIZONTAL_FACING, rot.rotate(state[HORIZONTAL_FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[HORIZONTAL_FACING]))

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = NuclearFiredGeneratorBlockEntity(pos, state)

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.fullCube()

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = VoxelShapes.fullCube()

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = VoxelShapes.empty()

    override fun getCameraCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = NuclearFiredGeneratorBlock.shape

    override fun getRaycastShape(state: BlockState?, world: BlockView?, pos: BlockPos?): VoxelShape {
        return super.getRaycastShape(state, world, pos)
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) player.openHandledScreen(state.createScreenHandlerFactory(world, pos) ?: return ActionResult.SUCCESS)
        return ActionResult.SUCCESS
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, stack: ItemStack) {
        if (stack.hasCustomName()) (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.customName = stack.name
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            ItemScatterer.spawn(world, pos, (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.inventory ?: return super.onStateReplaced(state, world, pos, newState, moved))
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        if (!world.isClient && player.isSurvival) {
            (world.getBlockEntity(pos) as? NuclearFiredGeneratorBlockEntity)?.explode(world as ServerWorld)
        }
        super.onBreak(world, pos, state, player)
    }

    override fun onDestroyedByExplosion(world: World, pos: BlockPos, explosion: Explosion) {
        if (!world.isClient) {
            NuclearFiredGeneratorBlockEntity.explode(world as ServerWorld, pos)
        }
        super.onDestroyedByExplosion(world, pos, explosion)
    }

    override fun hasComparatorOutput(state: BlockState) = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos) = ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (!world.isClient) checkType(type, LCCBlockEntities.nuclear_generator, NuclearFiredGeneratorBlockEntity::serverTick) else checkType(type, LCCBlockEntities.nuclear_generator, NuclearFiredGeneratorBlockEntity::clientTick)

}
