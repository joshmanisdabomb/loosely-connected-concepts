package com.joshmanisdabomb.lcc.block;

import com.google.common.collect.Maps;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.CogModel;
import com.joshmanisdabomb.lcc.block.network.BlockNetwork;
import com.joshmanisdabomb.lcc.block.network.CogNetwork;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.registry.LCCSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nullable;
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

    public static final CogNetwork NETWORK = new CogNetwork(32);

    public CogBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, CogState.NONE).with(EAST, CogState.NONE).with(SOUTH, CogState.NONE).with(WEST, CogState.NONE).with(UP, CogState.NONE).with(DOWN, CogState.NONE));
    }

    public boolean hasCog(Direction d, BlockState state) {
        return state.get(FACING_TO_PROPERTIES.get(d)) != CogState.NONE;
    }

    protected BlockState setCog(Direction d, BlockState state, boolean active, boolean check) {
        if (check && this.hasCog(d, state)) return state;
        return state.with(FACING_TO_PROPERTIES.get(d), active ? CogState.ACTIVE : CogState.INACTIVE);
    }

    protected BlockState removeCog(Direction d, BlockState state) {
        return state.with(FACING_TO_PROPERTIES.get(d), CogState.NONE);
    }

    protected BlockState destroyCog(Direction d, BlockState state, IWorld world, BlockPos pos, boolean effects, boolean drops) {
        if ((effects || drops) && this.hasCog(d, state)) {
            if (effects) world.playEvent(2001, pos, Block.getStateId(this.getDefaultState().with(FACING_TO_PROPERTIES.get(d), CogState.INACTIVE)));
            if (drops && world instanceof World) Block.spawnDrops(state, (World)world, pos, null);
        }
        return this.removeCog(d, state);
    }

    protected boolean validCogPosition(Direction d, BlockState state, IWorld world, BlockPos pos) {
        BlockPos from = pos.offset(d);
        return world.getBlockState(from).func_224755_d(world, from, d);
    }

    protected boolean oneCog(BlockState state) {
        return this.getCogs(state).size() == 1;
    }

    public List<Direction> getCogs(BlockState state) {
        return Arrays.stream(Direction.values()).filter(d -> this.hasCog(d, state)).collect(Collectors.toList());
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (oldState.getBlock() != state.getBlock() && !world.isRemote) {
            this.updateRedstone(state, world, pos);
        }
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() == newState.getBlock() && !world.isRemote) {
            this.updateRedstone(newState, world, pos);
        }
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (world.getBlockState(fromPos).getBlock() != this && !world.isRemote) {
            this.updateRedstone(state, world, pos);
        }
    }

    protected void updateRedstone(BlockState state, World world, BlockPos pos) {
        BlockNetwork.NetworkResult r = NETWORK.discover(world, new ImmutablePair<>(pos, null));
        boolean active = r.getNodes().size() != 0;
        for (BlockPos bp : r.getTraversables()) {
            final BlockState state2 = world.getBlockState(bp);
            BlockState state2New = null;
            for (Direction d : this.getCogs(state2)) {
                state2New = this.setCog(d, state2New != null ? state2New : state2, active, false);
            }
            if (state2New != null) {
                world.setBlockState(bp, state2New, 3);
            }
        }
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.getWeakPower(blockAccess, pos, side);
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return 15;
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
        return this.getCogs(check).size() == 0 ? Blocks.AIR.getDefaultState() : check;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction face = context.getFace();
        BlockPos pos = context.getPos();
        BlockPos posFrom = context.getPos().offset(face.getOpposite());
        BlockState state = context.getWorld().getBlockState(pos);
        BlockState from = context.getWorld().getBlockState(posFrom);

        if (!context.replacingClickedOnBlock() && from.getBlock() == this) {
            VoxelShape shape = this.getPartFromTrace(context.getHitVec(), from, context.getWorld(), posFrom);
            Direction face2 = SHAPES_TO_FACING.get(shape);
            if (face.getOpposite() == face2) {
                return null;
            } else {
                if (!this.validCogPosition(face2, state, context.getWorld(), pos)) return null;
                return this.updateEmptyState(this.setCog(face2, state.getBlock() == this ? state : this.getDefaultState(), false, true));
            }
        }

        if (!this.validCogPosition(face.getOpposite(), state, context.getWorld(), pos)) return null;
        return this.updateEmptyState(this.setCog(face.getOpposite(), state.getBlock() == this ? state : this.getDefaultState(), false, true));
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return context.getItem().getItem() == this.asItem() && !context.replacingClickedOnBlock();
    }

    @Override
    public boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        world.setBlockState(pos, this.updateEmptyState(this.destroyCog(SHAPES_TO_FACING.get(shape), state, world, pos, !this.oneCog(state), true)), 3);
        return true;
    }

    @Override
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
        return !this.oneCog(state);
    }

    @Override
    public SoundType getSoundType(BlockState state) {
        return !this.oneCog(state) ? LCCSounds.cog_multiple : super.getSoundType(state);
    }

    @Override
    public SoundType getSoundType(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        return this.getSoundType(state);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
        if (this.hasCog(facing, state) && !this.validCogPosition(facing, state, world, pos) && world instanceof World) {
            state = this.destroyCog(facing, state, world, pos, !this.oneCog(state), true);
            this.updateRedstone(state, (World)world, pos);
        }
        return this.updateEmptyState(state);
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
