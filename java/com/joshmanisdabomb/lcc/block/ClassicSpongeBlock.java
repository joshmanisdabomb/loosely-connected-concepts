package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ClassicSpongeBlock extends Block {

    public ClassicSpongeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        BlockPos.Mutable bp = new BlockPos.Mutable();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    if ((i != 0 || j != 0 || k != 0) && world.getFluidState(bp.setPos(pos).move(i,j,k)).isTagged(FluidTags.WATER)) {
                        this.absorb(state, world, pos, bp, true);
                    }
                }
            }
        }
    }

    public void absorb(BlockState state, IWorld world, BlockPos pos, BlockPos absorbed, boolean added) {
        BlockState state2 = world.getBlockState(absorbed);
        if (state2.getBlock() instanceof IBucketPickupHandler && ((IBucketPickupHandler)state2.getBlock()).pickupFluid(world, absorbed, state2) != Fluids.EMPTY) {
            return;
        } else if (state2.getMaterial() == Material.OCEAN_PLANT || state2.getMaterial() == Material.SEA_GRASS) {
            world.destroyBlock(absorbed, true);
        }
        world.setBlockState(absorbed, Blocks.AIR.getDefaultState(), added ? 3 : 20);
    }

}
