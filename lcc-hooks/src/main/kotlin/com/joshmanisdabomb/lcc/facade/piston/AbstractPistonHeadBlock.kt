package com.joshmanisdabomb.lcc.facade.piston

import net.minecraft.block.*
import net.minecraft.block.enums.PistonType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.minecraft.world.WorldView

abstract class AbstractPistonHeadBlock(settings: Settings) : PistonHeadBlock(settings), LCCPistonHead {

    open val validBases: Map<PistonType, Array<Block>> by lazy { bases.mapValues { (_, v) -> arrayOf(v) } }

    protected open fun isAttached(headState: BlockState, pistonState: BlockState): Boolean {
        return validBases[headState[TYPE]]?.contains(pistonState.block) == true && pistonState.get(PistonBlock.EXTENDED) && pistonState.get(FACING) == headState.get(FACING)
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        if (!world.isClient && player.abilities.creativeMode) {
            val pos2 = pos.offset(state.get(FACING).opposite)
            if (isAttached(state, world.getBlockState(pos2))) {
                world.breakBlock(pos2, false)
            }
        }
        super.onBreak(world, pos, state, player)
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            super.onStateReplaced(state, world, pos, newState, moved)
            val blockPos = pos.offset((state.get(FACING) as Direction).opposite)
            if (isAttached(state, world.getBlockState(blockPos))) {
                world.breakBlock(blockPos, true)
            }
        }
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val blockState = world.getBlockState(pos.offset((state.get(FACING) as Direction).opposite))
        return isAttached(state, blockState) || blockState.isOf(Blocks.MOVING_PISTON) && blockState.get(FACING) == state.get(FACING)
    }

}