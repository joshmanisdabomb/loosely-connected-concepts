package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.block.model.ChocolateModel;
import com.joshmanisdabomb.lcc.block.model.ComputingModel;
import com.joshmanisdabomb.lcc.block.render.AdvancedBlockRender;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.item.ChocolateBlockItem;
import com.joshmanisdabomb.lcc.item.ComputingBlockItem;
import com.joshmanisdabomb.lcc.registry.LCCSounds;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ChocolateBlock extends Block implements MultipartBlock, AdvancedBlockRender {

    public static final EnumProperty<Type> TOP = EnumProperty.create("top", Type.class);
    public static final EnumProperty<Type> BOTTOM = EnumProperty.create("bottom", Type.class);

    public static final VoxelShape BOTTOM_SLAB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    public static final VoxelShape BOTTOM_SQUARE_1 = Block.makeCuboidShape(1.0D, 8.0D, 1.0D, 7.0D, 9.0D, 7.0D);
    public static final VoxelShape BOTTOM_SQUARE_2 = Block.makeCuboidShape(1.0D, 8.0D, 9.0D, 7.0D, 9.0D, 15.0D);
    public static final VoxelShape BOTTOM_SQUARE_3 = Block.makeCuboidShape(9.0D, 8.0D, 1.0D, 15.0D, 9.0D, 7.0D);
    public static final VoxelShape BOTTOM_SQUARE_4 = Block.makeCuboidShape(9.0D, 8.0D, 9.0D, 15.0D, 9.0D, 15.0D);
    public static final VoxelShape BOTTOM_SHAPE = VoxelShapes.or(BOTTOM_SLAB, BOTTOM_SQUARE_1, BOTTOM_SQUARE_2, BOTTOM_SQUARE_3, BOTTOM_SQUARE_4);
    public static final VoxelShape TOP_SLAB = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    public static final VoxelShape TOP_SQUARE_1 = Block.makeCuboidShape(1.0D, 16.0D, 1.0D, 7.0D, 17.0D, 7.0D);
    public static final VoxelShape TOP_SQUARE_2 = Block.makeCuboidShape(1.0D, 16.0D, 9.0D, 7.0D, 17.0D, 15.0D);
    public static final VoxelShape TOP_SQUARE_3 = Block.makeCuboidShape(9.0D, 16.0D, 1.0D, 15.0D, 17.0D, 7.0D);
    public static final VoxelShape TOP_SQUARE_4 = Block.makeCuboidShape(9.0D, 16.0D, 9.0D, 15.0D, 17.0D, 15.0D);
    public static final VoxelShape TOP_SHAPE = VoxelShapes.or(TOP_SLAB, TOP_SQUARE_1, TOP_SQUARE_2, TOP_SQUARE_3, TOP_SQUARE_4);
    public static final VoxelShape DOUBLE_SHAPE = VoxelShapes.or(VoxelShapes.fullCube(), TOP_SQUARE_1, TOP_SQUARE_2, TOP_SQUARE_3, TOP_SQUARE_4);

    public static final Collection<VoxelShape> BOTTOM_SHAPES = Collections.singleton(BOTTOM_SHAPE);
    public static final Collection<VoxelShape> TOP_SHAPES = Collections.singleton(TOP_SHAPE);
    public static final Collection<VoxelShape> DOUBLE_SHAPES = Arrays.asList(BOTTOM_SLAB, TOP_SHAPE);

    public ChocolateBlock(Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(TOP, Type.NONE).with(BOTTOM, Type.MILK));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TOP, BOTTOM);
    }

    public boolean isDouble(BlockState state) {
        return state.get(TOP) != Type.NONE && state.get(BOTTOM) != Type.NONE;
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return !this.isDouble(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (this.isDouble(state)) return DOUBLE_SHAPE;
        if (state.get(TOP) != Type.NONE) return TOP_SHAPE;
        else return BOTTOM_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (this.isDouble(state)) return VoxelShapes.fullCube();
        if (state.get(TOP) != Type.NONE) return TOP_SLAB;
        else return BOTTOM_SLAB;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        BlockState state = context.getWorld().getBlockState(pos);
        ItemStack stack = context.getItem();
        ChocolateBlockItem item = (ChocolateBlockItem)stack.getItem();
        if (state.getBlock() == this) {
            if (state.get(TOP) == Type.NONE) return state.with(TOP, item.getType());
            return state.with(BOTTOM, item.getType());
        } else {
            Direction direction = context.getFace();
            switch (direction) {
                case DOWN: return this.getDefaultState().with(TOP, item.getType()).with(BOTTOM, Type.NONE);
                case UP: return this.getDefaultState().with(BOTTOM, item.getType());
                default: {
                    boolean bottom = !(context.getHitVec().y - (double) pos.getY() > 0.5D);
                    return this.getDefaultState().with(bottom ? BOTTOM : TOP, item.getType()).with(bottom ? TOP : BOTTOM, Type.NONE);
                }
            }
        }
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            if (this.isDouble(state)) {
                this.removeHalf(state, world, pos, TOP, false, true);
                this.removeHalf(state, world, pos, BOTTOM, false, true);
            } else {
                this.removeHalf(state, world, pos, state.get(TOP) != Type.NONE ? BOTTOM : TOP, false, true);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack stack = useContext.getItem();
        if (!this.isDouble(state) && stack.getItem() instanceof ChocolateBlockItem) {
            if (useContext.replacingClickedOnBlock()) {
                boolean flag = useContext.getHitVec().y - (double)useContext.getPos().getY() > 0.5D;
                Direction direction = useContext.getFace();
                if (state.get(BOTTOM) != Type.NONE) {
                    return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    protected BlockState removeHalf(BlockState state, IWorld world, BlockPos pos, EnumProperty<Type> type, boolean effects, boolean drops) {
        BlockState singlePart = this.getDefaultState().with(BOTTOM, Type.NONE).with(type, state.get(type));

        if (effects) this.harvestPartEffects(singlePart, world, pos);
        if (drops && world instanceof World) this.spawnPartDrops(singlePart, (World)world, pos);

        BlockState ret = state.with(type, Type.NONE);
        if (ret.get(TOP) == Type.NONE && ret.get(BOTTOM) == Type.NONE) return Blocks.AIR.getDefaultState();
        return ret;
    }

    @Override
    public Collection<VoxelShape> getParts(BlockState state, IWorld world, BlockPos pos) {
        if (this.isDouble(state)) return DOUBLE_SHAPES;
        if (state.get(TOP) != Type.NONE) return TOP_SHAPES;
        else return BOTTOM_SHAPES;
    }

    @Override
    public boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        if (this.isDouble(state)) {
            world.setBlockState(pos, this.removeHalf(state, world, pos, shape == TOP_SHAPE ? TOP : BOTTOM, true, !player.isCreative()), 3);
            return true;
        }
        return false;
    }

    @Override
    public boolean addDestroyEffects(BlockState state, World world, BlockPos pos, ParticleManager manager) {
        return !this.onePart(state, world, pos);
    }

    @Override
    public SoundType getSoundType(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        return !this.onePart(state, (IWorld)world, pos) ? LCCSounds.chocolate_multiple : super.getSoundType(state, world, pos, entity);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBakedModel newModel(Block block) {
        return new ChocolateModel(block);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Collection<ResourceLocation> getTextures() {
        return Arrays.asList(
            new ResourceLocation(LCC.MODID, "block/rainbow/chocolate/dark"),
            new ResourceLocation(LCC.MODID, "block/rainbow/chocolate/milk"),
            new ResourceLocation(LCC.MODID, "block/rainbow/chocolate/white"),
            new ResourceLocation(LCC.MODID, "block/rainbow/chocolate/pink")
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Collection<ResourceLocation> getSpecialModels() {
        Collection<ResourceLocation> models = new ArrayList<>();
        for (ChocolateBlock.Type type : ChocolateBlock.Type.values()) {
            if (type == Type.NONE) continue;
            models.add(new ResourceLocation(LCC.MODID, "block/chocolate_" + type.name().toLowerCase() + "_bottom"));
            models.add(new ResourceLocation(LCC.MODID, "block/chocolate_" + type.name().toLowerCase() + "_top"));
        }
        return models;
    }

    @Override
    public MaterialColor getMaterialColor(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.get(TOP) == Type.NONE ? state.get(BOTTOM).mapColor : state.get(TOP).mapColor;
    }

    public enum Type implements IStringSerializable {
        NONE(null),
        DARK(MaterialColor.BROWN_TERRACOTTA),
        MILK(MaterialColor.OBSIDIAN),
        WHITE(MaterialColor.WHITE_TERRACOTTA),
        PINK(MaterialColor.PINK_TERRACOTTA);

        public final MaterialColor mapColor;

        Type(MaterialColor mapColor) {
            this.mapColor = mapColor;
        }

        @Override
        public String getName() {
            return this.name().toLowerCase();
        }

        @Override
        public String toString() {
            return this.getName();
        }

    }

}
