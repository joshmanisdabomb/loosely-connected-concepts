package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockClassicWorld.ClassicWorldType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockJelly.JellyType;
import com.joshmanisdabomb.aimagg.util.MissileType;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockComputerCable extends AimaggBlockBasicCable {

    public static final PropertyEnum<ComputerCableType> TYPE = PropertyEnum.<ComputerCableType>create("type", ComputerCableType.class);

	public AimaggBlockComputerCable(String internalName, Material material) {
		super(internalName, material, ComputerCableType.ALL.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ComputerCableType.ALL).withProperty(N, false).withProperty(E, false).withProperty(S, false).withProperty(W, false).withProperty(U, false).withProperty(D, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE,N,E,S,W,U,D});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, ComputerCableType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (ComputerCableType cc : ComputerCableType.values()) {
        	items.add(new ItemStack(this, 1, cc.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (ComputerCableType cc : ComputerCableType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), cc.getMetadata(), cc.getModel());
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
				return super.getUnlocalizedName();
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
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		
		if (ComputerCableType.getFromMetadata(stack.getMetadata()) != ComputerCableType.ALL) {
			tooltip.add(ComputerCableType.getFromMetadata(stack.getMetadata()).getTooltip());
		}
	}
	
	@Override
	public boolean doesConnectTo(IBlockAccess world, BlockPos pos, IBlockState self, IBlockState other, EnumFacing side) {
		if (other.getBlock() == AimaggBlocks.computerCase) {
			return true;
		}
		return other.getBlock() == this && (other.getValue(TYPE) == self.getValue(TYPE) || self.getValue(TYPE) == ComputerCableType.ALL || other.getValue(TYPE) == ComputerCableType.ALL);
	}
	
	public static enum ComputerCableType implements IStringSerializable {
		ALL(TextFormatting.DARK_GRAY, MapColor.STONE),
		MONITOR(TextFormatting.AQUA, MapColor.LIGHT_BLUE),
		DATA(TextFormatting.LIGHT_PURPLE, MapColor.PINK),
		POWER(TextFormatting.GOLD, MapColor.ADOBE);
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;
		private final TextFormatting textColor;

		ComputerCableType(TextFormatting tcolor, MapColor mcolor) {
			this.mapColor = mcolor;
			this.textColor = tcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":computing/computer_cable_" + this.getName());
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

		public static ComputerCableType getFromMetadata(int meta) {
			return ComputerCableType.values()[meta];
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}

		public TextFormatting getTextColor() {
			return this.textColor;
		}
		
		public String getTooltip() {
			return TextFormatting.GRAY + I18n.format("tooltip.aimagg:computer_cable.only", new Object[] {this.getTextColor(), I18n.format("tooltip.aimagg:computer_cable." + this.getName(), new Object[0])});
		}
	}

}
