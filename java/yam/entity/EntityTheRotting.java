package yam.entity;

import yam.YetAnotherMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityTheRotting extends EntityMob implements IRangedAttackMob {

	public EntityTheRotting(World par1World) {
		super(par1World);
		
		this.setSize(1.5F, 0.5F);
        
        this.tasks.addTask(0, new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
		this.tasks.addTask(1, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 5, true));
	}
	
	public boolean canBePushed() {
		return false;
	}
	
	public boolean canTriggerWalking() {
		return true;
	}
	
	protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
    	return;
    }
	
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0.0D);
	}
	
	public ItemStack getPickedResult(MovingObjectPosition target)
    {
		return new ItemStack(YetAnotherMod.theRottingSpawnEgg, 1);
    }
	
	public boolean getCanSpawnHere() {
		AxisAlignedBB bb = this.boundingBox;
		bb = bb.expand(12.0D, 12.0D, 12.0D);
        return rand.nextInt(5) == 0 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(bb) && this.worldObj.getCollidingBoundingBoxes(this, bb).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
	
	public void attackEntityWithRangedAttack(EntityLivingBase par1Entity, float par2) {        
		if (this.onGround) { 
			this.motionY += 0.5D;
		}
		
        if (!this.worldObj.isRemote) {
			for (int i = 0; i < 3; i++) {
		        EntityFly fly = new EntityFly(worldObj);
		        fly.setPosition(this.posX,this.posY+(this.height/1.5F),this.posZ);
		        fly.motionX = (this.getRNG().nextDouble() * 0.5D) + 0.25D;
		        fly.motionY = (this.getRNG().nextDouble() * 0.5D) + 0.25D;
		        fly.motionZ = (this.getRNG().nextDouble() * 0.5D) + 0.25D;
		        fly.posY = this.posY + (double)(this.height / 2.0F);
		        this.worldObj.spawnEntityInWorld(fly);
		        fly.setAttackTarget(par1Entity);
		        fly.setRevengeTarget(par1Entity);
	        }
        }

        this.worldObj.playSoundAtEntity(this, YetAnotherMod.MODID + ":mob.rotting.vomit", 0.4F, 1.0F);
	}
	
    protected void fall(float par1) {}
	
	public void onUpdate() {
		this.motionX = 0.0D;
		this.motionZ = 0.0D;
		this.rotationPitch = 0.0F;
		this.rotationYaw = 0.0F;
		this.rotationYawHead = 0.0F;
		
		if (this.isInWater()) {
			this.setInWeb();
			this.attackEntityFrom(DamageSource.drown, 10.0F);
		}
		
		super.onUpdate();
	}
	
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
		if (par1DamageSource.getEntity() instanceof EntityLivingBase) {
			if (!(par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)(par1DamageSource.getEntity())).capabilities.isCreativeMode)) {
				this.attackEntityWithRangedAttack((EntityLivingBase)par1DamageSource.getEntity(), 0);
			}
		}
		return super.attackEntityFrom(par1DamageSource, par2);
    }
	
	public String getLivingSound() {
		return YetAnotherMod.MODID + ":mob.fly.say";
	}
	
	public void playLivingSound()
    {
        String s = this.getLivingSound();

        if (s != null)
        {
            this.playSound(s, this.getSoundVolume(), 0.5F);
        }
    }
	
	public String getHurtSound() {
		return YetAnotherMod.MODID + ":mob.rotting.hit";
	}
	
	public String getDeathSound() {
		return YetAnotherMod.MODID + ":mob.rotting.death";
	}

}
