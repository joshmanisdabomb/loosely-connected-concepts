package com.joshmanisdabomb.lcc.block;

import com.google.common.collect.Maps;
import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.CogModel;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
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

    public CogBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, CogState.INACTIVE).with(EAST, CogState.INACTIVE).with(SOUTH, CogState.INACTIVE).with(WEST, CogState.INACTIVE).with(UP, CogState.INACTIVE).with(DOWN, CogState.INACTIVE));
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
    public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        List<VoxelShape> shapes = FACING_TO_SHAPES.entrySet().stream().filter(e -> state.get(FACING_TO_PROPERTIES.get(e.getKey())) != CogState.NONE).map(Map.Entry::getValue).collect(Collectors.toList());
        if (shapes.size() == 0) return VoxelShapes.empty();
        return VoxelShapes.or(shapes.remove(0), shapes.toArray(new VoxelShape[]{}));
    }

    @Override
    public boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        System.out.println(FACING_TO_SHAPES.entrySet().stream().filter(e -> e.getValue() == shape).map(Map.Entry::getKey).findFirst().get());
        return true;
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
