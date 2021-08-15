package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.directory.LCCDamage
import com.joshmanisdabomb.lcc.directory.LCCSounds
import com.joshmanisdabomb.lcc.extensions.directionalFacePlacement
import com.joshmanisdabomb.lcc.extensions.exp
import com.joshmanisdabomb.lcc.extensions.isHorizontal
import com.joshmanisdabomb.lcc.extensions.transform
import com.joshmanisdabomb.lcc.mixin.content.common.EntityAccessor
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundCategory
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.FACING
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import kotlin.math.abs

class SpikesBlock(settings: Settings, val modifier: (damage: Float, entity: LivingEntity) -> Float) : Block(settings), LCCBlockTrait {

    init {
        defaultState = stateManager.defaultState.with(FACING, Direction.UP)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = directionalFacePlacement(context)

    override fun rotate(state: BlockState, rot: BlockRotation) = state.with(FACING, rot.rotate(state[FACING]))

    override fun mirror(state: BlockState, mirror: BlockMirror) = state.rotate(mirror.getRotation(state[FACING]))

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = SHAPE[state[FACING]]

    override fun lcc_onEntityCollisionGroupedByClass(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) {
        if (entity is LivingEntity) {
            val speed = (entity as EntityAccessor).fullVelocityBeforeCollides.length()
            val d = abs(entity.x - entity.lastRenderX)
            val e = abs(entity.y - entity.lastRenderY)
            val f = abs(entity.z - entity.lastRenderZ)
            val g = entity.isSneaking.transform(0.03, 0.003)
            if (speed >= g && (d >= g || e >= g || f >= g) && !world.isClient) {
                val damage = states.mapNotNull { it.block as? SpikesBlock }.distinct().maxOf { it.modifier(speed.plus(1).exp(3).minus(1.0).times(4.0).coerceAtLeast(1.0).toFloat(), entity) }
                if (entity.damage(LCCDamage.spikes, damage)) {
                    world.playSound(null, entity.blockPos, LCCSounds.spikes_hurt, SoundCategory.BLOCKS, 1.0f, 1.0f)
                }
            }
            val horizontal = states.any { it[FACING].isHorizontal }
            val vertical = states.any { !it[FACING].isHorizontal }
            entity.slowMovement(defaultState, Vec3d(vertical.transform(0.14, 0.23), horizontal.transform(0.1, 0.4), vertical.transform(0.14, 0.23)))
        } else {
            entity.damage(LCCDamage.spikes, 1.0f)
        }
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val dir = state[FACING]
        val pos2 = pos.offset(dir.opposite)
        val state2 = world.getBlockState(pos2)
        if (dir == Direction.UP && state2.isOf(Blocks.HOPPER)) return true
        return state2.isSideSolidFullSquare(world, pos2, dir)
    }

    override fun getStateForNeighborUpdate(state: BlockState, direction: Direction, neighborState: BlockState, world: WorldAccess, pos: BlockPos, neighborPos: BlockPos): BlockState {
        return if (direction == state[FACING].opposite && !state.canPlaceAt(world, pos)) Blocks.AIR.defaultState else super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType): Boolean {
        return false
    }

    companion object {
        val SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.5, 16.0).rotatable
    }

}