package com.joshmanisdabomb.lcc.block.network;

import com.joshmanisdabomb.lcc.block.CableBlock;
import com.joshmanisdabomb.lcc.block.ComputingBlock;
import com.joshmanisdabomb.lcc.block.TerminalBlock;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputingNetwork extends BlockNetwork<Pair<BlockPos, SlabType>> {

    private final Predicate<BlockState> cable;

    private final boolean networking, terminal;

    public ComputingNetwork(int distance, boolean networking, boolean terminal) {
        super(distance);
        this.networking = networking;
        this.terminal = terminal;
        this.cable = state -> (this.networking && state.getBlock() == LCCBlocks.networking_cable) || (this.terminal && state.getBlock() == LCCBlocks.terminal_cable);
    }

    @Override
    protected List<Pair<BlockPos, SlabType>> traverse(World world, Pair<BlockPos, SlabType> current, List<BlockPos> nodes) {
        ArrayList<Pair<BlockPos, SlabType>> positions = new ArrayList<>();
        BlockPos pos = this.toPosition(current);
        BlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        if (state.getBlock() instanceof ComputingBlock && te instanceof ComputingTileEntity && current.getRight() != null) {
            //traverse through cables, slabtypes will be null and will act like cubenetwork
            SlabType s = current.getRight();
            BlockPos pos2 = s == SlabType.TOP ? pos.up() : pos.down();
            BlockState state2 = world.getBlockState(pos2);
            TileEntity te2 = world.getTileEntity(pos2);

            ComputingModule m = ((ComputingTileEntity)te).getModule(s);

            ComputingModule other1 = ((ComputingTileEntity)te).getModule(flip(s));
            if (other1 != null && m.color == other1.color) {
                positions.add(new ImmutablePair<>(pos, flip(s)));
            }

            if (state2.getBlock() instanceof ComputingBlock && te2 instanceof ComputingTileEntity) {
                ComputingModule other2 = ((ComputingTileEntity)te2).getModule(flip(s));
                if (other2 != null && m.color == other2.color) {
                    positions.add(new ImmutablePair<>(pos2, flip(s)));
                }
            } else if (state2.getBlock() instanceof TerminalBlock) {
                if (m.color == ((TerminalBlock)state2.getBlock()).color) {
                    nodes.add(pos2);
                }
            }

            //check for cabling
            if (this.networking || this.terminal) {
                for (Direction d : Direction.values()) {
                    BlockPos pos3 = pos.offset(d);
                    BlockState state3 = world.getBlockState(pos3);
                    if (cable.test(state3)) {
                        positions.add(new ImmutablePair<>(pos3, null));
                    }
                }
            }
        } else {
            for (Direction d : Direction.values()) {
                if (state.getBlock() instanceof CableBlock && !state.get(CableBlock.FACING_TO_PROPERTIES.get(d))) continue;
                BlockPos pos2 = pos.offset(d);
                BlockState state2 = world.getBlockState(pos2);
                TileEntity te2 = world.getTileEntity(pos2);
                if (state2.getBlock() instanceof ComputingBlock && te2 instanceof ComputingTileEntity) {
                    switch (d) {
                        case UP:
                            if (((ComputingTileEntity)te2).getModule(SlabType.BOTTOM) == null) return positions;
                            break;
                        case DOWN:
                            if (((ComputingTileEntity)te2).getModule(SlabType.TOP) == null) return positions;
                            break;
                        default:
                            break;
                    }
                    for (ComputingModule m : ((ComputingTileEntity)te2).getInstalledModules()) {
                        positions.add(new ImmutablePair<>(pos2, m.location));
                    }
                } else if (state.getBlock() instanceof TerminalBlock && state2.getBlock() instanceof TerminalBlock) {
                    if (((TerminalBlock)state.getBlock()).color == ((TerminalBlock)state2.getBlock()).color) positions.add(new ImmutablePair<>(pos2, null));
                } else if (cable.test(state2)) {
                    positions.add(new ImmutablePair<>(pos2, null));
                } else {
                    nodes.add(pos2);
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
