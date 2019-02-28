package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockResourceOre extends Block {

    public BlockResourceOre(Block.Properties p) {
        super(p);
    }

    @Override
    public IItemProvider getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        if (this == LCCBlocks.ruby_ore) return LCCItems.ruby;
        if (this == LCCBlocks.topaz_ore) return LCCItems.topaz;
        if (this == LCCBlocks.sapphire_ore) return LCCItems.sapphire;
        if (this == LCCBlocks.amethyst_ore) return LCCItems.amethyst;
        return this;
    }

    @Override
    public int getItemsToDropCount(IBlockState state, int fortune, World worldIn, BlockPos pos, Random random) {
        if (this == LCCBlocks.ruby_ore || this == LCCBlocks.topaz_ore || this == LCCBlocks.sapphire_ore || this == LCCBlocks.amethyst_ore) {
            return (Math.max(0, random.nextInt(fortune + 2) - 1) + 1);
        } else {
            return 1;
        }
    }
}
