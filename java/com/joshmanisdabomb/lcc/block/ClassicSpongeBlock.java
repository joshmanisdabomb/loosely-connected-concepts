package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClassicSpongeBlock extends Block {

    public ClassicSpongeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        BlockPos.MutableBlockPos bp = new BlockPos.MutableBlockPos(pos);
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    if ((i != 0 || j != 0 || k != 0) && world.getFluidState(bp.setPos(pos).move(i,j,k)).isTagged(FluidTags.WATER)) {
                        this.absorb(state, world, pos, bp);
                    }
                }
            }
        }
    }

    public void absorb(BlockState state, World world, BlockPos pos, BlockPos.MutableBlockPos absorbed) {
        world.setBlockState(absorbed, Blocks.AIR.getDefaultState(), 3);
    }

}
