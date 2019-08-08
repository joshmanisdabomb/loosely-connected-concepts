package com.joshmanisdabomb.lcc.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class SpreaderInterfaceBlock extends Block implements LCCBlockHelper {

    private static final VoxelShape LEG1 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 5.0D, 2.0D);
    private static final VoxelShape LEG2 = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 5.0D, 2.0D);
    private static final VoxelShape LEG3 = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 2.0D, 5.0D, 16.0D);
    private static final VoxelShape LEG4 = Block.makeCuboidShape(14.0D, 0.0D, 14.0D, 16.0D, 5.0D, 16.0D);
    private static final VoxelShape BASE = Block.makeCuboidShape(1.0D, 3.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    private static final VoxelShape PORT = Block.makeCuboidShape(5.0D, 15.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE = VoxelShapes.or(BASE, PORT, LEG1, LEG2, LEG3, LEG4);

    public SpreaderInterfaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

}