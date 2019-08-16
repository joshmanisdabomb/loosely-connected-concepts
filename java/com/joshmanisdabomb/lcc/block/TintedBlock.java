package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.item.TintedItem;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

public interface TintedBlock extends TintedItem {

    int getBlockTintColor(BlockState state, IEnviromentBlockReader world, BlockPos pos, int tintIndex);

}
