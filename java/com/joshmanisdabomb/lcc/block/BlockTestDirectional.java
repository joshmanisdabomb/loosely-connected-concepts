package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;

public class BlockTestDirectional extends BlockDirectional implements LCCBlockHelper {

    public BlockTestDirectional(Block.Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.FACING, EnumFacing.NORTH));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return traitDirectionalPlacement(context);
    }

}
