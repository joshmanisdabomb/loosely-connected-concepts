package com.joshmanisdabomb.aimagg.blocks;

import java.util.Random;

import com.google.common.base.Predicate;
import com.joshmanisdabomb.aimagg.AimaggBlocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockClassicLeaves extends AimaggBlockBasic implements IShearable {

	public static final PropertyInteger DISTANCE_FROM_SUPPORT = PropertyInteger.create("distance", 0, 8);
	
    protected boolean leavesFancy;

	protected final Predicate<IBlockState> predicateLog;

	public AimaggBlockClassicLeaves(String internalName, MapColor mcolor, Predicate<IBlockState> log) {
		super(internalName, Material.LEAVES, mcolor);
		this.predicateLog = log;
		this.setTickRandomly(true);
		this.setLightOpacity(1);
		this.setDefaultState(this.blockState.getBaseState().withProperty(DISTANCE_FROM_SUPPORT, 1));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {DISTANCE_FROM_SUPPORT});
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DISTANCE_FROM_SUPPORT, meta);
    }

	@Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DISTANCE_FROM_SUPPORT);
    }
	
	protected void setDistance(IBlockState state, World worldIn, BlockPos pos) {
		int distance = 8;
		if (worldIn.getBlockState(pos.down()).getBlock() == this || predicateLog.apply(worldIn.getBlockState(pos.down()))) {
			worldIn.setBlockState(pos, state.withProperty(DISTANCE_FROM_SUPPORT, 2));
			return;
		}
		for (EnumFacing f : EnumFacing.HORIZONTALS) {
			BlockPos other = pos.offset(f);
			if (predicateLog.apply(worldIn.getBlockState(other))) {
				worldIn.setBlockState(pos, state.withProperty(DISTANCE_FROM_SUPPORT, 2));
				return;
			} else if (worldIn.getBlockState(other).getBlock() == this) {
				int otherDistance = worldIn.getBlockState(other).getValue(DISTANCE_FROM_SUPPORT);
				if (otherDistance == 1) {
					worldIn.setBlockState(pos, state.withProperty(DISTANCE_FROM_SUPPORT, 8));
					((AimaggBlockClassicLeaves)this).setDistance(worldIn.getBlockState(other), worldIn, other);
					worldIn.setBlockState(pos, state.withProperty(DISTANCE_FROM_SUPPORT, 1));
					distance = Math.min(distance, worldIn.getBlockState(other).getValue(DISTANCE_FROM_SUPPORT) + 1);
				} else if (otherDistance > 1) {
					distance = Math.min(distance, otherDistance + 1);
				}
			}
		}
		worldIn.setBlockState(pos, state.withProperty(DISTANCE_FROM_SUPPORT, distance));
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(DISTANCE_FROM_SUPPORT) == 1) {
			this.setDistance(state, worldIn, pos);
		}
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote && state.getValue(DISTANCE_FROM_SUPPORT) != 0) {
			this.setDistance(state, worldIn, pos);
			int distance = state.getValue(DISTANCE_FROM_SUPPORT);
			if (rand.nextInt(5) == 0 && distance == 8) {
				this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isTopSolid() && rand.nextInt(15) == 1) {
			double d0 = (double) ((float) pos.getX() + rand.nextFloat());
			double d1 = (double) pos.getY() - 0.05D;
			double d2 = (double) ((float) pos.getZ() + rand.nextFloat());
			worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	}

	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return !Minecraft.getMinecraft().gameSettings.fancyGraphics && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return true;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		int chance = 20;
		
		if (fortune > 0) {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }
		
		if (rand.nextInt(chance) == 0) {
            drops.add(new ItemStack(AimaggBlocks.classicSapling, 1, 0));
        }
	}

	@Override
	public NonNullList<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this, 1, 0));
	}

}
