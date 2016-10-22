package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTESpreaderConstructor;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AimaggBlockSpreaderConstructor extends AimaggBlockBasicHorizontal implements ITileEntityProvider {

	public AimaggBlockSpreaderConstructor(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(internalName, sortVal, material, mcolor);
		this.isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTESpreaderConstructor();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, (AimaggTESpreaderConstructor)world.getTileEntity(pos));
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
	        ((AimaggTESpreaderConstructor)world.getTileEntity(pos)).setCustomName((stack.getDisplayName()));
	    }
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
	    if (!world.isRemote) {
	        player.openGui(AimlessAgglomeration.instance, AimaggGUIHandler.SpreaderConstructorID, world, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}

}
