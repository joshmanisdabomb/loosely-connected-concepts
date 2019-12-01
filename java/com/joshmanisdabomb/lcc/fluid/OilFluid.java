package com.joshmanisdabomb.lcc.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class OilFluid extends ForgeFlowingFluid {

    private final boolean source;

    public OilFluid(boolean source, Properties properties) {
        super(properties);
        this.source = source;
    }

    @Override
    public boolean isSource(IFluidState state) {
        return this.source;
    }

    @Override
    public int getLevel(IFluidState state) {
        return this.source ? 8 : state.get(LEVEL_1_8);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
        super.fillStateContainer(builder);
        if (!this.source) builder.add(LEVEL_1_8);
    }

    @Override
    public int getTickRate(IWorldReader world) {return 20;}

    @Override
    protected void flowInto(IWorld world, BlockPos pos, BlockState blockState, Direction d, IFluidState fluidState) {
        IFluidState fluid = world.getFluidState(pos);
        if (blockState.getBlock() == Blocks.FIRE || blockState.getBlock() instanceof FlowingFluidBlock) {
            if (blockState.getBlock() == Blocks.FIRE || fluid.isTagged(FluidTags.LAVA)) {
                world.setBlockState(pos.offset(d.getOpposite()), net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(world, pos.offset(d.getOpposite()), pos.offset(d.getOpposite()), Blocks.FIRE.getDefaultState().with(FireBlock.AGE, 15)), 3);
            }
            return;
        }
        super.flowInto(world, pos, blockState, d, fluidState);
    }

    @Override
    protected boolean func_215665_a(IFluidState state, IBlockReader world, BlockPos pos, Fluid fluidIn, Direction direction) {
        return false;
    }

}
