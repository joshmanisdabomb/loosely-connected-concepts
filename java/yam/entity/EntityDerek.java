package yam.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import yam.YetAnotherMod;

public class EntityDerek extends EntityMob implements IBossDisplayData {

	private int fireballTicks;

	public EntityDerek(World par1World) {
		super(par1World);

		this.getNavigator().setAvoidsWater(true);
		this.getNavigator().setEnterDoors(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAILeapAtTarget(this, 1.0F));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityDerek.class, 1.0F, false));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityDerekJr.class, 1.0F, false));
		this.tasks.addTask(5, new EntityAIWander(this, 0.25F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityDerek.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityDerekJr.class, 8.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityDerek.class, 0, true));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityDerekJr.class, 5, true));
		
		this.experienceValue = 200;
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(40.0D);
	}
	
	public String getLivingSound() {
		return null;
	}
	
	public String getHurtSound() {
		return YetAnotherMod.MODID + ":mob.derek.say";
	}
	
	public String getDeathSound() {
		return YetAnotherMod.MODID + ":mob.derek.death";
	}
	
	public float getSoundPitch() {
		return 1.0F;
	}
	
	public float getSoundVolume() {
		return 10.0F;
	}
	
	protected void fall(float par1) {
		if (par1 > 3 && !this.worldObj.isRemote) {
			this.playHurtSound();
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, Math.min(par1, 12), false);
		}
	}
	
	public void onUpdate() {
		if (!this.worldObj.isRemote && this.getAttackTarget() != null) {
			if (fireballTicks > 0) {
				if (fireballTicks % 4 == 0) {
					this.playHurtSound();
					double d0 = this.getAttackTarget().posX - this.posX;
			        double d1 = this.getAttackTarget().boundingBox.minY + (double)(this.getAttackTarget().height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
			        double d2 = this.getAttackTarget().posZ - this.posZ;

			        float f1 = MathHelper.sqrt_float(1.0F) * 0.5F;
			        
			        EntityLargeFireball fireball = new EntityLargeFireball(this.worldObj, this, d0 + this.rand.nextGaussian() * (double)f1, d1, d2 + this.rand.nextGaussian() * (double)f1);
			        fireball.posY = this.posY + (double)(this.height / 2.0F);
			        this.worldObj.spawnEntityInWorld(fireball);
		        }
				fireballTicks -= 1;
			}
			if (this.getRNG().nextInt(300) == 0) {
				this.jump();
			} 
			if (this.getRNG().nextInt(600) == 0) {
				fireballTicks = (this.getRNG().nextInt(6) + 6) * 4;
			}
		}
		super.onUpdate();
		if (this.getHealth() > 0) {this.setHealth(Math.min(this.getHealth()+0.04f,this.getMaxHealth()));}
	}
	
	public void playHurtSound() {
		String s = this.getHurtSound();
        if (s != null) {
            this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
        }
	}

	protected void jump()
    {
		this.playHurtSound();

		this.motionX *= 3.0D;
		this.motionZ *= 3.0D;
        this.motionY = 1.0D;

        if (this.isPotionActive(Potion.jump))
        {
            this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }
	
	public void onDeath(DamageSource src) {
		if (!worldObj.isRemote) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 8, false);
		}
		super.onDeath(src);
	}
	
	public boolean isSprinting() {
		return true;
	}
	
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (par1DamageSource.getEntity() != null && !(par1DamageSource.getSourceOfDamage() instanceof EntityArrow || par1DamageSource.getSourceOfDamage() instanceof EntityBullet)) {
			return super.attackEntityFrom(par1DamageSource, par2);
		} else {
			this.setHealth(Math.min(this.getHealth()+(par2/3),this.getMaxHealth()));
			return false;
		}
    }
	
	public boolean canDespawn() {
		return false;
	}
	
	public boolean attackEntityAsMob(Entity par1Entity) {
		this.playHurtSound();
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 4, false);
		return true;
	}

}
