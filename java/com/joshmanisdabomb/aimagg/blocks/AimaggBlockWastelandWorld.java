package com.joshmanisdabomb.aimagg.blocks;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockWastelandWorld extends AimaggBlockBasic {
	
    public static final PropertyEnum<WastelandWorldType> TYPE = PropertyEnum.<WastelandWorldType>create("type", WastelandWorldType.class);

	public AimaggBlockWastelandWorld(String internalName, Material material) {
		super(internalName, material, WastelandWorldType.CRACKED_MUD.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, WastelandWorldType.CRACKED_MUD));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, WastelandWorldType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (WastelandWorldType rw : WastelandWorldType.values()) {
        	items.add(new ItemStack(this, 1, rw.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (WastelandWorldType rw : WastelandWorldType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), rw.getMetadata(), rw.getModel());
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
				return super.getUnlocalizedName() + "." + WastelandWorldType.getFromMetadata(stack.getMetadata()).getName();
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
	
	public static enum WastelandWorldType implements IStringSerializable {

		CRACKED_MUD(MapColor.WOOD);
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		WastelandWorldType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":wasteland/" + this.getName());
    	}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public int getMetadata() {
			return this.ordinal();
		}

		public ModelResourceLocation getModel() {
			return this.mrl;
		}

		public static WastelandWorldType getFromMetadata(int meta) {
			return WastelandWorldType.values()[meta];
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
		
	}

}
