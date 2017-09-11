package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowWorld.RainbowWorldType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSpikes.SpikesType;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockBasicHalf extends AimaggBlockBasic {

	public static final PropertyEnum<HalfType> HALF = PropertyEnum.<HalfType>create("half", HalfType.class);
	
    public AimaggBlockBasicHalf(String internalName, Material material, MapColor mcolor) {
		super(internalName, material, mcolor);
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, HalfType.BOTTOM));
	}
    
    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
    	return state.getValue(HALF) == HalfType.DOUBLE ? 255 : 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(HALF).getBoundingBox();
    }

    @Override
    public boolean isTopSolid(IBlockState state) {
        return state.getValue(HALF) != HalfType.BOTTOM;
    }

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		if (p_193383_2_.getValue(HALF) == HalfType.DOUBLE || (p_193383_2_.getValue(HALF) == HalfType.TOP && p_193383_4_ == EnumFacing.UP) || (p_193383_2_.getValue(HALF) == HalfType.BOTTOM && p_193383_4_ == EnumFacing.DOWN)) {
			return BlockFaceShape.SOLID;
		} else {
			return BlockFaceShape.UNDEFINED;
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {HALF});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HALF, HalfType.getFromMetadata(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF).ordinal();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, HalfType.BOTTOM.ordinal()));
	}

	@Override
	public void registerInventoryRender() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), HalfType.BOTTOM.ordinal(), new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName() + "_bottom", "inventory"));
	}

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return blockState.getValue(HALF) == HalfType.DOUBLE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return true;//blockState.getValue(HALF) == HalfType.DOUBLE;
    }
    
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(HALF) == HalfType.DOUBLE;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return state.getValue(HALF) == HalfType.DOUBLE;
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

			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				System.out.println("hello");
				return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
				System.out.println("hello");
				return super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
			return super.doesSideBlockRendering(state, world, pos, face);

		if (state.isOpaqueCube())
			return true;

		return (state.getValue(HALF) == HalfType.TOP && face == EnumFacing.UP) || (state.getValue(HALF) == HalfType.BOTTOM && face == EnumFacing.DOWN);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (blockState.getValue(HALF) == HalfType.DOUBLE) {
			return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		} else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side)) {
			return false;
		}
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (HalfType.getFromMetadata(meta) == HalfType.DOUBLE) {
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(HALF, HalfType.DOUBLE);
		} else {
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(HALF, facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? HalfType.BOTTOM : HalfType.TOP);
		}
	}
    
    public static enum HalfType implements IStringSerializable {

    	BOTTOM(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)),
    	TOP(new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D)),
    	DOUBLE(Block.FULL_BLOCK_AABB);

    	private final AxisAlignedBB boundingBox;

		HalfType(AxisAlignedBB aabb) {
    		this.boundingBox = aabb;
    	}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
		
		public AxisAlignedBB getBoundingBox() {
			return this.boundingBox;
		}
    	
		public static HalfType getFromMetadata(int meta) {
			return HalfType.values()[meta % HalfType.values().length];
		}
    	
    }

}
