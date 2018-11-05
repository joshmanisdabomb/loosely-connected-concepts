package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTEClassicChest;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AimaggBlockClassicChest extends AimaggBlockBasicFacingHorizontal implements ITileEntityProvider {

	public static final PropertyEnum<DoubleChestType> DOUBLE_TYPE = PropertyEnum.<DoubleChestType>create("type", DoubleChestType.class);
	
	public AimaggBlockClassicChest(String internalName, Material material, MapColor mcolor) {
		super(internalName, material, mcolor);
		this.isBlockContainer = true;
		this.setDefaultState(this.blockState.getBaseState().withProperty(HORIZONTAL_FACING, EnumFacing.NORTH).withProperty(DOUBLE_TYPE, DoubleChestType.SINGLE));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, HORIZONTAL_FACING, DOUBLE_TYPE);
	}

	@Override
	public ItemBlock createItemBlock() {
		ItemBlock ib = new ItemBlock(this);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing face = placer.getHorizontalFacing().getOpposite();
		for (EnumFacing f : EnumFacing.HORIZONTALS) {
			IBlockState other = worldIn.getBlockState(pos.offset(f));
			if (other.getBlock() instanceof AimaggBlockClassicChest) {
				EnumFacing face2 = other.getValue(HORIZONTAL_FACING);
				if (face2.getAxis() == face.getAxis()) {
					worldIn.setBlockState(pos, state.withProperty(HORIZONTAL_FACING, face));
					worldIn.setBlockState(pos.offset(f), state.withProperty(HORIZONTAL_FACING, face));
					return;
				} else {
					boolean currentChestLeftmost = face2.rotateY() == f;
					worldIn.setBlockState(pos, state.withProperty(HORIZONTAL_FACING, face2));
					worldIn.setBlockState(pos.offset(f), state.withProperty(HORIZONTAL_FACING, face2));
					return;
				}
			}
		}
		worldIn.setBlockState(pos, state.withProperty(HORIZONTAL_FACING, face));
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (worldIn.getBlockState(pos.offset(state.getValue(HORIZONTAL_FACING).rotateY())).getBlock() instanceof AimaggBlockClassicChest) {
			return state.withProperty(DOUBLE_TYPE, DoubleChestType.DOUBLE_LEFT);
		} else if (worldIn.getBlockState(pos.offset(state.getValue(HORIZONTAL_FACING).rotateYCCW())).getBlock() instanceof AimaggBlockClassicChest) {
			return state.withProperty(DOUBLE_TYPE, DoubleChestType.DOUBLE_RIGHT);
		}
		return state.withProperty(DOUBLE_TYPE, DoubleChestType.SINGLE);
	}
	
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		for (EnumFacing f : EnumFacing.HORIZONTALS) {
			IBlockState other = worldIn.getBlockState(pos.offset(f));
			if (other.getBlock() instanceof AimaggBlockClassicChest) {
				if (other.getBlock().getActualState(other, worldIn, pos.offset(f)).getValue(DOUBLE_TYPE) != DoubleChestType.SINGLE) {
					return false;
				}
			}
		}
        return super.canPlaceBlockAt(worldIn, pos);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTEClassicChest();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, (AimaggTEClassicChest)world.getTileEntity(pos));
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		AimaggTEClassicChest te = (AimaggTEClassicChest)worldIn.getTileEntity(pos);
		if (!worldIn.isRemote) {
			if (worldIn.getBlockState(pos.up()).isBlockNormalCube()) {
				return true;
			} else if (te.getNeighbour() != null && worldIn.getBlockState(te.getNeighbour().getPos().up()).isBlockNormalCube()) {
				return true;
			} else {
				playerIn.openGui(AimlessAgglomeration.instance, AimaggGUIHandler.ClassicChestID, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
	    }
		return true;
	}
	
	public static enum DoubleChestType implements IStringSerializable {
		SINGLE,
		DOUBLE_LEFT,
		DOUBLE_RIGHT;

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
		
	}

}
