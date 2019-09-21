package com.joshmanisdabomb.lcc.block;

import com.google.common.collect.Maps;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.CogModel;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;
import java.util.stream.Collectors;

public class CogBlock extends Block implements AdvancedBlockRender, MultipartBlock {

    public static final EnumProperty<CogState> NORTH = EnumProperty.create("north", CogState.class);
    public static final EnumProperty<CogState> EAST = EnumProperty.create("east", CogState.class);
    public static final EnumProperty<CogState> SOUTH = EnumProperty.create("south", CogState.class);
    public static final EnumProperty<CogState> WEST = EnumProperty.create("west", CogState.class);
    public static final EnumProperty<CogState> UP = EnumProperty.create("up", CogState.class);
    public static final EnumProperty<CogState> DOWN = EnumProperty.create("down", CogState.class);
    public static final Map<Direction, EnumProperty<CogState>> FACING_TO_PROPERTIES = Util.make(Maps.newEnumMap(Direction.class), map -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });
    public static final Map<EnumProperty<CogState>, Direction> PROPERTIES_TO_FACING = Util.make(new HashMap<>(), map -> {
        FACING_TO_PROPERTIES.forEach((key, value) -> map.put(value, key));
    });
    public static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(1.0F, 1.0F, 0.0F, 15.0F, 15.0F, 1.0F);
    public static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(1.0F, 1.0F, 15.0F, 15.0F, 15.0F, 16.0F);
    public static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(15.0F, 1.0F, 1.0F, 16.0F, 15.0F, 15.0F);
    public static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(0.0F, 1.0F, 1.0F, 1.0F, 15.0F, 15.0F);
    public static final VoxelShape UP_SHAPE = Block.makeCuboidShape(1.0F, 15.0F, 1.0F, 15.0F, 16.0F, 15.0F);
    public static final VoxelShape DOWN_SHAPE = Block.makeCuboidShape(1.0F, 0.0F, 1.0F, 15.0F, 1.0F, 15.0F);
    public static final Map<Direction, VoxelShape> FACING_TO_SHAPES = Util.make(Maps.newEnumMap(Direction.class), map -> {
        map.put(Direction.NORTH, NORTH_SHAPE);
        map.put(Direction.EAST, EAST_SHAPE);
        map.put(Direction.SOUTH, SOUTH_SHAPE);
        map.put(Direction.WEST, WEST_SHAPE);
        map.put(Direction.UP, UP_SHAPE);
        map.put(Direction.DOWN, DOWN_SHAPE);
    });
    public static final Map<VoxelShape, Direction> SHAPES_TO_FACING = Util.make(new HashMap<>(), map -> {
        FACING_TO_SHAPES.forEach((key, value) -> map.put(value, key));
    });

    public CogBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, CogState.NONE).with(EAST, CogState.NONE).with(SOUTH, CogState.NONE).with(WEST, CogState.NONE).with(UP, CogState.NONE).with(DOWN, CogState.NONE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public Collection<VoxelShape> getParts(BlockState state, IWorld world, BlockPos pos) {
        return FACING_TO_SHAPES.entrySet().stream().filter(e -> state.get(FACING_TO_PROPERTIES.get(e.getKey())) != CogState.NONE).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public BlockState updateEmptyState(BlockState check) {
        return FACING_TO_PROPERTIES.values().stream().allMatch(p -> check.get(p) == CogState.NONE) ? Blocks.AIR.getDefaultState() : check;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction face = context.getFace();
        BlockPos pos = context.getPos();
        BlockPos posFrom = context.getPos().offset(face.getOpposite());
        BlockState state = context.getWorld().getBlockState(pos);
        BlockState from = context.getWorld().getBlockState(posFrom);

        if (from.getBlock() == this) {
            VoxelShape shape = this.getPartFromTrace(context.getHitVec(), from, context.getWorld(), posFrom);
            if (face.getOpposite() == SHAPES_TO_FACING.get(shape)) {
                return null;
            } else {
                if (state.getBlock() == this) {
                    return this.updateEmptyState(state.with(FACING_TO_PROPERTIES.get(SHAPES_TO_FACING.get(shape)), CogState.INACTIVE));
                } else {
                    return this.updateEmptyState(this.getDefaultState().with(FACING_TO_PROPERTIES.get(SHAPES_TO_FACING.get(shape)), CogState.INACTIVE));
                }
            }
        }

        if (state.getBlock() == this) {
            return this.updateEmptyState(state.with(FACING_TO_PROPERTIES.get(context.getFace().getOpposite()), CogState.INACTIVE));
        } else {
            return this.updateEmptyState(this.getDefaultState().with(FACING_TO_PROPERTIES.get(context.getFace().getOpposite()), CogState.INACTIVE));
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return context.getItem().getItem() == this.asItem() && !context.replacingClickedOnBlock();
    }

    @Override
    public boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        EnumProperty<CogState> cog = FACING_TO_PROPERTIES.get(SHAPES_TO_FACING.get(shape));
        world.setBlockState(pos, this.updateEmptyState(state.with(cog, CogState.NONE)), 3);
        return true;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        return this.updateEmptyState(super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        List<VoxelShape> shapes = FACING_TO_SHAPES.entrySet().stream().filter(e -> state.get(FACING_TO_PROPERTIES.get(e.getKey())) != CogState.NONE).map(Map.Entry::getValue).collect(Collectors.toList());
        if (shapes.size() == 0) return VoxelShapes.empty();
        return VoxelShapes.or(shapes.remove(0), shapes.toArray(new VoxelShape[]{}));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBakedModel newModel(Block block) {
        return new CogModel(block);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Collection<ResourceLocation> getTextures() {
        return Arrays.asList(new ResourceLocation(LCC.MODID, "block/nostalgia/cog"), new ResourceLocation(LCC.MODID, "block/nostalgia/cog_cw"), new ResourceLocation(LCC.MODID, "block/nostalgia/cog_ccw"));
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public enum CogState implements IStringSerializable {
        NONE,
        INACTIVE,
        ACTIVE;

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }
    }

}
