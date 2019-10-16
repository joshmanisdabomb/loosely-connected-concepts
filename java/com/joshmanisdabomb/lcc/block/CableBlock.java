package com.joshmanisdabomb.lcc.block;

import com.google.common.collect.Maps;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.TriPredicate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

import static com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity.LOCAL_NETWORK;
import static net.minecraft.state.properties.BlockStateProperties.*;

public class CableBlock extends Block {

    public static final TriPredicate<IWorld, BlockPos, Direction> NETWORKING_CABLE = (world, pos, from) -> {
        BlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        if (state.getBlock() instanceof ComputingBlock && te instanceof ComputingTileEntity) {
            switch (from) {
                case UP:
                    return ((ComputingTileEntity)te).getModule(SlabType.TOP) != null;
                case DOWN:
                    return ((ComputingTileEntity)te).getModule(SlabType.BOTTOM) != null;
                default:
                    return true;
            }
        }
        return false;
    };
    public static final TriPredicate<IWorld, BlockPos, Direction> TERMINAL_CABLE = (world, pos, from) -> {
        BlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        if (state.getBlock() instanceof ComputingBlock && te instanceof ComputingTileEntity) {
            switch (from) {
                case UP:
                    if (((ComputingTileEntity) te).getModule(SlabType.TOP) == null) return false;
                    break;
                case DOWN:
                    if (((ComputingTileEntity) te).getModule(SlabType.BOTTOM) == null) return false;
                    break;
                default:
                    break;
            }
            List<Pair<BlockPos, SlabType>> modules = LOCAL_NETWORK.discover((World)world, new ImmutablePair<>(pos, ((ComputingTileEntity) te).getInstalledModules().get(0).location)).getTraversables();
            return modules.stream().map(m -> {
                TileEntity te2 = world.getTileEntity(m.getLeft());
                if (te2 instanceof ComputingTileEntity) {
                    return ((ComputingTileEntity)te2).getModule(m.getRight());
                }
                return null;
            }).anyMatch(module -> module != null && module.type == ComputingModule.Type.COMPUTER);
        }
        return world.getBlockState(pos).getBlock() instanceof TerminalBlock;
    };

    private final TriPredicate<IWorld, BlockPos, Direction> connector;

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

    public CableBlock(TriPredicate<IWorld, BlockPos, Direction> connector, Properties p) {
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
            state = state.with(e.getValue(), other.getBlock() == this || this.connector.test(context.getWorld(), context.getPos().offset(e.getKey()), e.getKey().getOpposite()));
        }
        return state;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (!world.isRemote()) {
            return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos).with(FACING_TO_PROPERTIES.get(facing), facingState.getBlock() == this || this.connector.test(world, facingPos, facing));
        }
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos from, boolean isMoving) {
        if (block instanceof ComputingBlock) {
            BlockPos subtract = from.subtract(pos);
            Direction d = Direction.getFacingFromVector(subtract.getX(), subtract.getY(), subtract.getZ());
            world.setBlockState(pos, this.updatePostPlacement(state, d, world.getBlockState(from), world, pos, from), 3);
        }
    }

}