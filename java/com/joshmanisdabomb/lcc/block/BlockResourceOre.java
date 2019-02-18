package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCCResources;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockResourceOre extends Block {

    private final LCCResources r;

    public BlockResourceOre(Block.Properties p, LCCResources r) {
        super(p);
        this.r = r;
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        return r.oreType == LCCResources.OreType.DROP_INGOT ? r.ingot : this;
    }

    @Override
    public int getItemsToDropCount(IBlockState state, int fortune, World worldIn, BlockPos pos, Random random) {
        if (r.oreType == LCCResources.OreType.DROP_INGOT) {
            return (Math.max(0, random.nextInt(fortune + 2) - 1) + 1);
        } else {
            return 1;
        }
    }
}
