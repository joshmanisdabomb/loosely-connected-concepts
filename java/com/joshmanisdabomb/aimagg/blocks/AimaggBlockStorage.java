package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.OreIngotStorage;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockStorage extends AimaggBlockBasic {

	public static int nextid = 0;
	private int id;
	
    public static final PropertyEnum<OreIngotStorage> TYPE = PropertyEnum.<OreIngotStorage>create("type", OreIngotStorage.class);

	public AimaggBlockStorage(String internalName, int sortVal, Material material) {
		super(internalName, sortVal, material, MapColor.IRON);
		this.id = nextid++;
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, OreIngotStorage.RUBY));
	}
	
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }
	
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, OreIngotStorage.getFromMetadata(this, meta));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getStorageMetadata();
    }
    
	@Override
	public int getSortValue(ItemStack is) {
		return super.getSortValue(is)+(is.getMetadata()*3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (OreIngotStorage ois : OreIngotStorage.getAllWithStorageForm()) {
        	items.add(new ItemStack(this, 1, ois.getStorageMetadata()));
        }
	}
	
	@Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return blockState.getValue(TYPE).getStorageHardness();
    }
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(TYPE).getStorageResistance();
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(TYPE).getStorageSoundType();
    }
	
	@Override
	public String getHarvestTool(IBlockState state) {
		return state.getValue(TYPE).getStorageHarvestTool();
	}
	
	@Override
	public int getHarvestLevel(IBlockState state) {
		return state.getValue(TYPE).getStorageHarvestLevel();
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return (state.getValue(TYPE)).getStorageMapColor();
    }

	@Override
	public void registerInventoryRender() {
        for (OreIngotStorage ois : OreIngotStorage.getAllWithStorageForm()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), ois.getStorageMetadata(), ois.getStorageModel());
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
				return super.getUnlocalizedName() + "." + OreIngotStorage.getFromID(stack.getMetadata()).getName();
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}

	public int getOISid() {
		return this.id;
	}

}
