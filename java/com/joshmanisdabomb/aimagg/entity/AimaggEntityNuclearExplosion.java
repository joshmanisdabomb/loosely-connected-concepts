package com.joshmanisdabomb.aimagg.entity;

import net.minecraft.block.material.Material;
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
		final float r = this.getTicksProcessed() * 0.6F;
		final float optimisation_rsquared = r*r;
		for (int i = (int)Math.floor(-r); i <= (int)Math.floor(r); i++) {
			final int optimisation_isquared = i*i;
			for (int j = (int)Math.floor(-r); j <= (int)Math.floor(r); j++) {
				final int optimisation_jsquared = j*j;
				for (int k = (int)Math.floor(-r); k <= (int)Math.floor(r); k++) {
					final int optimisation_ksquared = k*k;
					BlockPos bp = new BlockPos(this.getPosition().getX()+i,this.getPosition().getY()+j,this.getPosition().getZ()+k);
					if (optimisation_isquared + optimisation_jsquared + optimisation_ksquared < optimisation_rsquared*0.4 || (optimisation_isquared + optimisation_jsquared + optimisation_ksquared < optimisation_rsquared && this.worldObj.canBlockSeeSky(bp.up(1)))) {
						if (!this.worldObj.isAirBlock(bp) && this.worldObj.getBlockState(bp).getBlock().getBlockHardness(this.worldObj.getBlockState(bp), this.worldObj, bp) != -1 && (this.worldObj.getBlockState(bp).getMaterial() == Material.WATER) ? this.rand.nextInt(3) != 0 : this.rand.nextInt(9) == 0) {
							this.worldObj.setBlockToAir(bp);
						}
					}
				}
			}
		}
		
		this.setTicksProcessed(this.getTicksProcessed() + 1);
		if (this.getTicksProcessed() > 120*this.getStrength()) {
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
