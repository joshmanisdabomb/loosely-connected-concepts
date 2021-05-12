package com.joshmanisdabomb.lcc.fluid

import com.joshmanisdabomb.lcc.trait.LCCFluidTrait
import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCFluids
import com.joshmanisdabomb.lcc.directory.LCCItems
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.FluidBlock
import net.minecraft.block.FluidFillable
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties.AGE_7
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class AsphaltFluid(val source: Boolean) : FlowableFluid(), LCCFluidTrait {

    val colors = FloatArray(3) { 0.33f }

    override fun getStill() = LCCFluids.asphalt_still

    override fun getFlowing() = LCCFluids.asphalt_flowing

    override fun matchesType(fluid: Fluid) = fluid == still || fluid == flowing

    override fun getBucketItem() = LCCItems.asphalt_bucket

    override fun isStill(state: FluidState) = source

    override fun getLevel(state: FluidState) = if (source) 8 else state[LEVEL]

    override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) = super.appendProperties(builder).also { if (!source) builder.add(LEVEL) }

    override fun getFlowSpeed(world: WorldView) = 9

    override fun getLevelDecreasePerBlock(world: WorldView) = 1

    override fun canBeReplacedWith(state: FluidState, world: BlockView, pos: BlockPos, fluid: Fluid, direction: Direction) = true

    override fun getTickRate(world: WorldView) = 32

    override fun getBlastResistance() = 100.0f

    override fun toBlockState(state: FluidState) = LCCBlocks.asphalt.defaultState.with(FluidBlock.LEVEL, getBlockStateLevel(state));

    override fun isInfinite() = false

    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
        Block.dropStacks(state, world, pos, if (state.hasBlockEntity()) world.getBlockEntity(pos) else null)
    }

    override fun flow(world: WorldAccess, pos: BlockPos, state: BlockState, direction: Direction, fluidState: FluidState) {
        val b = state.block
        when (b) {
            is FluidBlock -> return
            else -> {
                if (b is FluidFillable) {
                    b.tryFillWithFluid(world, pos, state, fluidState)
                } else {
                    if (!state.isAir) beforeBreakingBlock(world, pos, state)
                    val stateFrom = world.getBlockState(pos.offset(direction, -1))
                    world.setBlockState(pos, fluidState.blockState.with(AGE_7, if (stateFrom.isOf(LCCBlocks.asphalt)) stateFrom[AGE_7].coerceAtMost(5) else 0), 3)
                }
            }
        }
    }

    override fun lcc_fogColor() = colors

    override fun lcc_fogDensity() = 0.7f//RenderSystem.fogMode(GlStateManager.FogMode.EXP2).let { 0.7f }

}