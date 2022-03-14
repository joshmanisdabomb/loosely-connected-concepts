package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.LCCBlockEntities
import com.joshmanisdabomb.lcc.directory.LCCPacketsToClient
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.directionalFacePlacement
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry
import net.fabricmc.fabric.api.server.PlayerStream
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.network.PacketByteBuf
import net.minecraft.sound.SoundCategory
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties.FACING
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class BouncePadBlock(settings: Settings, val motions: DoubleArray) : BlockWithEntity(settings), LCCBlockTrait {

    init {
        defaultState = stateManager.defaultState.with(FACING, Direction.UP).with(SETTING, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(FACING, SETTING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = directionalFacePlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(FACING, rot.rotate(state[FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[FACING]))

    override fun createBlockEntity(pos: BlockPos, state: BlockState) = BouncePadBlockEntity(pos, state)

    override fun onLandedUpon(world: World, state: BlockState, pos: BlockPos, entity: Entity, distance: Float) {
        entity.handleFallDamage(distance, 0.0f, DamageSource.FALL)
        entity.fallDistance = 0.0f
    }

    override fun onEntityLand(world: BlockView, entity: Entity) {
        entity.handleFallDamage(entity.fallDistance, 0.0f, DamageSource.FALL)
        entity.fallDistance = 0.0f
    }

    override fun lcc_onEntityCollisionGroupedByBlock(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) {
        with(pos.minByOrNull { it.getSquaredDistanceFromCenter(entity.x, entity.y, entity.z) } ?: return) {
            bounce(world, this, states[pos.indexOf(this)], entity)
        }
    }

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        entity.handleFallDamage(entity.fallDistance, 0.0f, DamageSource.FALL)
        entity.fallDistance = 0.0f
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = SHAPE[state[FACING]]

    override fun getCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        val direction = state[FACING]
        return if (direction == Direction.UP) COLLISION_UP else COLLISION[direction]
    }

    override fun getCullingShape(state: BlockState, world: BlockView, pos: BlockPos) = SHAPE[state[FACING]]

    override fun getCameraCollisionShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = SHAPE[state[FACING]]

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand?, hit: BlockHitResult): ActionResult {
        val cycle = state.with(SETTING, MathHelper.floorMod(state[SETTING].plus(if (player.isSneaking) -1 else 1), 5))
        world.setBlockState(pos, cycle)
        if (!world.isClient) world.playSound(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, LCCSounds.bounce_pad_set, SoundCategory.BLOCKS, 0.4F, 0.8F + (cycle[SETTING].times(0.1F)))
        return ActionResult.SUCCESS
    }

    private fun bounce(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        entity.handleFallDamage(entity.fallDistance, 0.0f, DamageSource.FALL)
        entity.fallDistance = 0.0f
        entity.isOnGround = false
        val dir = state[FACING]
        val setting = state[SETTING]

        if (dir == Direction.UP) {
            entity.updatePosition(pos.x + 0.5 + dir.offsetX.times(0.49), pos.y + 0.5 + dir.offsetY.times(0.49), pos.z + 0.5 + dir.offsetZ.times(0.49))
        }
        if (!world.isClient) {
            when (dir.axis) {
                Direction.Axis.X -> entity.setVelocity(dir.offsetX.times(motions[setting]), 0.5, 0.0)
                Direction.Axis.Y -> entity.setVelocity(0.0, dir.offsetY.times(motions[setting]), 0.0)
                Direction.Axis.Z -> entity.setVelocity(0.0, 0.5, dir.offsetZ.times(motions[setting]))
                else -> return
            }
            entity.velocityModified = true
            entity.velocityDirty = true

            PlayerStream.watching(world, pos).forEach { ServerSidePacketRegistry.INSTANCE.sendToPlayer(it, LCCPacketsToClient[LCCPacketsToClient::bounce_pad_extension].first().id, PacketByteBuf(Unpooled.buffer()).also { it.writeBlockPos(pos) }) }
        }
    }

    override fun <T : BlockEntity> getTicker(world: World, state: BlockState, type: BlockEntityType<T>) = if (world.isClient) checkType(type, LCCBlockEntities.bounce_pad, BouncePadBlockEntity::tick) else null

    override fun getRenderType(state: BlockState) = BlockRenderType.MODEL

    companion object {
        val SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0).rotatable
        val COLLISION_UP = createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0)
        val COLLISION = createCuboidShape(0.0, 0.0, 0.0, 16.0, 0.01, 16.0).rotatable

        val SETTING = IntProperty.of("setting", 0, 4)
    }

}
