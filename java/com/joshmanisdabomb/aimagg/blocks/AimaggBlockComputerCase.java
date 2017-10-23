package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockCandyCaneRefined.CandyCaneRefinedType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockJelly.JellyType;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTEComputerCase;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockComputerCase extends AimaggBlockBasicFacingAny implements ITileEntityProvider {
	
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor>create("color", EnumDyeColor.class);
	public static final PropertyBool POWER_STATE = PropertyBool.create("power_state");
	
	public AimaggBlockComputerCase(String internalName, Material material) {
		super(internalName, material, MapColor.IRON);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ANY_FACING, EnumFacing.NORTH).withProperty(COLOR, EnumDyeColor.SILVER).withProperty(POWER_STATE, false));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {ANY_FACING, COLOR, POWER_STATE});
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		AimaggTEComputerCase te = (AimaggTEComputerCase)worldIn.getTileEntity(pos);
		return state.withProperty(COLOR, EnumDyeColor.byMetadata(te.getColor())).withProperty(POWER_STATE, te.getPowerState());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (EnumDyeColor c : EnumDyeColor.values()) {
        	items.add(new ItemStack(this, 1, c.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (EnumDyeColor c : EnumDyeColor.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), c.getMetadata(), this.getInventoryModel(c));
        }
	}
	
	private ModelResourceLocation getInventoryModel(EnumDyeColor c) {
		return new ModelResourceLocation(Constants.MOD_ID + ":computing/computer_case_" + c);
	}

	@Override
	public ItemBlock createItemBlock() {
		ItemBlock ib = new ItemBlock(this) {
			@Override
			public int getMetadata(int metadata) {
				return metadata;
			}

			@Override
			public String getUnlocalizedName(ItemStack stack) {
				return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(stack.getMetadata());
			}

			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				IBlockState iblockstate = worldIn.getBlockState(pos);
				Block block = iblockstate.getBlock();

				if (!block.isReplaceable(worldIn, pos)) {
					pos = pos.offset(facing);
				}

				ItemStack itemstack = player.getHeldItem(hand);

				if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, (Entity) null)) {
					IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);

					if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
						iblockstate1 = worldIn.getBlockState(pos);
						SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
						worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						itemstack.shrink(1);
					}

					return EnumActionResult.SUCCESS;
				} else {
					return EnumActionResult.FAIL;
				}
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.getBlockColor(EnumDyeColor.byMetadata(((AimaggTEComputerCase)worldIn.getTileEntity(pos)).getColor()));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTEComputerCase();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, (AimaggTEComputerCase)world.getTileEntity(pos));
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
        world.setBlockState(pos, state.withProperty(ANY_FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)));
        ((AimaggTEComputerCase)world.getTileEntity(pos)).setColor((stack.getMetadata()));
	    if (stack.hasDisplayName()) {
	        ((AimaggTEComputerCase)world.getTileEntity(pos)).setCustomName((stack.getDisplayName()));
	    }
	    ((AimaggTEComputerCase)world.getTileEntity(pos)).markDirty();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(AimlessAgglomeration.instance, AimaggGUIHandler.ComputerCaseID, worldIn, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

}
