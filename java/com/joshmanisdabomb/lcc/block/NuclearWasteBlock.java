package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class NuclearWasteBlock extends FallingBlock {

    public NuclearWasteBlock(Block.Properties p) {
        super(p);
    }

    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            this.checkFallable(worldIn, pos);
        }
    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if (canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            BlockState state = this.getDefaultState();
            if (worldIn.getBlockState(pos).getBlock() == this) {
                state = worldIn.getBlockState(pos);
                worldIn.removeBlock(pos, false);
            }

            BlockPos.Mutable mb = new BlockPos.Mutable(pos);
            for (; canFallThrough(worldIn.getBlockState(mb)) && mb.getY() > 0; mb.move(0, -1, 0)) {
                ;
            }

            if (mb.getY() > 0) {
                worldIn.setBlockState(mb.up(), state);
            }
        }
    }

}
