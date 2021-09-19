package com.joshmanisdabomb.lcc.fluid

import com.joshmanisdabomb.lcc.directory.LCCBlocks
import com.joshmanisdabomb.lcc.directory.LCCFluids
import com.joshmanisdabomb.lcc.directory.LCCItems
import com.joshmanisdabomb.lcc.trait.LCCFluidTrait
import net.minecraft.block.*
import net.minecraft.fluid.FlowableFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.state.StateManager
import net.minecraft.tag.BlockTags
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class OilFluid(val source: Boolean) : FlowableFluid(), LCCFluidTrait {

    val colors = FloatArray(3) { 0.03f }

    override fun getStill() = LCCFluids.oil_still

    override fun getFlowing() = LCCFluids.oil_flowing

    override fun matchesType(fluid: Fluid) = fluid == still || fluid == flowing

    override fun getBucketItem() = LCCItems.oil_bucket

    override fun isStill(state: FluidState) = source

    override fun getLevel(state: FluidState) = if (source) 8 else state[LEVEL]

    override fun appendProperties(builder: StateManager.Builder<Fluid, FluidState>) = super.appendProperties(builder).also { if (!source) builder.add(LEVEL) }

    override fun getFlowSpeed(world: WorldView) = 1

    override fun getLevelDecreasePerBlock(world: WorldView) = 7

    override fun canBeReplacedWith(state: FluidState, world: BlockView, pos: BlockPos, fluid: Fluid, direction: Direction) = false

    override fun getTickRate(world: WorldView) = 25

    override fun getBlastResistance() = 2f

    override fun toBlockState(state: FluidState) = LCCBlocks.oil.defaultState.with(FluidBlock.LEVEL, getBlockStateLevel(state));

    override fun isInfinite() = false

    override fun beforeBreakingBlock(world: WorldAccess, pos: BlockPos, state: BlockState) {
        Block.dropStacks(state, world, pos, if (state.hasBlockEntity()) world.getBlockEntity(pos) else null)
    }

    override fun flow(world: WorldAccess, pos: BlockPos, state: BlockState, direction: Direction, fluidState: FluidState) {
        when {
            state.isIn(BlockTags.FIRE) || state.fluidState.isIn(FluidTags.LAVA) -> {
                val fire = AbstractFireBlock.getState(world, pos.offset(direction.opposite)).run { if (this == Blocks.FIRE) with(FireBlock.AGE, 15) else this }
                world.setBlockState(pos.offset(direction.opposite), fire, 3)
                world.blockTickScheduler.schedule(pos, fire.block, world.random.nextInt(5) + 2)
            }
            state.block is FluidBlock -> return
            else -> return super.flow(world, pos, state, direction, fluidState)
        }
    }

    override fun lcc_fogColor() = colors

    override fun lcc_fogDensity() = 0.7f//RenderSystem.fogMode(GlStateManager.FogMode.EXP2).let { 0.7f }

}
