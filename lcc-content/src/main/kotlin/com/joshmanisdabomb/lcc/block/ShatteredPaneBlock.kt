package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.extensions.booleanProperty
import com.joshmanisdabomb.lcc.trait.LCCBlockTrait
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.PaneBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

open class ShatteredPaneBlock(val unbroken: Block, settings: Settings) : PaneBlock(settings), LCCBlockTrait {

    override fun onProjectileHit(world: World, state: BlockState, hit: BlockHitResult, projectile: ProjectileEntity) {
        ShatteredGlassBlock.smash(world, hit.blockPos)
    }

    override fun onLandedUpon(world: World, state: BlockState, pos: BlockPos, entity: Entity, fallDistance: Float) {
        ShatteredGlassBlock.smash(world, pos)
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        ShatteredGlassBlock.smashOnWalk(world, pos, entity)
    }

    override fun isSideInvisible(state: BlockState, stateFrom: BlockState, direction: Direction): Boolean {
        if (stateFrom.isOf(unbroken)) {
            if (!direction.axis.isHorizontal) return true
            if (state.get(direction.booleanProperty) && stateFrom.get(direction.opposite.booleanProperty)) return true
        }
        return super.isSideInvisible(state, stateFrom, direction)
    }

    override fun lcc_otherSideInvisible(state: BlockState, state2: BlockState, from: Direction): Boolean? {
        if (state2.isOf(unbroken)) {
            if (!from.axis.isHorizontal) return true
            if (state2.get(from.booleanProperty) && state.get(from.opposite.booleanProperty)) return true
        }
        return null
    }

}