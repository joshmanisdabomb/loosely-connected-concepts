package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.item.TintedItem;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;

public interface TintedBlock extends TintedItem {

    int getBlockTintColor(BlockState state, ILightReader world, BlockPos pos, int tintIndex);

}
