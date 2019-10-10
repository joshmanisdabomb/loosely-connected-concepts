package com.joshmanisdabomb.lcc.block.network;

import com.joshmanisdabomb.lcc.block.CogBlock;
import com.joshmanisdabomb.lcc.block.ComputingBlock;
import com.joshmanisdabomb.lcc.misc.Util;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputingNetwork extends BlockNetwork<Pair<BlockPos, SlabType>> {

    public ComputingNetwork(int distance) {
        super(distance);
    }

    @Override
    protected List<Pair<BlockPos, SlabType>> traverse(World world, Pair<BlockPos, SlabType> current, List<BlockPos> nodes) {
        ArrayList<Pair<BlockPos, SlabType>> positions = new ArrayList<>();
        BlockPos pos = this.toPosition(current);
        BlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        if (state.getBlock() instanceof ComputingBlock && te instanceof ComputingTileEntity) {
            //traverse through cables, slabtypes will be null and will act like cubenetwork
            SlabType s = current.getRight();
            BlockPos pos2 = s == SlabType.TOP ? pos.up() : pos.down();
            BlockState state2 = world.getBlockState(pos2);
            TileEntity te2 = world.getTileEntity(pos2);

            ComputingTileEntity.ComputingModule m = ((ComputingTileEntity)te).getModule(s);

            ComputingTileEntity.ComputingModule other1 = ((ComputingTileEntity)te).getModule(flip(s));
            if (other1 != null && m.color == other1.color) {
                positions.add(new ImmutablePair<>(pos, flip(s)));
            }

            if (state2.getBlock() instanceof ComputingBlock && te2 instanceof ComputingTileEntity) {
                ComputingTileEntity.ComputingModule other2 = ((ComputingTileEntity)te2).getModule(flip(s));
                if (other2 != null && m.color == other2.color) {
                    positions.add(new ImmutablePair<>(pos2, flip(s)));
                }
            }
        }
        return positions;
    }

    @Override
    protected BlockPos toPosition(Pair<BlockPos, SlabType> traversable) {
        return traversable.getLeft();
    }

}
