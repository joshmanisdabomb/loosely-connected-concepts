package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.directionalFacePlacement
import com.joshmanisdabomb.lcc.extensions.exp
import com.joshmanisdabomb.lcc.mixin.content.common.EntityAccessor
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.damage.DamageSource
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import kotlin.math.abs

class SpikesBlock(settings: Settings, val modifier: (damage: Float, entity: LivingEntity) -> Float) : Block(settings), LCCBlockTrait {

    init {
        defaultState = stateManager.defaultState.with(Properties.FACING, Direction.UP)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(Properties.FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = directionalFacePlacement(context)

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = SHAPE[state[Properties.FACING]]

    override fun lcc_onEntityCollisionGroupedByClass(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) {
        if (entity is LivingEntity) {
            val speed = (entity as EntityAccessor).fullVelocity.length()
            val d = abs(entity.x - entity.lastRenderX)
            val e = abs(entity.y - entity.lastRenderY)
            val f = abs(entity.z - entity.lastRenderZ)
            if (speed >= 0.003 && (d >= 0.003 || e >= 0.003 || f >= 0.003) && !world.isClient) {
                val damage = states.mapNotNull { it.block as? SpikesBlock }.distinct().maxOf { it.modifier(speed.plus(1).exp(3).minus(1.0).times(4.0).coerceAtLeast(1.0).toFloat(), entity) }
                entity.damage(DamageSource.SWEET_BERRY_BUSH, damage)
            }
            entity.slowMovement(defaultState, Vec3d(0.1, 0.5, 0.1))
        } else {
            entity.damage(DamageSource.CACTUS, 1.0f)
        }
    }

    override fun canPathfindThrough(state: BlockState?, world: BlockView?, pos: BlockPos?, type: NavigationType?): Boolean {
        return super.canPathfindThrough(state, world, pos, type)
    }

    companion object {
        val SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.5, 16.0).rotatable
    }

}