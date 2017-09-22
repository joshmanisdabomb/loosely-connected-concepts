package com.joshmanisdabomb.aimagg.blocks;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBillieTiles.BillieTileType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockScaffolding extends AimaggBlockBasic implements AimaggBlockColored {
	
	public static final PropertyBool MARKED = PropertyBool.create("marked");
	public static final PropertyEnum<ScaffoldingType> TYPE = PropertyEnum.<ScaffoldingType>create("type", ScaffoldingType.class);

	public static final AxisAlignedBB SCAFFOLDING_CORE = new AxisAlignedBB(1/32D, 0.0D, 1/32D, 31/32D, 1.0D, 31/32D);
	public static final AxisAlignedBB SCAFFOLDING_PLATFORM = new AxisAlignedBB(0.0D, 1.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	public static final AxisAlignedBB ENTITY_LEAFY_CHECK_RANGE = new AxisAlignedBB(-0.2D, 1.0D, -0.2D, 1.2D, 1.2D, 1.2D);

	public AimaggBlockScaffolding(String internalName, Material material) {
		super(internalName, material, ScaffoldingType.WOODEN.getMapColor());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, ScaffoldingType.WOODEN).withProperty(MARKED, false));
		this.setLightOpacity(0);
		this.setTickRandomly(true);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, MARKED});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, ScaffoldingType.getFromMetadata(meta % ScaffoldingType.values().length)).withProperty(MARKED, meta / ScaffoldingType.values().length == 1);
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMetadata() + (state.getValue(MARKED) ? ScaffoldingType.values().length : 0);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		for (ScaffoldingType s : ScaffoldingType.values()) {
			items.add(new ItemStack(this, 1, s.getMetadata()));
		}
	}

	@Override
	public void registerInventoryRender() {
		for (ScaffoldingType s : ScaffoldingType.values()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), s.getMetadata(), s.getModel());
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
				return super.getUnlocalizedName() + "." + ScaffoldingType.getFromMetadata(stack.getMetadata()).getName();
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
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (state.getValue(MARKED)) {
			this.markBlocks(worldIn, pos, state);
            this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		} else if (state.getValue(TYPE) == ScaffoldingType.LEAFY) {
			if (rand.nextInt(5) == 0) {
				List<Entity> entities = worldIn.getEntitiesWithinAABB(Entity.class, ENTITY_LEAFY_CHECK_RANGE.offset(pos));
				for (Entity e : entities) {
					if (e instanceof EntityPlayer) {
						return;
					}
				}
				worldIn.destroyBlock(pos, true);
			}
		}
    }
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		IBlockState state = worldIn.getBlockState(pos);
		if (playerIn.isSneaking()) {
			this.markBlocks(worldIn, pos, state);
            this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
		super.onBlockClicked(worldIn, pos, playerIn);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state.withProperty(MARKED, false));
    }
	
	private void markBlocks(World worldIn, BlockPos pos, IBlockState state) {
		for (EnumFacing f : EnumFacing.VALUES) {
			IBlockState other = worldIn.getBlockState(pos.offset(f));
			if (other.getBlock() instanceof AimaggBlockScaffolding && !other.getValue(MARKED) && other.getValue(TYPE) == state.getValue(TYPE)) {
				worldIn.setBlockState(pos.offset(f), other.withProperty(MARKED, true));
				worldIn.scheduleUpdate(pos.offset(f), this, 2);
			}
		}
	}

	@Override
	public int getColorFromBlock(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		if (tintIndex != 0 || state.getValue(MARKED) || state.getValue(TYPE) != ScaffoldingType.LEAFY) {
			return 0xFFFFFF;
		}
		int c = worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
		int r = (int)((255 - ((c >> 16) & 0xFF)) * 0.5F);
		int g = (int)((255 - ((c >> 8) & 0xFF)) * 0.5F);
		int b = (int)((255 - (c & 0xFF)) * 0.5F);
		return c + (r << 16) + (g << 8) + b;
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return this.getColorFromBlock(Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata()), null, null, tintIndex);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState other = blockAccess.getBlockState(pos.offset(side));
		return other.getBlock() != this;
	}
	
    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }
	
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, SCAFFOLDING_CORE);
    	if (entityIn != null && pos != null) {
    		if (entityIn.motionY <= 0 && entityIn.posY >= pos.getY()+1 && !entityIn.isSneaking()) {
    			addCollisionBoxToList(pos, entityBox, collidingBoxes, SCAFFOLDING_PLATFORM);
    		}
    	}
    }
    
    public boolean isTopSolid(IBlockState state) {
        return true;
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return p_193383_4_ == EnumFacing.UP || p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

	public static enum ScaffoldingType implements IStringSerializable {
		WOODEN(MapColor.BROWN),
		LEAFY(MapColor.WOOD);
		
		private final ModelResourceLocation mrl;
		private final MapColor mapColor;

		ScaffoldingType(MapColor mcolor) {
			this.mapColor = mcolor;
			this.mrl = new ModelResourceLocation(Constants.MOD_ID + ":scaffolding_" + this.getName());
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

		public static ScaffoldingType getFromMetadata(int meta) {
			return ScaffoldingType.values()[meta];
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}
	}

}
