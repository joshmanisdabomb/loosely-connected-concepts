package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicHalf.HalfType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockChocolate.ChocolateType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld.RainbowWorldType;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockChocolate extends AimaggBlockBasicHalf {

	public static final PropertyEnum<ChocolateType> TYPE = PropertyEnum.<ChocolateType>create("type", ChocolateType.class);

	//TODO possible to have multiple types of chocolate in the same block
	
	public AimaggBlockChocolate(String internalName, Material material) {
		super(internalName, material, ChocolateType.MILK.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, HalfType.BOTTOM).withProperty(TYPE, ChocolateType.MILK));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, HALF});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HALF, HalfType.getFromMetadata(meta)).withProperty(TYPE, ChocolateType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata() + state.getValue(HALF).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (ChocolateType c : ChocolateType.values()) {
        	items.add(new ItemStack(this, 1, c.getMetadata() + HalfType.BOTTOM.ordinal()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (ChocolateType c : ChocolateType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), c.getMetadata(), c.getModel());
        }
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
				return super.getUnlocalizedName() + "." + ChocolateType.getFromMetadata(stack.getMetadata()).getName();
			}
			
			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				EnumActionResult ear = AimaggBlockChocolate.this.onItemBlockUse(this, player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
				return ear != null ? ear : super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
				Boolean b = AimaggBlockChocolate.this.canPlaceItemBlockOnSide(this, worldIn, pos, side, player, stack);
				return b != null ? b : super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.getValue(TYPE).getMapColor();
    }

	public static enum ChocolateType implements IStringSerializable {
		MILK(MapColor.BROWN),
		WHITE(MapColor.BLACK_STAINED_HARDENED_CLAY),
		DARK(MapColor.WHITE_STAINED_HARDENED_CLAY);
		
		private final MapColor mapColor;
		private final ModelResourceLocation mrl;

		ChocolateType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":rainbow/" + this.getName() + "_chocolate");
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public static ChocolateType getFromMetadata(int meta) {
			return ChocolateType.values()[meta / HalfType.values().length];
		}

		public int getMetadata() {
			return this.ordinal() * HalfType.values().length;
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}

		public ModelResourceLocation getModel() {
			return this.mrl;
		}

	}

}
