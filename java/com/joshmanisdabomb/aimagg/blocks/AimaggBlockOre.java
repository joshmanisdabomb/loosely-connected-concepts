package com.joshmanisdabomb.aimagg.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.joshmanisdabomb.aimagg.util.OreIngotStorage;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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

public class AimaggBlockOre extends AimaggBlockBasic {

	public static int nextid = 0;
	private int id;
	
    public static final PropertyEnum<OreIngotStorage> TYPE = PropertyEnum.<OreIngotStorage>create("type", OreIngotStorage.class);
    
	public AimaggBlockOre(String internalName, Material material) {
		super(internalName, material, MapColor.STONE);
		this.id = nextid++;
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, OreIngotStorage.RUBY));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, OreIngotStorage.getFromMetadata(this, meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getOreMetadata();
    }
    
	@Override
	public int getLowerSortValue(ItemStack is) {
		return is.getMetadata()*3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (OreIngotStorage ois : OreIngotStorage.getAllWithOreForm()) {
        	items.add(new ItemStack(this, 1, ois.getOreMetadata()));
        }
	}
	
	@Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return blockState.getValue(TYPE).getOreHardness();
    }
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(TYPE).getOreResistance();
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        return state.getValue(TYPE).getOreSoundType();
    }
	
	@Override
	public String getHarvestTool(IBlockState state) {
		return state.getValue(TYPE).getOreHarvestTool();
	}
	
	@Override
	public int getHarvestLevel(IBlockState state) {
		return state.getValue(TYPE).getOreHarvestLevel();
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return (state.getValue(TYPE)).getOreMapColor();
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        switch (state.getValue(TYPE).getOreType()) {
        	case DROP_SELF:
        		return Item.getItemFromBlock(this);
        	case DROP_INGOT: case DROP_INGOTS_LIKE_REDSTONE: case DROP_INGOTS_LIKE_LAPIS:
        		return state.getValue(TYPE).getIngotItem();
			default:
				return null;
        }
    }

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(this, 1, this.damageDropped(state));
    }
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		//TODO Add fortune support.
		switch (state.getValue(TYPE).getOreType()) {
	    	case DROP_SELF: case DROP_INGOT:
	    		return 1;
	    	case DROP_INGOTS_LIKE_REDSTONE:
	    		return 4 + random.nextInt(2);
	    	case DROP_INGOTS_LIKE_LAPIS:
	    		return 4 + random.nextInt(5);
			default:
				return 0;
	    }
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(TYPE).getOreLightValue();
	}
	
	@Override
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(TYPE).getOreLightOpacity();
	}

	@Override
	public void registerInventoryRender() {
        for (OreIngotStorage ois : OreIngotStorage.getAllWithOreForm()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), ois.getOreMetadata(), ois.getOreModel());
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
