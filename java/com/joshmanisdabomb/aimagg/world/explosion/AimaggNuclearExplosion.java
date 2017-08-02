package com.joshmanisdabomb.aimagg.world.explosion;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSoft;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockSoft.SoftBlockType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AimaggNuclearExplosion extends AimaggExplosion {

	public AimaggNuclearExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean affectEntities, boolean playSound, boolean spawnParticles, boolean damagesTerrain, boolean flaming, float dropChance) {
		super(worldIn, entityIn, x, y, z, size, affectEntities, playSound, spawnParticles, damagesTerrain, flaming, dropChance);
	}
	
	@Override
	public float getEffectiveResistance(World world, IBlockState iblockstate, BlockPos blockpos) {
		return super.getEffectiveResistance(world, iblockstate, blockpos) / 100F;
	}
	
	@Override
	public void onBlockExploded(World world, IBlockState iblockstate, BlockPos pos) {
		if (iblockstate.getMaterial() != Material.AIR) {
			Block b = iblockstate.getBlock();
			if (this.random.nextInt(4) == 0) {
				world.setBlockToAir(pos);
			} else if (this.random.nextInt(4) == 0) {
				world.setBlockState(pos, AimaggBlocks.nuclearFire.getDefaultState());
			} else {
				world.setBlockState(pos, AimaggBlocks.soft.getDefaultState().withProperty(AimaggBlockSoft.TYPE, SoftBlockType.NUCLEAR_WASTE));
			}
		}
	}
	
	@Override
	public void placeFire(World world, IBlockState iblockstate, BlockPos pos) {
		if (!this.world.isRemote) {
			if (this.world.getBlockState(pos).getMaterial() == Material.AIR && this.random.nextInt(5) == 0) {
				this.world.setBlockState(pos, AimaggBlocks.nuclearFire.getDefaultState());
			}
		}
	}
	
}