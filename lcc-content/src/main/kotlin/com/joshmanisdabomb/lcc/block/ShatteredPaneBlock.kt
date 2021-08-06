package com.joshmanisdabomb.lcc.block

import net.minecraft.block.BlockState
import net.minecraft.block.PaneBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.projectile.ProjectileEntity
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ShatteredPaneBlock(settings: Settings) : PaneBlock(settings) {

    override fun onProjectileHit(world: World, state: BlockState, hit: BlockHitResult, projectile: ProjectileEntity) {
        ShatteredGlassBlock.smash(world, hit.blockPos)
    }

    override fun onLandedUpon(world: World, pos: BlockPos, entity: Entity, distance: Float) {
        ShatteredGlassBlock.smash(world, pos)
    }

    override fun onSteppedOn(world: World, pos: BlockPos, entity: Entity) {
        ShatteredGlassBlock.smashOnWalk(world, pos, entity)
    }

}