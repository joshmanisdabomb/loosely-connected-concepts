package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class TestHorizontalBlock extends HorizontalBlock implements LCCBlockHelper {

    public TestHorizontalBlock(Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return traitHorizontalPlacement(context);
    }

}
