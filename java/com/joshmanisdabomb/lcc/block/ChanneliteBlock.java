package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ChanneliteBlock extends DirectionalBlock {

    public static final EnumProperty<ChanneliteConnection> CONNECTION = EnumProperty.create("connection", ChanneliteConnection.class);

    @Nullable
    private final DyeColor color;

    public ChanneliteBlock(DyeColor color, Properties p) {
        super(p);
        this.color = color;
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP).with(CONNECTION, ChanneliteConnection.NONE));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, CONNECTION);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (player.getHeldItem(hand).isEmpty()) {
            if (state.get(CONNECTION) == ChanneliteConnection.INVISIBLE) {
                world.setBlockState(pos, state.with(CONNECTION, this.getConnection(state.get(FACING), world, pos)));
            } else {
                world.setBlockState(pos, state.with(CONNECTION, ChanneliteConnection.INVISIBLE));
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction flow = context.getFace();
        BlockPos from = context.getPos().offset(flow.getOpposite());
        return LCCBlocks.channelite.get(this.getColorFrom(context.getWorld().getBlockState(from))).getDefaultState().with(FACING, flow).with(CONNECTION, this.getConnection(flow, context.getWorld(), context.getPos()));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        Direction d = state.get(FACING);
        if (state.get(CONNECTION) == ChanneliteConnection.INVISIBLE) return state;
        BlockState result = state;
        if (facing == d.getOpposite()) result = LCCBlocks.channelite.get(this.getColorFrom(facingState)).getDefaultState().with(FACING, d).with(CONNECTION, state.get(CONNECTION));
        if ((facing == d || facing == d.getOpposite())) result = result.with(CONNECTION, this.getConnection(d, world, currentPos));
        return result;
    }

    public DyeColor getColor() {
        return this.color;
    }

    protected ChanneliteConnection getConnection(Direction flow, IWorld world, BlockPos pos) {
        BlockPos up = pos.offset(flow);
        BlockPos down = pos.offset(flow.getOpposite());

        boolean top = world.getBlockState(up).getBlock() instanceof ChanneliteBlock && world.getBlockState(up).get(FACING) == flow;
        boolean bottom = world.getBlockState(down).getBlock() instanceof ChanneliteBlock && world.getBlockState(down).get(FACING) == flow;

        if (top && bottom) return ChanneliteConnection.BOTH;
        if (top) return ChanneliteConnection.TOP;
        if (bottom) return ChanneliteConnection.BOTTOM;
        return ChanneliteConnection.NONE;
    }

    protected DyeColor getColorFrom(BlockState state) {
        if (state.getBlock() instanceof ChanneliteBlock) return ((ChanneliteBlock)state.getBlock()).getColor();
        if (state.getBlock() instanceof ChanneliteSourceBlock) return ((ChanneliteSourceBlock)state.getBlock()).getColor();
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public RenderType getRenderLayer() {
        return RenderType.getTranslucent();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        Direction d = state.get(FACING);
        return ((side == d || side == d.getOpposite()) && (adjacentBlockState.getBlock() instanceof ChanneliteBlock && adjacentBlockState.get(FACING) == d)) || super.isSideInvisible(state, adjacentBlockState, side);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }

    public enum ChanneliteConnection implements IStringSerializable {
        NONE,
        TOP,
        BOTTOM,
        BOTH,
        INVISIBLE;

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
