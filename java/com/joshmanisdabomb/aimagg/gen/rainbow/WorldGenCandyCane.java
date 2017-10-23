package com.joshmanisdabomb.aimagg.gen.rainbow;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicAxis;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockCandyCane;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockCandyCane.CandyCaneType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass.RainbowGrassType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenCandyCane extends WorldGenAbstractTree {

	private final boolean bigCanes;

	public WorldGenCandyCane(boolean big, boolean notify) {
		super(notify);
		this.bigCanes = big;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (this.bigCanes) {
			for (int i = 0; i <= 1; i++) {
				for (int k = 0; k <= 1; k++) {
					if (!worldIn.getBlockState(position.add(i,-1,k)).equals(AimaggBlocks.rainbowGrass.getDefaultState().withProperty(AimaggBlockRainbowGrass.TYPE, RainbowGrassType.SUGAR))) {
						return false;
					}
				}
			}
			
			IBlockState candyCaneState = AimaggBlocks.candyCane.getDefaultState().withProperty(AimaggBlockCandyCane.TYPE, CandyCaneType.values()[rand.nextInt(CandyCaneType.values().length)]);
			EnumFacing direction = EnumFacing.HORIZONTALS[rand.nextInt(EnumFacing.HORIZONTALS.length)];
			boolean caneTipWidth3 = rand.nextBoolean();
			int caneHeight = rand.nextInt(10) + (caneTipWidth3 ? 18 + rand.nextInt(5) : 14);
			
			for (int i = direction.getFrontOffsetX() == -1 ? -8 : -1; i <= (direction.getFrontOffsetX() == 1 ? 9 : 2); i++) {
				for (int j = 0; j <= caneHeight + 1; j++) {
					for (int k = direction.getFrontOffsetZ() == -1 ? -8 : -1; k <= (direction.getFrontOffsetZ() == 1 ? 9 : 2); k++) {
						if (!worldIn.isAirBlock(position.add(i,j,k))) {
							return false;
						}
					}
				}
			}
			
			for (int j = 0; j < caneHeight; j++) {
				for (int i = 0; i <= 1; i++) {
					for (int k = 0; k <= 1; k++) {
						worldIn.setBlockState(position.add(i,j,k), candyCaneState);
					}
				}
			}
			for (int i = 0; i <= 1; i++) {
				for (int j = 0; j <= 1; j++) {
					for (int k = 0; k <= 1; k++) {
						worldIn.setBlockState(position.add(i,caneHeight + j - 1,k).offset(direction), candyCaneState);
						worldIn.setBlockState(position.add(i,caneHeight + j - 1,k).offset(direction, caneTipWidth3 ? 5 : 4), candyCaneState);
						worldIn.setBlockState(position.add(i,caneHeight + j - 2,k).offset(direction, caneTipWidth3 ? 6 : 5), candyCaneState);
					}
				}
			}
			for (int i = 0; i <= 1; i++) {
				for (int j = 0; j <= 1; j++) {
					for (int k = 0; k <= 1; k++) {
						for (int o = 0; o < (caneTipWidth3 ? 3 : 2); o++) {
							worldIn.setBlockState(position.add(i,caneHeight + j,k).offset(direction, o + 2), candyCaneState);
						}
					}
				}
			}
			return true;
		} else {
			if (worldIn.getBlockState(position.down()).equals(AimaggBlocks.rainbowGrass.getDefaultState().withProperty(AimaggBlockRainbowGrass.TYPE, RainbowGrassType.SUGAR))) {
				IBlockState candyCaneState = AimaggBlocks.candyCane.getDefaultState().withProperty(AimaggBlockCandyCane.TYPE, CandyCaneType.values()[rand.nextInt(CandyCaneType.values().length)]);
				EnumFacing direction = EnumFacing.HORIZONTALS[rand.nextInt(EnumFacing.HORIZONTALS.length)];
				boolean caneTipWidth2 = rand.nextBoolean();
				int caneHeight = rand.nextInt(7) + (caneTipWidth2 ? 6 + rand.nextInt(4) : 6);
				
				for (int i = direction.getFrontOffsetX() == -1 ? -4 : -1; i <= (direction.getFrontOffsetX() == 1 ? 4 : 1); i++) {
					for (int j = 0; j <= caneHeight + 1; j++) {
						for (int k = direction.getFrontOffsetZ() == -1 ? -4 : -1; k <= (direction.getFrontOffsetZ() == 1 ? 4 : 1); k++) {
							if (!worldIn.isAirBlock(position.add(i,j,k))) {
								return false;
							}
						}
					}
				}
				
				for (int i = 0; i < caneHeight; i++) {
					worldIn.setBlockState(position.up(i), candyCaneState);
				}
				worldIn.setBlockState(position.up(caneHeight).offset(direction), candyCaneState.withProperty(AimaggBlockBasicAxis.AXIS, direction.getAxis()));
				if (caneTipWidth2) {
					worldIn.setBlockState(position.up(caneHeight).offset(direction, 2), candyCaneState.withProperty(AimaggBlockBasicAxis.AXIS, direction.getAxis()));
				}
				worldIn.setBlockState(position.up(caneHeight - 1).offset(direction, caneTipWidth2 ? 3 : 2), candyCaneState);
				return true;
			}
			return false;
		}
	}

}
