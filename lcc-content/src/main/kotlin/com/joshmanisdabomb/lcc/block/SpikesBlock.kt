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
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundCategory
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.FACING
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import kotlin.math.abs

class SpikesBlock(settings: Settings, val modifier: (damage: Float, entity: LivingEntity) -> Float) : Block(settings), LCCBlockTrait {

    init {
        defaultState = stateManager.defaultState.with(FACING, Direction.UP)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = directionalFacePlacement(context)

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = SHAPE[state[FACING]]

    override fun lcc_onEntityCollisionGroupedByClass(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) {
        if (entity is LivingEntity) {
            val speed = (entity as EntityAccessor).fullVelocity.length()
            val d = abs(entity.x - entity.lastRenderX)
            val e = abs(entity.y - entity.lastRenderY)
            val f = abs(entity.z - entity.lastRenderZ)
            if (speed >= 0.003 && (d >= 0.003 || e >= 0.003 || f >= 0.003) && !world.isClient) {
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

    override fun canPathfindThrough(state: BlockState?, world: BlockView?, pos: BlockPos?, type: NavigationType?): Boolean {
        return super.canPathfindThrough(state, world, pos, type)
    }

    companion object {
        val SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.5, 16.0).rotatable
    }

}