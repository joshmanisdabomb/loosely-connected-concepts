package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.item.ComputingBlockItem;
import com.joshmanisdabomb.lcc.registry.LCCSounds;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.block.*;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ComputingBlock extends ContainerBlock implements LCCBlockHelper, MultipartBlock {

    public static final EnumProperty<SlabType> MODULES = EnumProperty.create("modules", SlabType.class);

    public static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    public static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final Collection<VoxelShape> BOTTOM_SHAPES = Collections.singleton(BOTTOM_SHAPE);
    public static final Collection<VoxelShape> TOP_SHAPES = Collections.singleton(TOP_SHAPE);
    public static final Collection<VoxelShape> DOUBLE_SHAPES = Arrays.asList(BOTTOM_SHAPE, TOP_SHAPE);

    public ComputingBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(MODULES, SlabType.BOTTOM));
    }

    protected BlockState removeModule(BlockState state, IWorld world, BlockPos pos, SlabType module, boolean effects, boolean drops) {
        BlockState singlePart = this.getDefaultState().with(MODULES, module);
        if (effects) this.harvestPartEffects(singlePart, world, pos);
        if (drops && world instanceof World) this.spawnPartDrops(singlePart, (World)world, pos);
        return state.with(MODULES, module == SlabType.TOP ? SlabType.BOTTOM : SlabType.TOP);
    }

    @Override
    public boolean func_220074_n(BlockState state) {
        return state.get(MODULES) != SlabType.DOUBLE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MODULES);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        SlabType s = state.get(MODULES);
        switch(s) {
            case DOUBLE: return VoxelShapes.fullCube();
            case TOP: return TOP_SHAPE;
            default: return BOTTOM_SHAPE;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        BlockState state = context.getWorld().getBlockState(pos);
        if (state.getBlock() == this) {
            return state.with(MODULES, SlabType.DOUBLE);
        } else {
            Direction direction = context.getFace();
            switch (direction) {
                case DOWN:
                    return this.getDefaultState().with(MODULES, SlabType.TOP);
                case UP:
                    return this.getDefaultState().with(MODULES, SlabType.BOTTOM);
                default:
                    return this.getDefaultState().with(MODULES, !(context.getHitVec().y - (double)pos.getY() > 0.5D) ? SlabType.BOTTOM : SlabType.TOP);
            }
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        ItemStack stack = useContext.getItem();
        SlabType module = state.get(MODULES);
        if (module != SlabType.DOUBLE && stack.getItem() instanceof ComputingBlockItem) {
            if (useContext.replacingClickedOnBlock()) {
                boolean flag = useContext.getHitVec().y - (double)useContext.getPos().getY() > 0.5D;
                Direction direction = useContext.getFace();
                if (module == SlabType.BOTTOM) {
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

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new ComputingTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public Collection<VoxelShape> getParts(BlockState state, IWorld world, BlockPos pos) {
        SlabType s = state.get(MODULES);
        switch(s) {
            case DOUBLE: return DOUBLE_SHAPES;
            case TOP: return TOP_SHAPES;
            default: return BOTTOM_SHAPES;
        }
    }

    @Override
    public boolean onShapeHarvested(BlockState state, IWorld world, BlockPos pos, PlayerEntity player, VoxelShape shape) {
        SlabType s = state.get(MODULES);
        if (s == SlabType.DOUBLE) {
            if (shape == TOP_SHAPE) world.setBlockState(pos, this.removeModule(state, world, pos, SlabType.TOP, true, !player.isCreative()), 3);
            else world.setBlockState(pos, this.removeModule(state, world, pos, SlabType.BOTTOM, true, !player.isCreative()), 3);
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
        return !this.onePart(state, (IWorld)world, pos) ? LCCSounds.cog_multiple : super.getSoundType(state, world, pos, entity);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

}
