package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;
import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.data.world.SpreaderData;
import com.joshmanisdabomb.aimagg.util.MissileType;
import com.joshmanisdabomb.aimagg.util.OreIngotStorage;

import it.unimi.dsi.fastutil.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockSpreader extends AimaggBlockBasic implements AimaggBlockColored {
	
	public final EnumDyeColor dyeColor;
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
	
	public AimaggBlockSpreader(EnumDyeColor dyeColor, String internalName, Material material) {
		super(internalName, material, MapColor.getBlockColor(dyeColor));
		this.dyeColor = dyeColor;
		this.setLightOpacity(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, AGE);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AGE);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, meta);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		int speedMin = 84 - (SpreaderData.getInstance(worldIn).getSpeed(dyeColor) * 4);
    	worldIn.scheduleUpdate(pos, this, speedMin + this.RANDOM.nextInt(speedMin + 1));
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {		
		int speedMin = 84 - (SpreaderData.getInstance(worldIn).getSpeed(dyeColor) * 4);
	    worldIn.scheduleUpdate(pos, this, speedMin + this.RANDOM.nextInt(speedMin + 1));
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {		
		int speed = SpreaderData.getInstance(worldIn).getSpeed(dyeColor);
		int speedMin = 84 - (speed * 4);
		int range = SpreaderData.getInstance(worldIn).getRange(dyeColor);
		int spread = SpreaderData.getInstance(worldIn).getSpread(dyeColor);
		int maxFails = ((spread*2)+3)*((spread*2)+3)*((spread*2)+3);
		int age = state.getValue(AGE);
		boolean eating = SpreaderData.getInstance(worldIn).isEating(dyeColor);

		boolean inGround = SpreaderData.getInstance(worldIn).spreadsInGround(dyeColor);
		boolean inLiquid = SpreaderData.getInstance(worldIn).spreadsInLiquid(dyeColor);
		boolean inAir = SpreaderData.getInstance(worldIn).spreadsInAir(dyeColor);
		
		boolean destroy = false;
		int fails = 0;
		
		if (range == 65 || age < 15) {
			for (int i = -spread-1; i <= spread+1; i++) {
				for (int j = -spread-1; j <= spread+1; j++) {
					for (int k = -spread-1; k <= spread+1; k++) {
						IBlockState state2 = worldIn.getBlockState(pos.add(i, j, k));
						if (state2.getBlock() == this) {
							fails++;
						} else if (rand.nextInt(eating ? 4 : 10) == 0) {							
							if (
									(inLiquid && state2.getMaterial().isLiquid()) || 
									(inAir && state2.getMaterial() == Material.AIR) || 
									(inGround && (!state2.getMaterial().isLiquid() && !(state2.getMaterial() == Material.AIR))))
							{																
								if (range == 65) {
									worldIn.setBlockState(pos.add(i, j, k), state, 3);
								} else {
									worldIn.setBlockState(pos.add(i, j, k), state.withProperty(AGE, age+(rand.nextDouble() < (66-range)/70D ? 1 : 0)), 3);
								}
								
								worldIn.scheduleUpdate(pos.add(i, j, k), this, speedMin + rand.nextInt(speedMin + 1));
								destroy = eating;
							}
						}
					}
				}
			}

			if (destroy) {
				worldIn.setBlockToAir(pos);
			} else if (fails < maxFails) {
				worldIn.scheduleUpdate(pos, this, speedMin + rand.nextInt(speedMin + 1));
			}
		} else if (eating) {
			worldIn.setBlockToAir(pos);
		}
	
    }

	@Override
	public int getColorFromBlock(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return tintIndex == 0 ? this.dyeColor.getColorValue() : 0xFFFFFF;
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return tintIndex == 0 ? this.dyeColor.getColorValue() : 0xFFFFFF;
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
				return super.getUnlocalizedName();
			}
		};
		ib.setMaxDamage(0).setHasSubtypes(true);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}

}
