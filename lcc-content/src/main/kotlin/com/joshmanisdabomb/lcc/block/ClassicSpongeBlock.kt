package com.joshmanisdabomb.lcc.block

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import net.minecraft.block.*
import net.minecraft.item.ItemStack
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ClassicSpongeBlock(settings: Settings) : Block(settings) {

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        val bp = BlockPos.Mutable()
        for (i in -2..2) {
            for (j in -2..2) {
                for (k in -2..2) {
                    if ((i != 0 || j != 0 || k != 0) && world.getFluidState(bp.set(pos).move(i, j, k)).isIn(FluidTags.WATER)) {
                        this.absorb(state, world, pos, bp, true)
                    }
                }
            }
        }
    }

    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (!state.isOf(newState.block)) {
            val bp = BlockPos.Mutable()
            for (i in -3..3) {
                for (j in -3..3) {
                    for (k in -3..3) {
                        val fluid = world.getFluidState(bp.set(pos).move(i, j, k))
                        if (((i == 3 || i == -3) || (j == 3 || j == -3) || (k == 3 || k == -3)) && fluid.isIn(FluidTags.WATER)) {
                            world.createAndScheduleFluidTick(bp, fluid.fluid, fluid.fluid.getTickRate(world))
                        }
                    }
                }
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    fun absorb(state: BlockState, world: World, pos: BlockPos, absorbed: BlockPos, added: Boolean) {
        val state2 = world.getBlockState(absorbed)
        if ((state2.block as? FluidDrainable)?.tryDrainFluid(world, absorbed, state2) ?: ItemStack.EMPTY != ItemStack.EMPTY) {
            return
        } else if (state2.material == Material.REPLACEABLE_UNDERWATER_PLANT) {
            world.removeBlock(absorbed, true)
        }
        world.setBlockState(absorbed, Blocks.AIR.defaultState, if (added) 3 else 20)
    }

    companion object {
        fun cancelFill(world: BlockView, pos: BlockPos, state: BlockState): Boolean {
            val bp = BlockPos.Mutable()
            for (i in -2..2) {
                for (j in -2..2) {
                    for (k in -2..2) {
                        if (world.getBlockState(bp.set(pos).move(i, j, k)).isOf(LCCBlocks.classic_sponge)) {
                            //LCCBlocks.classic_sponge.absorb(state, world, bp, pos, true)
                            return true
                        }
                    }
                }
            }
            return false
        }
    }

}