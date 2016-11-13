package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTELaunchPad;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockLaunchPad extends AimaggBlockBasic implements ITileEntityProvider {
	
    public static final AxisAlignedBB LAUNCH_PAD_AABB_SELECTION = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 4/16D, 1.0D);
    public static final AxisAlignedBB LAUNCH_PAD_AABB_COLLISION_1 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 2/16D, 1.0D);
    public static final AxisAlignedBB LAUNCH_PAD_AABB_COLLISION_2 = new AxisAlignedBB(1/16D, 2/16D, 1/16D, 15/16D, 3/16D, 15/16D);
    public static final AxisAlignedBB LAUNCH_PAD_AABB_COLLISION_3 = new AxisAlignedBB(2/16D, 3/16D, 2/16D, 14/16D, 4/16D, 14/16D);
    public static final AxisAlignedBB LAUNCH_PAD_AABB_COLLISION_4 = new AxisAlignedBB(1/16D, 0.0D, 1/16D, 2/16D, 32/16D, 2/16D);
    public static final AxisAlignedBB LAUNCH_PAD_AABB_COLLISION_5 = new AxisAlignedBB(14/16D, 0.0D, 1/16D, 15/16D, 32/16D, 2/16D);
    public static final AxisAlignedBB LAUNCH_PAD_AABB_COLLISION_6 = new AxisAlignedBB(1/16D, 0.0D, 14/16D, 2/16D, 32/16D, 15/16D);
    public static final AxisAlignedBB LAUNCH_PAD_AABB_COLLISION_7 = new AxisAlignedBB(14/16D, 0.0D, 14/16D, 15/16D, 32/16D, 15/16D);

	public AimaggBlockLaunchPad(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(internalName, sortVal, material, mcolor);
		this.isBlockContainer = true;
		this.setLightOpacity(0);
		this.setTickRandomly(true);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTELaunchPad();
	}

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up()) && worldIn.isBlockNormalCube(pos.down(), false);
    }
    
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        super.neighborChanged(state, worldIn, pos, blockIn);
        this.checkAndDropBlock(worldIn, pos, state);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!(worldIn.isAirBlock(pos.up()) && worldIn.isBlockNormalCube(pos.down(), false))) {
    		worldIn.destroyBlock(pos, true);
        }
    }
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, (AimaggTELaunchPad)world.getTileEntity(pos));
		world.removeTileEntity(pos);
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity te = world.getTileEntity(pos);
        return te == null ? false : te.receiveClientEvent(id, param);
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	    if (stack.hasDisplayName()) {
	        ((AimaggTELaunchPad)world.getTileEntity(pos)).setCustomName((stack.getDisplayName()));
	    }
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
	    if (!world.isRemote) {
	        player.openGui(AimlessAgglomeration.instance, AimaggGUIHandler.LaunchPadID, world, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }
    
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return LAUNCH_PAD_AABB_SELECTION;
    }
    
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LAUNCH_PAD_AABB_COLLISION_1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LAUNCH_PAD_AABB_COLLISION_2);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LAUNCH_PAD_AABB_COLLISION_3);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LAUNCH_PAD_AABB_COLLISION_4);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LAUNCH_PAD_AABB_COLLISION_5);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LAUNCH_PAD_AABB_COLLISION_6);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LAUNCH_PAD_AABB_COLLISION_7);
    }

}
