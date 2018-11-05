package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;
import java.util.Random;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockWastelandWorld.WastelandWorldType;

import net.minecraft.block.Block;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockPoop extends AimaggBlockBasic {
	
	public static final AxisAlignedBB POOP_AABB_SELECTION   = new AxisAlignedBB(1/16D, 0.0D, 1/16D, 15/16D, 15/16D, 15/16D);
    public static final AxisAlignedBB POOP_AABB_COLLISION_1 = new AxisAlignedBB(1/16D, 0.0D, 1/16D, 15/16D, 4/16D, 15/16D);
    public static final AxisAlignedBB POOP_AABB_COLLISION_2 = new AxisAlignedBB(2/16D, 4/16D, 2/16D, 14/16D, 8/16D, 14/16D);
    public static final AxisAlignedBB POOP_AABB_COLLISION_3 = new AxisAlignedBB(4/16D, 8/16D, 4/16D, 12/16D, 11/16D, 12/16D);
    public static final AxisAlignedBB POOP_AABB_COLLISION_4 = new AxisAlignedBB(6/16D, 11/16D, 6/16D, 10/16D, 13/16D, 10/16D);

	public static final PropertyEnum<PoopType> TYPE = PropertyEnum.<PoopType>create("type", PoopType.class);

	public AimaggBlockPoop(String internalName, Material material) {
		super(internalName, material, PoopType.NORMAL.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, PoopType.NORMAL));
		this.setLightOpacity(0);
		this.setTickRandomly(true);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {TYPE});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, PoopType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (PoopType p : PoopType.values()) {
        	items.add(new ItemStack(this, 1, p.getMetadata()));
        }
	}

	@Override
	public void registerInventoryRender() {
        for (PoopType p : PoopType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), p.getMetadata(), p.getModel());
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
				return super.getUnlocalizedName() + "." + PoopType.getFromMetadata(stack.getMetadata()).getName();
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
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up()) && worldIn.isBlockNormalCube(pos.down(), false);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    	super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.checkAndDropBlock(worldIn, pos, state);
    }

	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!(worldIn.isAirBlock(pos.up()) && worldIn.isBlockNormalCube(pos.down(), false))) {
    		worldIn.destroyBlock(pos, true);
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }
    
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return POOP_AABB_SELECTION;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POOP_AABB_COLLISION_1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POOP_AABB_COLLISION_2);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POOP_AABB_COLLISION_3);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POOP_AABB_COLLISION_4);
    }
	
	public static enum PoopType implements IStringSerializable {
		
		NORMAL(MapColor.BROWN),
		CORNED(MapColor.OBSIDIAN),
		RED(MapColor.NETHERRACK),
		GOLD(MapColor.GOLD),
		RAINBOW(MapColor.MAGENTA);
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		PoopType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":wasteland/poop_" + this.getName());
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

		public static PoopType getFromMetadata(int meta) {
			return PoopType.values()[meta];
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
	}

}
