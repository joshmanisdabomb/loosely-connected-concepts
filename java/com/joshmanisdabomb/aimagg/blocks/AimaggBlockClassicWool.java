package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockJelly.JellyType;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockClassicWool extends AimaggBlockBasic {

    public static final PropertyInteger COLOR = PropertyInteger.create("color", 0, 15);
	private final boolean classicColors;

	public AimaggBlockClassicWool(boolean classicColors, String internalName, Material material) {
		super(internalName, material, MapColor.SNOW);
		this.classicColors = classicColors;
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, 0));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {COLOR});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(COLOR, meta);
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i = 0; i < 16; i++) {
        	items.add(new ItemStack(this, 1, i));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (int i = 0; i < 16; i++) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), i, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName() + "/" + i));
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
				return super.getUnlocalizedName() + "." + stack.getMetadata();
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (classicColors) {
			return AimaggBlockClassicWool.getClassicMapColor(state.getValue(COLOR));
		} else {
			return MapColor.getBlockColor(EnumDyeColor.byMetadata(state.getValue(COLOR)));
		}
	}
	
	public static MapColor getClassicMapColor(int metadata) {
		switch(metadata) {
			case 1: return MapColor.SILVER;
			case 2: return MapColor.GRAY;
			case 3: return MapColor.TNT;
			case 4: return MapColor.ADOBE;
			case 5: return MapColor.GOLD;
			case 6: return MapColor.LIME;
			case 7: return MapColor.EMERALD;
			case 8: return MapColor.DIAMOND;
			case 9: return MapColor.DIAMOND;
			case 10: return MapColor.LIGHT_BLUE;
			case 11: return MapColor.LAPIS;
			case 12: return MapColor.PURPLE;
			case 13: return MapColor.MAGENTA;
			case 14: return MapColor.MAGENTA;
			case 15: return MapColor.PINK;
			default: return MapColor.SNOW;
		}
	}

}
