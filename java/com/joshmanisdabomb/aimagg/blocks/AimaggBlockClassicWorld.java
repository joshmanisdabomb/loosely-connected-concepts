package com.joshmanisdabomb.aimagg.blocks;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
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

public class AimaggBlockClassicWorld extends AimaggBlockBasic {
	
    public static final PropertyEnum<ClassicWorldType> TYPE = PropertyEnum.<ClassicWorldType>create("type", ClassicWorldType.class);

	public AimaggBlockClassicWorld(String internalName, Material material) {
		super(internalName, material, ClassicWorldType.COBBLESTONE.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ClassicWorldType.COBBLESTONE));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, ClassicWorldType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (ClassicWorldType rw : ClassicWorldType.values()) {
        	items.add(new ItemStack(this, 1, rw.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (ClassicWorldType rw : ClassicWorldType.values()) {
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
				return super.getUnlocalizedName() + "." + ClassicWorldType.getFromMetadata(stack.getMetadata()).getName();
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
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    	worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    	worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    	if (!worldIn.isRemote) {
    		if (state.getValue(TYPE) == ClassicWorldType.GRAVEL) {
    			this.checkFallable(worldIn, pos, state);
    		} else if (state.getValue(TYPE) == ClassicWorldType.SPONGE) {
    			for (int i = -2; i <= 2; i++) {
    				for (int j = -2; j <= 2; j++) {
    					for (int k = -2; k <= 2; k++) {
    	    				if (worldIn.getBlockState(pos.add(i,j,k)).getMaterial().equals(Material.WATER)) {
    	    					worldIn.setBlockToAir(pos.add(i,j,k));
    	    				}
    	    			}
        			}
    			}
    			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    		}
        }
    }
    
    private void checkFallable(World worldIn, BlockPos pos, IBlockState state) {
		if ((worldIn.isAirBlock(pos.down()) || BlockFalling.canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0) {
			int i = 32;

			if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
				if (!worldIn.isRemote) {
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
					worldIn.spawnEntity(entityfallingblock);
				}
			} else {
				worldIn.setBlockToAir(pos);
				BlockPos blockpos;

				for (blockpos = pos.down(); (worldIn.isAirBlock(blockpos) || BlockFalling.canFallThrough(worldIn.getBlockState(blockpos))) && blockpos.getY() > 0; blockpos = blockpos.down()) {
					;
				}

				if (blockpos.getY() > 0) {
					worldIn.setBlockState(blockpos.up(), state);
				}
			}
		}
	}
    
    @Override
	public int tickRate(World worldIn) {
        return 1;
    }
	
	public static enum ClassicWorldType implements IStringSerializable {

		COBBLESTONE(MapColor.STONE),
		MOSSY_COBBLESTONE(MapColor.STONE),
		GRAVEL(MapColor.SILVER_STAINED_HARDENED_CLAY),
		BRICKS(MapColor.TNT),
		SPONGE(MapColor.YELLOW),
		IRON_BLOCK(MapColor.IRON),
		GOLD_BLOCK(MapColor.GOLD),
		DIAMOND_BLOCK(MapColor.DIAMOND),
		SMOOTH_IRON_BLOCK(MapColor.IRON),
		GLOWING_OBSIDIAN(MapColor.RED);
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		ClassicWorldType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":classic/" + this.getName());
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

		public static ClassicWorldType getFromMetadata(int meta) {
			return ClassicWorldType.values()[meta];
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
		
	}

}
