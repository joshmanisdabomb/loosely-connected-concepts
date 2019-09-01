package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;

public class ClassicGrassBlock extends FunctionalGrassBlock {

    public ClassicGrassBlock(Properties properties) {
        super((state, snowy) -> {
            if (state == null) return Blocks.DIRT.getDefaultState();
            else if (state.getBlock() == Blocks.DIRT) return LCCBlocks.classic_grass_block.getDefaultState();
            else return null;
        }, properties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction side, IPlantable plantable) {
        return plantable == Blocks.POPPY || plantable == Blocks.DANDELION || plantable == LCCBlocks.classic_sapling;
    }

}
