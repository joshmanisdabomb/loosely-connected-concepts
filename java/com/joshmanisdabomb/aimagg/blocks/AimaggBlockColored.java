package com.joshmanisdabomb.aimagg.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface AimaggBlockColored {
	
	public int getColorFromBlock(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex);

	public int getColorFromItemstack(ItemStack stack, int tintIndex);
	
}
