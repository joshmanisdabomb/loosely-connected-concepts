package com.joshmanisdabomb.lcc.block.network;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class CubeNetwork extends BlockNetwork<BlockPos> {

    private final BiPredicate<BlockState, Direction> traversable;
    private final BiPredicate<BlockState, Direction> node;

    public CubeNetwork(BiPredicate<BlockState, Direction> traversable, BiPredicate<BlockState, Direction> node, int distance) {
        super(distance);
        this.traversable = traversable;
        this.node = node;
    }

    @Override
    protected List<BlockPos> traverse(World world, BlockPos current, List<BlockPos> nodes) {
        ArrayList<BlockPos> positions = new ArrayList<>();
        for (Direction d : Direction.values()) {
            BlockPos pos = current.offset(d);
            if (this.traversable.test(world.getBlockState(pos), d)) {
                positions.add(pos);
            } else if (this.node.test(world.getBlockState(pos), d)) {
                nodes.add(pos);
            }
        }
        return positions;
    }

    @Override
    protected BlockPos toPosition(BlockPos traversable) {
        return traversable;
    }

}