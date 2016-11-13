package com.joshmanisdabomb.aimagg.entity.missile;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class AimaggEntityMissileExplosive extends AimaggEntityMissile {
	
	public static final ResourceLocation texture = new ResourceLocation("aimagg:textures/entity/missile/small/explosive.png");

	public AimaggEntityMissileExplosive(World worldIn) {
		super(worldIn);
	}

	public AimaggEntityMissileExplosive(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public ResourceLocation getEntityTexturePath() {
		return texture;
	}

	@Override
	public float getTopSpeed() {
		return 2.0F;
	}

	@Override
	public float getLaunchSpeed() {
		return 0.05F;
	}

	@Override
	public float getSpeedModifier() {
		return 1.1F;
	}

	@Override
	public void detonate() {
		if (!this.worldObj.isRemote) {
			Explosion explosion = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, 3.0F*this.getStrength(), false, true);
	        if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.worldObj, explosion)) {
	        	explosion.doExplosionA();
	        	explosion.doExplosionB(true);
	        }
		} else {
	        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
		}
	}
	
}
