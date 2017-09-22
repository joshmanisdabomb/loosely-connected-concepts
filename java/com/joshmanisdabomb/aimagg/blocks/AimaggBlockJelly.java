package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld.RainbowWorldType;

import net.minecraft.block.Block;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockJelly extends AimaggBlockBasic implements AimaggBlockColored {

    public static final PropertyEnum<JellyType> TYPE = PropertyEnum.<JellyType>create("type", JellyType.class);
    
	public AimaggBlockJelly(String internalName, Material material) {
		super(internalName, material, JellyType.STRAWBERRY.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, JellyType.STRAWBERRY));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, JellyType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (JellyType j : JellyType.values()) {
        	items.add(new ItemStack(this, 1, j.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (JellyType j : JellyType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), j.getMetadata(), j.getModel());
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
				return super.getUnlocalizedName() + "." + JellyType.getFromMetadata(stack.getMetadata()).getName();
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

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return !blockAccess.getBlockState(pos.offset(side)).equals(blockState);
	}
	
	@Override
	public int getColorFromBlock(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return state.getValue(TYPE).getBlockColor();
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return JellyType.getFromMetadata(stack.getMetadata()).getBlockColor();
	}
	
	public static enum JellyType implements IStringSerializable {

		STRAWBERRY(0xAD0200, MapColor.RED),
		CHERRY(0x9B0022, MapColor.RED),
		GRAPE(0x8D2B84, MapColor.PURPLE),
		RASPBERRY(0xAE2B57, MapColor.MAGENTA_STAINED_HARDENED_CLAY),
		APPLE(0x7ED86A, MapColor.LIME_STAINED_HARDENED_CLAY),
		ORANGE(0xFFB247, MapColor.YELLOW_STAINED_HARDENED_CLAY),
		BLACKBERRY(0x441313, MapColor.GRAY_STAINED_HARDENED_CLAY),
		PEACH(0xFFC392, MapColor.WHITE_STAINED_HARDENED_CLAY),
		PINEAPPLE(0xFFDA7F, MapColor.SAND),
		WATERMELON(0xFF8CA5, MapColor.PINK),
		LEMON(0xFFFF56, MapColor.GOLD),
		CHOCOLATE(0x54341F, MapColor.OBSIDIAN),
		COLA(0x602815, MapColor.BROWN_STAINED_HARDENED_CLAY),
		BUBBLEGUM(0xFFA3E4, MapColor.PINK),
		LIME(0x8DCE42, MapColor.LIME),
		FRUIT_PUNCH(0xD1626E, MapColor.PINK_STAINED_HARDENED_CLAY);
		
		private final int blockColor;
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		JellyType(int color, MapColor mcolor) {
			this.blockColor = color;
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":rainbow/jelly");
			//this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":rainbow/jelly_" + this.getName());
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

		public static JellyType getFromMetadata(int meta) {
			return JellyType.values()[meta];
		}

		public int getBlockColor() {
			return this.blockColor;
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
		
	}

}
