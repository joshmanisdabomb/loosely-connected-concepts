package com.joshmanisdabomb.lcc.block.network;

import com.joshmanisdabomb.lcc.block.CogBlock;
import com.joshmanisdabomb.lcc.misc.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class CogNetwork extends BlockNetwork<Pair<BlockPos, Direction>> {

    public CogNetwork(int distance) {
        super(distance);
    }

    @Override
    protected List<Pair<BlockPos, Direction>> traverse(World world, Pair<BlockPos, Direction> current, List<BlockPos> nodes) {
        ArrayList<Pair<BlockPos, Direction>> positions = new ArrayList<>();
        BlockPos pos = this.toPosition(current);
        BlockState state = world.getBlockState(pos);
        Direction d = current.getRight();
        if (d == null) {
            ((CogBlock)state.getBlock()).getCogs(state).forEach(cd -> positions.add(new ImmutablePair<>(pos, cd)));
        } else {
            for (Direction pd : Util.PERPENDICULARS.get(d)) {
                BlockPos adjacentPos = pos.offset(pd);
                BlockState adjacent = world.getBlockState(adjacentPos);
                BlockPos wrapPos = adjacentPos.offset(d);
                BlockState wrap = world.getBlockState(wrapPos);

                if (((CogBlock)state.getBlock()).hasCog(pd, state)) positions.add(new ImmutablePair<>(pos, pd));

                if (adjacent.getBlock() instanceof CogBlock) {
                    if (((CogBlock)adjacent.getBlock()).hasCog(d, adjacent)) positions.add(new ImmutablePair<>(adjacentPos, d));
                } else if (adjacent.canProvidePower() && (world.getRedstonePower(adjacentPos, pd) > 0 || (adjacent.getBlock() instanceof RedstoneWireBlock && adjacent.get(BlockStateProperties.POWER_0_15) > 0))) {
                    nodes.add(adjacentPos);
                }

                if (!adjacent.isNormalCube(world, adjacentPos) && wrap.getBlock() instanceof CogBlock && ((CogBlock)wrap.getBlock()).hasCog(pd.getOpposite(), wrap)) positions.add(new ImmutablePair<>(wrapPos, pd.getOpposite()));
            }
        }
        return positions;
    }

    @Override
    protected BlockPos toPosition(Pair<BlockPos, Direction> traversable) {
        return traversable.getLeft();
    }

}
