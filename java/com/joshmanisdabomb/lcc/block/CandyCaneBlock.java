package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class CandyCaneBlock extends PillarBlock {

    public static final BooleanProperty SIDE_ALTERNATE = BooleanProperty.create("side_alternate");
    public static final BooleanProperty END_ALTERNATE = BooleanProperty.create("end_alternate");

    public CandyCaneBlock(Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.AXIS, Direction.Axis.Y).with(SIDE_ALTERNATE, false).with(END_ALTERNATE, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AXIS, SIDE_ALTERNATE, END_ALTERNATE);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getCaneState(traitPillarPlacement(context), context.getPos());
    }

    public BlockState getCaneState(BlockState state, BlockPos pos) {
        Direction.Axis axis = state.get(AXIS);

        int a = axis == Direction.Axis.X ? pos.getZ() : pos.getX();
        int b = axis == Direction.Axis.Y ? pos.getZ() : pos.getY();
        boolean side = Math.floorMod(a + b, 2) == 0;

        int c = axis.isHorizontal() ? (axis == Direction.Axis.X ? pos.getZ() : pos.getX()) : pos.getX();
        boolean end = Math.floorMod(c, 2) == 0;
        return state.with(SIDE_ALTERNATE, side).with(END_ALTERNATE, end);
    }

}
