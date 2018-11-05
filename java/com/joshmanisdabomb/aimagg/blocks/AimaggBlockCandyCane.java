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

public class AimaggBlockCandyCane extends AimaggBlockBasicAxis {

    public static final PropertyEnum<CandyCaneType> TYPE = PropertyEnum.<CandyCaneType>create("type", CandyCaneType.class);

	public AimaggBlockCandyCane(String internalName, Material material) {
		super(internalName, material, CandyCaneType.RED.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y).withProperty(TYPE, CandyCaneType.RED));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE, AXIS});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.values()[meta % 3]).withProperty(TYPE, CandyCaneType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata() + state.getValue(AXIS).ordinal();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (CandyCaneType cc : CandyCaneType.values()) {
        	items.add(new ItemStack(this, 1, cc.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (CandyCaneType cc : CandyCaneType.values()) {
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
				return super.getUnlocalizedName() + "." + CandyCaneType.getFromMetadata(stack.getMetadata()).getName();
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

	public static enum CandyCaneType implements IStringSerializable {
		RED(MapColor.RED),
		YELLOW(MapColor.YELLOW),
		GREEN(MapColor.LIME),
		BLUE(MapColor.BLUE);
		
		private final MapColor mapColor;
		private final ModelResourceLocation mrl;

		CandyCaneType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":rainbow/candy_cane_" + this.getName());
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

		public static CandyCaneType getFromMetadata(int meta) {
			return CandyCaneType.values()[meta / EnumFacing.Axis.values().length];
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
