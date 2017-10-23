package com.joshmanisdabomb.aimagg.blocks;

import java.util.Random;

import com.google.common.base.Predicate;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld.RainbowWorldType;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockRainbowGrass extends AimaggBlockBasicGrass {
	
    public static final PropertyEnum<RainbowGrassType> TYPE = PropertyEnum.<RainbowGrassType>create("type", RainbowGrassType.class);

	public AimaggBlockRainbowGrass(String internalName, Material material, Predicate<IBlockState> growOver, IBlockState state) {
		super(internalName, material, RainbowGrassType.RAINBOW.getMapColor(), growOver, state);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, RainbowGrassType.RAINBOW));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, RainbowGrassType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (RainbowGrassType rw : RainbowGrassType.values()) {
        	items.add(new ItemStack(this, 1, rw.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (RainbowGrassType rw : RainbowGrassType.values()) {
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
				return super.getUnlocalizedName() + "." + RainbowGrassType.getFromMetadata(stack.getMetadata()).getName();
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
		
	public static enum RainbowGrassType implements IStringSerializable {
		
		RAINBOW(MapColor.MAGENTA),
		SUGAR(MapColor.SNOW),
		CHOCOLATE(MapColor.BROWN);
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		RainbowGrassType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":rainbow/" + this.getName() + "_grass");
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

		public static RainbowGrassType getFromMetadata(int meta) {
			return RainbowGrassType.values()[meta];
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
		
	}

}
