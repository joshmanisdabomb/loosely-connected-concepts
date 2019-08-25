package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.network.BouncePadExtensionPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

public class BouncePadBlock extends Block {

    public static final IntegerProperty SETTING = IntegerProperty.create("setting", 0, 4);
    public static final double[] MOTION_VALUES = new double[]{1D, 1.4D, 1.8D, 2.2D, 2.6D};

    public static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);

    public BouncePadBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SETTING, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SETTING);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BouncePadTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        int newSetting = (state.get(SETTING)+1) % 5;
        world.setBlockState(pos, state.with(SETTING, newSetting));
        world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 0.4F, 0.8F + (newSetting * 0.1F), false);
        return true;
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance) {
        entity.fall(fallDistance, 0.0F);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.isFlying) return;
        entity.onGround = false;
        entity.fallDistance = 0.0F;
        entity.setPosition(pos.getX() + 0.5, pos.getY() + 0.99, pos.getZ() + 0.5);
        entity.setMotion(entity.getMotion().mul(1.0D, 0.0D, 1.0D).add(0.0D, MOTION_VALUES[state.get(SETTING)], 0.0D));

        if (!world.isRemote) {
            BouncePadTileEntity te = (BouncePadTileEntity) world.getTileEntity(pos);
            if (te != null) {
                LCCPacketHandler.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), new BouncePadExtensionPacket(pos, 1.8F * (state.get(SETTING) + 1)));
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

}
