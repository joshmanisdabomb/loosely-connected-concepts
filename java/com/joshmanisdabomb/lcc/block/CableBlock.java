package com.joshmanisdabomb.lcc.block;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.BiPredicate;

import static net.minecraft.state.properties.BlockStateProperties.*;

public class CableBlock extends Block {

    private final BiPredicate<BlockState, Direction> connector;

    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), map -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });

    public static final VoxelShape CENTER_CABLE = Block.makeCuboidShape(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);
    public static final VoxelShape NORTH_CABLE = Block.makeCuboidShape(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 6.0D);
    public static final VoxelShape EAST_CABLE = Block.makeCuboidShape(10.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    public static final VoxelShape SOUTH_CABLE = Block.makeCuboidShape(6.0D, 6.0D, 10.0D, 10.0D, 10.0D, 16.0D);
    public static final VoxelShape WEST_CABLE = Block.makeCuboidShape(0.0D, 6.0D, 6.0D, 6.0D, 10.0D, 10.0D);
    public static final VoxelShape UP_CABLE = Block.makeCuboidShape(6.0D, 10.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    public static final VoxelShape DOWN_CABLE = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
    public static final Map<Direction, VoxelShape> FACING_TO_SHAPES = Util.make(Maps.newEnumMap(Direction.class), map -> {
        map.put(Direction.NORTH, NORTH_CABLE);
        map.put(Direction.EAST, EAST_CABLE);
        map.put(Direction.SOUTH, SOUTH_CABLE);
        map.put(Direction.WEST, WEST_CABLE);
        map.put(Direction.UP, UP_CABLE);
        map.put(Direction.DOWN, DOWN_CABLE);
    });

    public CableBlock(BiPredicate<BlockState, Direction> connector, Properties p) {
        super(p);
        this.connector = connector;
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        VoxelShape[] shapes = FACING_TO_SHAPES.entrySet().stream().filter(e -> state.get(FACING_TO_PROPERTIES.get(e.getKey()))).map(Map.Entry::getValue).toArray(VoxelShape[]::new);
        if (shapes.length == 0) return CENTER_CABLE;
        return VoxelShapes.or(CENTER_CABLE, shapes);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);
        for (Map.Entry<Direction, BooleanProperty> e : FACING_TO_PROPERTIES.entrySet()) {
            BlockState other = context.getWorld().getBlockState(context.getPos().offset(e.getKey()));
            state = state.with(e.getValue(), other.getBlock() == this || this.connector.test(other, e.getKey()));
        }
        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (!world.isRemote()) {
            return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos).with(FACING_TO_PROPERTIES.get(facing), facingState.getBlock() == this || this.connector.test(facingState, facing));
        }
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

}