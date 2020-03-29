package com.joshmanisdabomb.lcc.block;

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
            entity.changeDimension(LCCDimensions.rainbow.getType());
        }
    }

}
