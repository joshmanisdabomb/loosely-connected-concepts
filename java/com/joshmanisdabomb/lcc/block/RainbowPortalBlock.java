package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.gen.dimension.teleporter.RainbowTeleporter;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class RainbowPortalBlock extends Block implements LCCBlockHelper {

    protected static final VoxelShape X = Block.makeCuboidShape(0.0D, 0.0D, 7.0D, 16.0D, 16.0D, 9.0D);
    protected static final VoxelShape Z = Block.makeCuboidShape(7.0D, 0.0D, 0.0D, 9.0D, 16.0D, 16.0D);
    public static final IntegerProperty Y = RainbowGateBlock.Y;
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty MIDDLE = BooleanProperty.create("middle");

    public RainbowPortalBlock(Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.X).with(Y, 0).with(MIDDLE, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(Y, AXIS, MIDDLE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(AXIS) == Direction.Axis.X ? X : Z;
    }

    @Override
    public RenderType getRenderLayer() {
        return RenderType.getTranslucent();
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        int y = state.get(Y);
        if (facing == Direction.UP && y != 3 && facingState != state.with(Y, y+1)) return Blocks.AIR.getDefaultState();
        if (facing == Direction.DOWN && y != 0 && facingState != state.with(Y, y-1)) return Blocks.AIR.getDefaultState();
        if (facing == Direction.DOWN && y == 0 && !RainbowPortalBlock.validGround(world, facingPos)) return Blocks.AIR.getDefaultState();
        Direction.Axis axis = state.get(AXIS);
        if (facing.getAxis() == axis && facingState != state && facingState != LCCBlocks.rainbow_gate.getDefaultState().with(Y, y)) return Blocks.AIR.getDefaultState();
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    public static boolean validGround(IWorldReader world, BlockPos pos) {
        return hasSolidSideOnTop(world, pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isRemote) {
            BlockPos middle = this.findMiddle(state, world, pos);
            if (middle == null) return;
            Direction.Axis axis = this.findAxis(state, world, middle);
            if (axis == null) return;
            entity.changeDimension(world.getDimension().getType() == LCCDimensions.rainbow.getType() ? DimensionType.OVERWORLD : LCCDimensions.rainbow.getType(), RainbowTeleporter.INSTANCE.setGateMiddle(middle).setGateAxis(axis));
        }
    }

    protected BlockPos findMiddle(BlockState state, World world, BlockPos pos) {
        BlockPos.Mutable bp = new BlockPos.Mutable(pos);
        BlockState state2 = null;
        for (int i = 0; i < 4; i++) {
            state2 = world.getBlockState(bp);
            if (state2 == state.with(Y, 0)) break;
            bp.move(Direction.DOWN);
            if (i == 3) return null;
        }
        if (state2.get(MIDDLE)) return bp;
        for (int i = 0; i < 4; i++) {
            Direction d = Direction.byHorizontalIndex(i);
            bp.move(d);
            state2 = world.getBlockState(bp);
            if (state2.getBlock() == this && state2.get(MIDDLE)) return bp.toImmutable();
            bp.move(d.getOpposite());
        }
        return null;
    }

    protected Direction.Axis findAxis(BlockState state, World world, BlockPos middle) {
        BlockPos.Mutable bp = new BlockPos.Mutable(middle);
        for (int i = 0; i < 4; i++) {
            Direction d = Direction.byHorizontalIndex(i);
            bp.move(d);
            if (world.getBlockState(bp) == state.with(MIDDLE, false)) {
                bp.move(d);
                if (world.getBlockState(bp).getBlock() instanceof RainbowGateBlock) return d.getAxis();
                bp.move(d.getOpposite());
            }
            bp.move(d.getOpposite());
        }
        return null;
    }

}
