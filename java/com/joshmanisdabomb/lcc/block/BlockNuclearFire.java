package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockNuclearFire extends BlockFire {

    public BlockNuclearFire(Block.Properties p) {
        super(p);
    }

    public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState) {
        if (oldState.getBlock() != state.getBlock()) {
            if (!state.isValidPosition(worldIn, pos)) {
                worldIn.removeBlock(pos);
            }
        }
    }

    public void tick(IBlockState state, World world, BlockPos pos, Random random) {
        if (world.getGameRules().getBoolean("doFireTick")) {
            if (!world.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when spreading fire
            if (!state.isValidPosition(world, pos)) {
                world.removeBlock(pos);
            }

            int i = state.get(AGE);
            if (i == 15) {
                world.removeBlock(pos);
            } else {
                BlockPos mbDown = pos.down();
                if (!world.isAirBlock(mbDown) && world.getBlockState(mbDown).getBlockHardness(world, mbDown) != -1.0f) {
                    world.setBlockState(mbDown, LCCBlocks.nuclear_waste.getDefaultState(), 3);
                }
                if (random.nextInt(2) == 0) {
                    BlockPos.MutableBlockPos mb = new BlockPos.MutableBlockPos(pos);
                    for (int x = -2; x <= 2; x++) {
                        for (int y = -2; y <= 2; y++) {
                            for (int z = -2; z <= 2; z++) {
                                mb.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                                float f = random.nextFloat();
                                if (i < 15 && world.isAirBlock(mb)) {
                                    if (f < 0.05f) {
                                        world.setBlockState(mb, state.with(AGE, i + 1), 3);
                                    }
                                } else if (world.getBlockState(mb).getBlock() != LCCBlocks.nuclear_fire && world.getBlockState(mb).getBlockHardness(world, mb) != -1.0f) {
                                    if (f < 0.2f) {
                                        world.setBlockState(mb, LCCBlocks.nuclear_waste.getDefaultState(), 3);
                                    } else if (f < 0.3f) {
                                        world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                                    }
                                }
                            }
                        }
                    }
                }
                if (random.nextFloat() < ((float)i/15)) {
                    world.removeBlock(pos);
                } else {
                    world.setBlockState(pos, state.with(AGE, i + 1), 4);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {

    }

    public int tickRate(IWorldReaderBase worldIn) {
        return 0;
    }

    public boolean canCatchFire(IBlockReader world, BlockPos pos, EnumFacing face) {
        return world.getBlockState(pos).getBlockFaceShape(world, pos, face) == BlockFaceShape.SOLID;
    }

}
