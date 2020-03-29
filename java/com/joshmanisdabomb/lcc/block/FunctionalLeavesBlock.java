package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.misc.AdaptedFromSource;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Predicate;

public class FunctionalLeavesBlock extends LeavesBlock implements IShearableBlock {

    private final Predicate<BlockState> trunk;

    public FunctionalLeavesBlock(Predicate<BlockState> trunk, Properties properties) {
        super(properties);
        this.trunk = trunk;
    }

    @Override
    @AdaptedFromSource
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, this.updateDistance(state, world, pos), 3);
    }

    @Override
    @AdaptedFromSource
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.updateDistance(this.getDefaultState().with(PERSISTENT, true), context.getWorld(), context.getPos());
    }

    @Override
    @AdaptedFromSource
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        int i = this.getDistance(facingState) + 1;
        if (i != 1 || state.get(DISTANCE) != i) {
            world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        }
        return state;
    }

    public BlockState updateDistance(BlockState state, IWorld world, BlockPos pos) {
        int i = 7;

        try (BlockPos.PooledMutable blockpos$pooledmutableblockpos = BlockPos.PooledMutable.retain()) {
            for(Direction direction : Direction.values()) {
                blockpos$pooledmutableblockpos.setPos(pos).move(direction);
                i = Math.min(i, this.getDistance(world.getBlockState(blockpos$pooledmutableblockpos)) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return state.with(DISTANCE, i);
    }

    public int getDistance(BlockState neighbor) {
        if (this.trunk.test(neighbor)) return 0;
        else return neighbor.getBlock() instanceof LeavesBlock ? neighbor.get(DISTANCE) : 7;
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    public float getBreakSpeed(BlockState state, World world, BlockPos pos, ItemStack shears, PlayerEntity entity) {
        return 15.0F;
    }
}
