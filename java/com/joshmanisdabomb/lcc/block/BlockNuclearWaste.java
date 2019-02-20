package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNuclearWaste extends BlockFalling {

    public BlockNuclearWaste(Block.Properties p) {
        super(p);
    }

    public void tick(IBlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            this.checkFallable(worldIn, pos);
        }
    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if (canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            IBlockState state = getDefaultState();
            if (worldIn.getBlockState(pos).getBlock() == this) {
                state = worldIn.getBlockState(pos);
                worldIn.removeBlock(pos);
            }

            BlockPos blockpos;
            for(blockpos = pos.down(); canFallThrough(worldIn.getBlockState(blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down()) {
                ;
            }

            if (blockpos.getY() > 0) {
                worldIn.setBlockState(blockpos.up(), state);
            }
        }
    }

}
