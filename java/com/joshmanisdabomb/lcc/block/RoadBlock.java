package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.render.ConnectedTextureBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class RoadBlock extends Block implements LCCBlockHelper, ConnectedTextureBlock {

    public static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public static final BooleanProperty MARKED = BooleanProperty.create("marked");

    @OnlyIn(Dist.CLIENT)
    private ConnectedTextureMap connectedTextureMap;

    public RoadBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(MARKED, false).with(BlockStateProperties.UP, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MARKED, BlockStateProperties.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return super.getStateForPlacement(context).with(BlockStateProperties.UP, context.getWorld().getBlockState(context.getPos().up()).func_224755_d(context.getWorld(), context.getPos().up(), Direction.DOWN));
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (player.getHeldItem(hand).isEmpty()) {
            world.setBlockState(pos, state.with(MARKED, !state.get(MARKED)));
            return true;
        }
        return false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.UP) {
            return stateIn.with(BlockStateProperties.UP, worldIn.getBlockState(facingPos).func_224755_d(worldIn, facingPos, Direction.DOWN));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        entity.setMotion(entity.getMotion().mul(1.5D, 1.0D, 1.5D));
    }

    @Override
    public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        return entity instanceof BoatEntity ? 0.989F : super.getSlipperiness(state, world, pos, entity);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(BlockStateProperties.UP) ? VoxelShapes.fullCube() : SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return state.get(BlockStateProperties.UP) ? VoxelShapes.fullCube() : SHAPE;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader world, BlockPos pos) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.get(BlockStateProperties.UP) ? VoxelShapes.fullCube() : SHAPE;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public boolean func_220074_n(BlockState state) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean connectWith(BlockState state, BlockState other) {
        if (other.getBlock().equals(state.getBlock())) {
            return !state.get(MARKED) || other.get(MARKED).equals(state.get(MARKED));
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int blockHeight(BlockState state) {
        return state.get(BlockStateProperties.UP) ? 16 : 15;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int borderWidth(BlockState state, Direction side) {
        return state.get(MARKED) && side == Direction.UP ? 7 : 4;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ConnectedTextureMap getConnectedTextureMap() {
        if (connectedTextureMap == null) connectedTextureMap = new ConnectedTextureMap().useWhen(state -> !state.get(MARKED), "road", true).useWhen(state -> state.get(MARKED), map -> {
            map.put(ConnectedTextureType.TOP_BASE, new ResourceLocation(LCC.MODID, "block/road/marked_base"));
            map.put(ConnectedTextureType.TOP_CORNERS_OUTER, new ResourceLocation(LCC.MODID, "block/road/marked_corners_o"));
            map.put(ConnectedTextureType.TOP_CORNERS_INNER, new ResourceLocation(LCC.MODID, "block/road/marked_corners_i"));
            map.put(ConnectedTextureType.TOP_LINES_H, new ResourceLocation(LCC.MODID, "block/road/marked_lines_h"));
            map.put(ConnectedTextureType.TOP_LINES_V, new ResourceLocation(LCC.MODID, "block/road/marked_lines_v"));
        });
        return connectedTextureMap;
    }

}
