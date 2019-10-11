package com.joshmanisdabomb.lcc.misc;

import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;

public class ComputerSession {

    public ComputerSession(ComputingTileEntity te, ComputingTileEntity.ComputingModule module) {
        /*BlockNetwork<Pair<BlockPos, SlabType>>.NetworkResult res = new ComputingNetwork(64).discover(te.getWorld(), new ImmutablePair<>(te.getPos(), module.location));
        System.out.println(res);
        for (BlockPos pos : res.getTraversablePositions()) {
            System.out.println(te.getWorld().getBlockState(pos));
        }*/
    }

    /*public BlockNetwork<Pair<BlockPos, SlabType>>.NetworkResult getLocalNetwork() {

    }*/

}
