package com.joshmanisdabomb.aimagg.blocks;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockChocolate.ChocolateType;
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
import net.minecraft.entity.Entity;
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
    
    public AimaggBlockBasicHalf(String internalName, IBlockState modelState) {
    	super(internalName, modelState.getMaterial(), MapColor.AIR);
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
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), HalfType.BOTTOM.ordinal(), new ModelResourceLocation(Constants.MOD_ID + ":shaped/" + this.getInternalName(), "inventory"));
	}

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return blockState.getValue(HALF) == HalfType.DOUBLE;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return blockState.getValue(HALF) == HalfType.DOUBLE;
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
				return super.getUnlocalizedName() + "." + HalfType.getFromMetadata(stack.getMetadata()).getName();
			}
			
			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				EnumActionResult ear = AimaggBlockBasicHalf.this.onItemBlockUse(this, player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
				return ear != null ? ear : super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
				Boolean b = AimaggBlockBasicHalf.this.canPlaceItemBlockOnSide(this, worldIn, pos, side, player, stack);
				return b != null ? b : super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}
	
	public EnumActionResult onItemBlockUse(ItemBlock ib, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
			if (worldIn.getBlockState(pos).getBlock() instanceof AimaggBlockBasicHalf) {
				if ((facing == EnumFacing.UP && worldIn.getBlockState(pos).getValue(HALF) == HalfType.BOTTOM || facing == EnumFacing.DOWN && worldIn.getBlockState(pos).getValue(HALF) == HalfType.TOP) && this.getStateFromMeta(itemstack.getMetadata()).equals(worldIn.getBlockState(pos).withProperty(HALF, HalfType.BOTTOM))) {
					if (worldIn.checkNoEntityCollision(HalfType.DOUBLE.getBoundingBox().offset(pos)) && worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(HALF, HalfType.DOUBLE), 11)) {
						SoundType soundtype = this.getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
						worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						itemstack.shrink(1);

						if (player instanceof EntityPlayerMP) {
							CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, itemstack);
						}
					}
					return EnumActionResult.SUCCESS;
				}
			}
			
			pos = pos.offset(facing);

			if (worldIn.getBlockState(pos).getBlock() instanceof AimaggBlockBasicHalf) {
				if (this.getStateFromMeta(itemstack.getMetadata()).equals(worldIn.getBlockState(pos).withProperty(HALF, HalfType.BOTTOM))) {
					if (worldIn.checkNoEntityCollision(HalfType.DOUBLE.getBoundingBox().offset(pos)) && worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(HALF, HalfType.DOUBLE), 11)) {
						SoundType soundtype = this.getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
						worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
						itemstack.shrink(1);
					}
					return EnumActionResult.SUCCESS;
				}
			}

	        return null;
		} else {
			return EnumActionResult.FAIL;
		}
    }
	
	public Boolean canPlaceItemBlockOnSide(ItemBlock ib, World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		if (worldIn.getBlockState(pos).getBlock() instanceof AimaggBlockBasicHalf && ((worldIn.getBlockState(pos).getValue(HALF) == HalfType.TOP && side == EnumFacing.DOWN) || (worldIn.getBlockState(pos).getValue(HALF) == HalfType.BOTTOM && side == EnumFacing.UP))) {
			return true;
		}
		return worldIn.getBlockState(pos.offset(side)).getBlock() instanceof AimaggBlockBasicHalf && this.getStateFromMeta(stack.getMetadata()).equals(worldIn.getBlockState(pos.offset(side)).withProperty(HALF, HalfType.BOTTOM)) ? true : null;
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

		public int getMetadata() {
			return this.ordinal();
		}
    	
		public static HalfType getFromMetadata(int meta) {
			return HalfType.values()[meta % HalfType.values().length];
		}
    	
    }

}
