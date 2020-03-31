package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.block.shapes.RotatableShape;
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity;
import com.joshmanisdabomb.lcc.tileentity.AtomicBombTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class AtomicBombBlock extends ContainerBlock implements LCCBlockHelper {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final EnumProperty<Segment> SEGMENT = EnumProperty.create("segment", Segment.class);

    private static final RotatableShape HEAD = new RotatableShape(VoxelShapes.or(
        bodyCreator(4.6863, 0, 12).withOffset(0, 0, 0.25),
        bodyCreator(5.1005, 1, 1).withOffset(0, 0, 0.1875),
        bodyCreator(5.5147, 2, 1).withOffset(0, 0, 0.125),
        bodyCreator(5.9289, 3, 1).withOffset(0, 0, 0.0625),
        bodyCreator(6.3431, 4, 1)
    ));
    private static final RotatableShape BODY = new RotatableShape(bodyCreator(4.6863, 0, 16));
    private static final VoxelShape TAIL_1 = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape TAIL_2 = Block.makeCuboidShape(3.0D, 3.0D, 8.0D, 13.0D, 13.0D, 0.0D);
    private static final RotatableShape TAIL = new RotatableShape(VoxelShapes.or(TAIL_1, TAIL_2));

    public AtomicBombBlock(Properties p) {
        super(p);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(SEGMENT, Segment.MIDDLE));
    }

    private static VoxelShape bodyCreator(double bodyMinH, double bodyMinV, double bodyDepth) {
        double bodyWidth = 16 - (bodyMinH * 2);
        int points = (int)Math.floor(bodyWidth);
        VoxelShape ret = VoxelShapes.empty();
        for (int i = 0; i <= points; i++) {
            double j = (double)i / points;
            double k = 1 - j;
            ret = VoxelShapes.or(ret, Block.makeCuboidShape((bodyMinH * k) + (bodyMinV * j), (bodyMinH * j) + (bodyMinV * k), 0.0D, 16 - ((bodyMinH * k) + (bodyMinV * j)), 16 - ((bodyMinH * j) + (bodyMinV * k)), bodyDepth));
        }
        return ret;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, SEGMENT);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new AtomicBombTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return state.get(SEGMENT) == Segment.MIDDLE;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.isBlockPowered(pos)) {
            this.detonate(state, worldIn, pos, null);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(this.middle(state.get(SEGMENT), state.get(FACING), pos));
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, buf -> {
                    buf.writeBlockPos(tileEntity.getPos());
                });
            } else {
                throw new IllegalStateException("Named container provider missing.");
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }

    protected void detonate(BlockState state, World world, BlockPos pos, PlayerEntity entity) {
        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(this.middle(state.get(SEGMENT), state.get(FACING), pos));
            if (te instanceof AtomicBombTileEntity) {
                ((AtomicBombTileEntity)te).detonate(entity);
            }
        }
    }

    private BlockPos middle(Segment s, Direction facing, BlockPos pos) {
        switch (s) {
            case FRONT:
                return pos.offset(facing.getOpposite());
            case BACK:
                return pos.offset(facing);
            default:
                return pos;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(SEGMENT)) {
            case FRONT:
                return HEAD.get(state.get(FACING));
            case BACK:
                return TAIL.get(state.get(FACING));
            default:
                return BODY.get(state.get(FACING));
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (!world.isRemote) {
            world.setBlockState(pos.offset(state.get(FACING)), state.with(SEGMENT, Segment.FRONT), 3);
            world.setBlockState(pos.offset(state.get(FACING).getOpposite()), state.with(SEGMENT, Segment.BACK), 3);
            world.notifyNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, 3);
        }
        if (stack.hasDisplayName()) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof AtomicBombTileEntity) {
                ((AtomicBombTileEntity)tileentity).customName = stack.getDisplayName();
            }
        }
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof AtomicBombTileEntity) {
                ((AtomicBombTileEntity)te).inventory.ifPresent(h -> {
                    for (int i = 0; i < h.getSlots(); i++) {
                        InventoryHelper.spawnItemStack(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), h.extractItem(i, 64, false));
                    }
                });
                world.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        world.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(world));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(SEGMENT) == Segment.MIDDLE) {
            if (state.get(FACING) == facing && !isSegment(facingState, facing, Segment.FRONT)) return Blocks.AIR.getDefaultState();
            if (state.get(FACING) == facing.getOpposite() && !isSegment(facingState, facing.getOpposite(), Segment.BACK)) return Blocks.AIR.getDefaultState();
        } else if (state.get(SEGMENT) == Segment.BACK) {
            if (state.get(FACING) == facing && !isSegment(facingState, facing, Segment.MIDDLE)) return Blocks.AIR.getDefaultState();
        } else if (state.get(SEGMENT) == Segment.FRONT) {
            if (state.get(FACING) == facing.getOpposite() && !isSegment(facingState, facing.getOpposite(), Segment.MIDDLE)) return Blocks.AIR.getDefaultState();
        }
        world.getPendingBlockTicks().scheduleTick(currentPos, this, this.tickRate(world));
        return state;
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isRemote) {
            this.fall(state, world, this.middle(state.get(SEGMENT), state.get(FACING), pos));
        }
    }

    private void fall(BlockState state, World world, BlockPos pos) {
        Direction facing = state.get(FACING);
        if (this.canFall(world, pos) && this.canFall(world, pos.offset(facing)) && this.canFall(world, pos.offset(facing.getOpposite())) && pos.getY() >= 0) {
            if (!world.isRemote) {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof AtomicBombTileEntity) {
                    AtomicBombEntity e = new AtomicBombEntity(world, (double) ((float) pos.getX() + 0.5F), (double) pos.getY(), (double) ((float) pos.getZ() + 0.5F), facing, (AtomicBombTileEntity)te);
                    world.addEntity(e);
                    world.removeTileEntity(pos);
                    world.setBlockState(pos.offset(facing), Blocks.AIR.getDefaultState(), 18);
                    world.setBlockState(pos.offset(facing.getOpposite()), Blocks.AIR.getDefaultState(), 18);
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 18);
                }
            }
        }
    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 2;
    }

    private boolean canFall(World world, BlockPos pos) {
        return world.isAirBlock(pos.down()) || FallingBlock.canFallThrough(world.getBlockState(pos.down()));
    }

    private boolean isSegment(BlockState state, Direction d, Segment s) {
        return state.getBlock() == this && state.get(FACING) == d && state.get(SEGMENT) == s;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getPlacementHorizontalFacing().rotateY();
        BlockState state = this.getDefaultState().with(FACING, direction);
        BlockPos p1 = context.getPos().offset(direction);
        BlockPos p2 = context.getPos().offset(direction.getOpposite());
        if (!context.getWorld().getBlockState(p1).isReplaceable(context) || !context.getWorld().func_226663_a_(state, p1, ISelectionContext.dummy())) return null;
        if (!context.getWorld().getBlockState(p2).isReplaceable(context) || !context.getWorld().func_226663_a_(state, p2, ISelectionContext.dummy())) return null;
        return state;
    }

    public enum Segment implements IStringSerializable {

        FRONT,
        MIDDLE,
        BACK;

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
