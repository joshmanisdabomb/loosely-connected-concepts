package com.joshmanisdabomb.aimagg.blocks;

import java.util.Arrays;
import java.util.Collection;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSoft.SoftBlockType;
import com.joshmanisdabomb.aimagg.blocks.model.AimaggBlockConnectedTextureModel.AimaggBlockConnectedTextureBakedModel.TextureType;

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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockRainbowPad extends AimaggBlockBasicConnected {
	
    public static final PropertyEnum<RainbowPadType> TYPE = PropertyEnum.<RainbowPadType>create("type", RainbowPadType.class);

	public AimaggBlockRainbowPad(String internalName, Material material) {
		super(ConnectionType.STANDARD_THREE_TEXTURE, internalName, "rainbow/pad", material, RainbowPadType.ACTIVE.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, RainbowPadType.INACTIVE));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{TYPE}, new IUnlistedProperty[]{N,E,S,W,U,D,NE,SE,NW,SW,NU,EU,SU,WU,ND,ED,SD,WD});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, RainbowPadType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (RainbowPadType rp : RainbowPadType.values()) {
        	items.add(new ItemStack(this, 1, rp.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (RainbowPadType rp : RainbowPadType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), rp.getMetadata(), rp.getModel());
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
				return super.getUnlocalizedName() + "." + RainbowPadType.getFromMetadata(stack.getMetadata()).getName();
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return (state.getValue(TYPE)).getMapColor();
    }
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		ResourceLocation[] locations = new ResourceLocation[TextureType.values().length * RainbowPadType.values().length];
		for (int i = 0; i < RainbowPadType.values().length; i++) {
			locations[(i*TextureType.values().length)] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/top_base");
			locations[(i*TextureType.values().length)+1] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/top_corners");
			locations[(i*TextureType.values().length)+2] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/top_lines_h");
			locations[(i*TextureType.values().length)+3] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/top_lines_v");
			locations[(i*TextureType.values().length)+4] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/side_base");
			locations[(i*TextureType.values().length)+5] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/side_corners");
			locations[(i*TextureType.values().length)+6] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/side_lines_h");
			locations[(i*TextureType.values().length)+7] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/side_lines_v");
			locations[(i*TextureType.values().length)+8] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/bottom_base");
			locations[(i*TextureType.values().length)+9] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/bottom_corners");
			locations[(i*TextureType.values().length)+10] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/bottom_lines_h");
			locations[(i*TextureType.values().length)+11] = new ResourceLocation(Constants.MOD_ID, "blocks/" + textureFolder + "/" + RainbowPadType.values()[i].getName() + "/bottom_lines_v");
		}
		return Arrays.asList(locations);
	}
	
	public static enum RainbowPadType implements IStringSerializable {
		INACTIVE(MapColor.BLACK),
		ACTIVE(MapColor.GOLD);

		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		RainbowPadType(MapColor c) {
			this.mapColor = c;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":rainbow/pad_" + this.getName());
		}
		
		public ModelResourceLocation getModel() {
			return this.mrl;
		}

		public int getMetadata() {
			return this.ordinal();
		}

		public static RainbowPadType getFromMetadata(int meta) {
			return RainbowPadType.values()[meta];
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
		
	}

}
