package com.joshmanisdabomb.lcc.block;

import com.joshmanisdabomb.lcc.data.capability.SpreaderCapability;
import com.joshmanisdabomb.lcc.tileentity.SpreaderInterfaceTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class SpreaderInterfaceBlock extends ContainerBlock implements LCCBlockHelper {

    private static final VoxelShape LEG1 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 5.0D, 2.0D);
    private static final VoxelShape LEG2 = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 5.0D, 2.0D);
    private static final VoxelShape LEG3 = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 2.0D, 5.0D, 16.0D);
    private static final VoxelShape LEG4 = Block.makeCuboidShape(14.0D, 0.0D, 14.0D, 16.0D, 5.0D, 16.0D);
    private static final VoxelShape BASE = Block.makeCuboidShape(1.0D, 3.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    private static final VoxelShape PORT = Block.makeCuboidShape(5.0D, 15.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private static final VoxelShape SHAPE = VoxelShapes.or(BASE, PORT, LEG1, LEG2, LEG3, LEG4);

    public SpreaderInterfaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return SHAPE;
    }

    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new SpreaderInterfaceTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (!world.isRemote) {
            SpreaderCapability.Provider.getGlobalCapability(world.getServer()).ifPresent(spreader -> {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof INamedContainerProvider) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, buf -> {
                        buf.writeBlockPos(tileEntity.getPos());
                        spreader.writeToPacket(buf);
                    });
                } else {
                    throw new IllegalStateException("Named container provider missing.");
                }
            });
            return true;
        }
        return true;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof SpreaderInterfaceTileEntity) {
                ((SpreaderInterfaceTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

}