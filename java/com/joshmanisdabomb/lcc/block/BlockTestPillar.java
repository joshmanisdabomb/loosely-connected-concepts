package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;

public class BlockTestPillar extends BlockRotatedPillar implements LCCBlockHelper {

    public BlockTestPillar(Block.Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.AXIS, EnumFacing.Axis.Y));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(BlockStateProperties.AXIS);
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return traitPillarPlacement(context);
    }


}