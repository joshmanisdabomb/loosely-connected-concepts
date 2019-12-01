package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class OilBlock extends FlowingFluidBlock {

    public OilBlock(Supplier<? extends FlowingFluid> fluid, Properties p) {
        super(fluid, p);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType path) {
        return false;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean reactWithNeighbors(World world, BlockPos pos, BlockState state) {
        boolean flag = true;
        for (Direction d : Direction.values()) {
            BlockPos bp = pos.offset(d);
            IFluidState fluid = world.getFluidState(bp);
            if (fluid.isTagged(FluidTags.LAVA)) {
                world.setBlockState(pos, Blocks.FIRE.getDefaultState().with(FireBlock.AGE, 15), 18);
                return false;
            }
        }
        return flag;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 3000;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 300;
    }

}
