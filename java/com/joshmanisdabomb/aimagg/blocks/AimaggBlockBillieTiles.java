package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;
import java.util.Random;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockChocolate.ChocolateType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockJelly.JellyType;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockBillieTiles extends AimaggBlockBasic {

	public static final PropertyInteger LIGHT = PropertyInteger.create("light", 0, 15);
	public static final PropertyEnum<BillieTileType> TYPE = PropertyEnum.<BillieTileType>create("type", BillieTileType.class);
	
	public static final AxisAlignedBB ENTITY_CHECK_RANGE = new AxisAlignedBB(0.0D, 1.0D, 0.0D, 1.0D, 1.2D, 1.0D);
	public static final AxisAlignedBB ARROW_CHECK_RANGE = new AxisAlignedBB(0.0D, 1.0D, 0.0D, 1.0D, 1.2D, 1.0D);
	
	public AimaggBlockBillieTiles(String internalName, Material material) {
		super(internalName, material, BillieTileType.STONE.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BillieTileType.STONE).withProperty(LIGHT, 0));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, LIGHT});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, BillieTileType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (BillieTileType btt : BillieTileType.values()) {
			items.add(new ItemStack(this, 1, btt.getMetadata()));
		}
	}

	@Override
	public void registerInventoryRender() {
		for (BillieTileType btt : BillieTileType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), btt.getMetadata(), btt.getModel());
		}
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(LIGHT);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		worldIn.scheduleUpdate(pos, this, 1);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.scheduleUpdate(pos, this, 1);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {		
		boolean lightUp = false;
		List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class, ENTITY_CHECK_RANGE.offset(pos));
		for (Entity e : entities) {
			if ((e instanceof EntityLivingBase || e instanceof EntityItem) && (e.posY >= pos.getY() + 1 && e.posY <= pos.getY() + 1.5D)) {
				lightUp = true;
			}
		}
				
		worldIn.setBlockState(pos, state.withProperty(LIGHT, Math.max(Math.min(state.getValue(LIGHT) + (lightUp ? 1 : -1), 15), 0)), 3);
		worldIn.scheduleUpdate(pos, this, 1);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
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
				return super.getUnlocalizedName() + "." + BillieTileType.getFromMetadata(stack.getMetadata()).getName();
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
	
	public static enum BillieTileType implements IStringSerializable {
		STONE(MapColor.STONE),
		COBBLESTONE(MapColor.STONE),
		GRANITE(MapColor.DIRT),
		DIORITE(MapColor.QUARTZ),
		ANDESITE(MapColor.STONE),
		FORTSTONE(MapColor.BLACK),
		TWILIGHT_STONE(MapColor.PURPLE),
		TWILIGHT_COBBLESTONE(MapColor.PURPLE);
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		BillieTileType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":illuminant_tile/" + this.getName());
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

		public static BillieTileType getFromMetadata(int meta) {
			return BillieTileType.values()[meta];
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
	}

}
