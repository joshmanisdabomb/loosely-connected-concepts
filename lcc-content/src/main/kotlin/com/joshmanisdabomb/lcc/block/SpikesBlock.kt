package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.block.shape.RotatableShape.Companion.rotatable
import com.joshmanisdabomb.lcc.extensions.directionalFacePlacement
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
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
import kotlin.math.pow

class SpikesBlock(settings: Settings, modifier: (damage: Float, entity: LivingEntity) -> Float) : Block(settings), LCCBlockTrait {

    init {
        defaultState = stateManager.defaultState.with(Properties.FACING, Direction.UP)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) = builder.add(Properties.FACING).let {}

    override fun getPlacementState(context: ItemPlacementContext) = directionalFacePlacement(context)

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext) = SHAPE[state[Properties.FACING]]

    override fun lcc_onEntityCollisionGroupedByClass(world: World, pos: Array<BlockPos>, states: Array<BlockState>, entity: Entity) {
        if (entity is LivingEntity) {
            val d = entity.x - entity.lastRenderX
            val e = entity.y - entity.lastRenderY
            val f = entity.z - entity.lastRenderZ
            if (!world.isClient && (abs(d) >= 0.003 || abs(e) >= 0.003 || abs(f) >= 0.003)) {
                println("-------")
                println(d)
                println(e)
                println(f)
                println((d*d + e*e + f*f))
                println((d*d + e*e + f*f).times(4.0f).pow(2.0).toFloat())
                println(entity.velocity.x)
                println(entity.velocity.y)
                println(entity.velocity.z)
                entity.damage(DamageSource.SWEET_BERRY_BUSH, (d*d + e*e + f*f).times(4.0f).pow(2.0).coerceAtLeast(1.0).toFloat())
            }
            entity.slowMovement(defaultState, Vec3d(0.1, 0.5, 0.1))
        } else {

        }
    }

    companion object {
        val SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.5, 16.0).rotatable
    }

}