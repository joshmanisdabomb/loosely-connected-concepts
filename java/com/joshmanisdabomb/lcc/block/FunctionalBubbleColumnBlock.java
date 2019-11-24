package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.misc.AdaptedFromSource;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Function;

public class FunctionalBubbleColumnBlock extends BubbleColumnBlock {

    private final Function<BlockState, ColumnType> bases;

    public FunctionalBubbleColumnBlock(Function<BlockState, ColumnType> bases, Properties properties) {
        super(properties);
        this.bases = bases;
    }

    @Override
    @AdaptedFromSource
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        this.place(worldIn, pos.up(), this.getDrag(worldIn, pos.down()));
    }

    @Override
    @AdaptedFromSource
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        this.place(worldIn, pos.up(), this.getDrag(worldIn, pos));
    }

    public void place(World world, BlockPos pos, boolean drag) {
        if (canHoldBubbleColumn(world, pos)) {
            world.setBlockState(pos, this.getDefaultState().with(DRAG, drag), 2);
        }
    }

    private boolean getDrag(IBlockReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == this) {
            return state.get(DRAG);
        } else {
            return this.bases.apply(state) == ColumnType.DOWNWARDS;
        }
    }

    @Override
    @AdaptedFromSource
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState state2 = worldIn.getBlockState(pos.down());
        return state2.getBlock() == this || this.bases.apply(state2) != ColumnType.NONE;
    }

    @Override
    @AdaptedFromSource
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.isValidPosition(worldIn, currentPos)) {
            return Blocks.WATER.getDefaultState();
        } else {
            if (facing == Direction.DOWN) {
                worldIn.setBlockState(currentPos, this.getDefaultState().with(DRAG, this.getDrag(worldIn, facingPos)), 2);
            } else if (facing == Direction.UP && facingState.getBlock() != this && canHoldBubbleColumn(worldIn, facingPos)) {
                worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, this.tickRate(worldIn));
            }

            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            return stateIn;
        }
    }

    public enum ColumnType {
        NONE,
        DOWNWARDS,
        UPWARDS
    }

}
