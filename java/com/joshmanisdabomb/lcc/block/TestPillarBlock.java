package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class TestPillarBlock extends RotatedPillarBlock implements LCCBlockHelper {

    public TestPillarBlock(Block.Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.AXIS, Direction.Axis.Y));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.AXIS);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return traitPillarPlacement(context);
    }


}