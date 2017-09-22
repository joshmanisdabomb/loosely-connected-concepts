package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicHalf.HalfType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockChocolate.ChocolateType;

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

public class AimaggBlockCandyCaneRefined extends AimaggBlockBasicAxis {

    public static final PropertyEnum<CandyCaneRefinedType> TYPE = PropertyEnum.<CandyCaneRefinedType>create("type", CandyCaneRefinedType.class);

	public AimaggBlockCandyCaneRefined(String internalName, Material material) {
		super(internalName, material, CandyCaneRefinedType.RED.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y).withProperty(TYPE, CandyCaneRefinedType.RED));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, AXIS});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.values()[meta % 3]).withProperty(TYPE, CandyCaneRefinedType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata() + state.getValue(AXIS).ordinal();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (CandyCaneRefinedType ccr : CandyCaneRefinedType.values()) {
        	items.add(new ItemStack(this, 1, ccr.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (CandyCaneRefinedType ccr : CandyCaneRefinedType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), ccr.getMetadata(), ccr.getModel());
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
				return super.getUnlocalizedName() + "." + CandyCaneRefinedType.getFromMetadata(stack.getMetadata()).getName();
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

	public static enum CandyCaneRefinedType implements IStringSerializable {
		RED(MapColor.RED),
		YELLOW(MapColor.YELLOW),
		GREEN(MapColor.LIME),
		BLUE(MapColor.BLUE);
		
		private final MapColor mapColor;
		private final ModelResourceLocation mrl;

		CandyCaneRefinedType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":rainbow/refined_candy_cane_" + this.getName());
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public static CandyCaneRefinedType getFromMetadata(int meta) {
			return CandyCaneRefinedType.values()[meta / EnumFacing.Axis.values().length];
		}

		public int getMetadata() {
			return this.ordinal() * EnumFacing.Axis.values().length;
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}

		public ModelResourceLocation getModel() {
			return this.mrl;
		}

	}

}
