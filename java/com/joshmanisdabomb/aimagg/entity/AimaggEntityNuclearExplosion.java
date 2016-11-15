package com.joshmanisdabomb.aimagg.entity;

import com.joshmanisdabomb.aimagg.AimaggBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AimaggEntityNuclearExplosion extends Entity {

	private static final DataParameter<Float> TICKSPROCESSED = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> STRENGTH = EntityDataManager.createKey(AimaggEntityMissile.class, DataSerializers.FLOAT);

	public AimaggEntityNuclearExplosion(World worldIn) {
		super(worldIn);
		this.setSize(0,0);
	}

	public AimaggEntityNuclearExplosion(World worldIn, double x, double y, double z) {
		this(worldIn);
        this.setPosition(x, y, z);
	}

	@Override
	protected void entityInit() {		
        this.getDataManager().register(TICKSPROCESSED, 0.0F);
        this.getDataManager().register(STRENGTH, 1.0F);
	}
	
	public void onUpdate() {
		if (this.getTicksProcessed() % 4 == 0 && !this.worldObj.isRemote) {
			final float r = this.getTicksProcessed() * 0.5F;
			final float optimisation_rsquared = r*r;
			for (int i = (int)Math.floor(-r); i <= (int)Math.floor(r); i++) {
				final int optimisation_isquared = i*i;
				for (int j = (int)Math.floor(-r); j <= (int)Math.floor(r); j++) {
					final int optimisation_jsquared = j*j;
					for (int k = (int)Math.floor(-r); k <= (int)Math.floor(r); k++) {
						final int optimisation_ksquared = k*k;
						BlockPos bp = new BlockPos(this.getPosition().getX()+i,this.getPosition().getY()+j,this.getPosition().getZ()+k);
						if (this.worldObj.canBlockSeeSky(bp.up(3)) ? (optimisation_isquared + optimisation_jsquared + optimisation_ksquared < optimisation_rsquared + rand.nextInt(13) - 6) : (optimisation_isquared + optimisation_jsquared + optimisation_ksquared < (optimisation_rsquared*0.4) + rand.nextInt(13) - 6)) {
							final IBlockState optimisation_blockstate = this.worldObj.getBlockState(bp);
							if (!optimisation_blockstate.getBlock().isAir(optimisation_blockstate, worldObj, bp) && optimisation_blockstate.getBlockHardness(worldObj, bp) >= 0) {
								if (optimisation_blockstate.getBlock() == AimaggBlocks.nuclearWaste || optimisation_blockstate.getBlock() == AimaggBlocks.nuclearFire) {
									;
								} else if (rand.nextInt(Math.max((int)(r*2F),1)) == 0) {
									this.worldObj.setBlockState(bp, AimaggBlocks.nuclearFire.getDefaultState(), 3);
								} else if (rand.nextInt(Math.max((int)(r*0.5F),1)) == 0) {
									this.worldObj.setBlockState(bp, AimaggBlocks.nuclearWaste.getDefaultState(), 3);
								} else if (rand.nextInt(Math.max((int)(r*0.09F),1)) == 0) {
							        this.worldObj.setBlockState(bp, Blocks.AIR.getDefaultState(), 3);
								} else if (rand.nextInt(Math.max((int)(r*0.09F),1)) == 0) {
					                this.worldObj.setBlockToAir(bp);
					                BlockPos blockpos;
	
					                for (blockpos = bp.down(); (this.worldObj.isAirBlock(blockpos) && blockpos.getY() > 0); blockpos = blockpos.down())
					                {
					                    ;
					                }
	
					                if (blockpos.getY() > 0)
					                {
					                	this.worldObj.setBlockState(blockpos.up(), optimisation_blockstate); //Forge: Fix loss of state information during world gen.
					                }
								}
							}
						}
					}
				}
			}
		}
		
		this.setTicksProcessed(this.getTicksProcessed() + 1);
		if (this.getTicksProcessed() > 10*this.getStrength()) {
			this.setDead();
		}
	}
	
	public void setTicksProcessed(float v) {
		this.getDataManager().set(TICKSPROCESSED, v);
	}
	
	public float getTicksProcessed() {
		return this.getDataManager().get(TICKSPROCESSED);
	}
	
	public void setStrength(float v) {
		this.getDataManager().set(STRENGTH, v);
	}
	
	public float getStrength() {
		return this.getDataManager().get(STRENGTH);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setTicksProcessed(compound.getFloat("TicksProcessed"));
		this.setStrength(compound.getFloat("Strength"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("TicksProcessed", this.getTicksProcessed());
		compound.setFloat("Strength", this.getStrength());
	}

}
