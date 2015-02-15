package yam.entity;

import yam.YetAnotherMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityFly extends EntityMob {

	public EntityFly(World par1World) {
		super(par1World);

        this.getNavigator().setAvoidSun(true);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setCanSwim(false);
        
		this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
		this.tasks.addTask(1, new EntityAIWander(this, 0.25F));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 5, true));
		
		this.experienceValue = 1;
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(0.1D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
	}
	
	public ItemStack getPickedResult(MovingObjectPosition target)
    {
		return new ItemStack(YetAnotherMod.flySpawnEgg, 1);
    }
	
	public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
	
	public void onUpdate() {
		if (this.isInWater()) {
			this.attackEntityFrom(DamageSource.drown, 1);
		}
		
		super.onUpdate();
	}
	
	public String getLivingSound() {
		return YetAnotherMod.MODID + ":mob.fly.say";
	}
	
	public String getHurtSound() {
		return YetAnotherMod.MODID + ":mob.fly.say";
	}
	
	public String getDeathSound() {
		return YetAnotherMod.MODID + ":mob.fly.death";
	}

}
