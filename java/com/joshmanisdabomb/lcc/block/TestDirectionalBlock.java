package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class TestDirectionalBlock extends DirectionalBlock implements LCCBlockHelper {

    public TestDirectionalBlock(Block.Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return traitDirectionalPlacement(context);
    }

}
