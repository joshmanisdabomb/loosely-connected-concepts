package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;

public class BlockTestHorizontal extends BlockHorizontal implements LCCBlockHelper {

    public BlockTestHorizontal(Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.HORIZONTAL_FACING, EnumFacing.NORTH));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return traitHorizontalPlacement(context);
    }

}
