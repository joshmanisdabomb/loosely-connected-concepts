package yam.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class EntityPsychoPig extends EntityMob {

	public EntityPsychoPig(World par1World) {
		super(par1World);
		
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.5F));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
		this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPig.class, 1.0F, false));
		this.tasks.addTask(5, new EntityAIWander(this, 0.7F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPig.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 5, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPig.class, 5, true));
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(28.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
	}
	
	public ItemStack getPickedResult(MovingObjectPosition target)
    {
		return new ItemStack(YetAnotherMod.psychopigSpawnEgg, 1);
    }
	
	public String getLivingSound() {
		return YetAnotherMod.MODID + ":mob.psycho.say";
	}
	
	public String getHurtSound() {
		return YetAnotherMod.MODID + ":mob.psycho.say";
	}
	
	public String getDeathSound() {
		return YetAnotherMod.MODID + ":mob.psycho.death";
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		if (par1Entity instanceof EntityLivingBase) {
			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 40, 0));
			((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 150, 0));
		}
		super.attackEntityAsMob(par1Entity);
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		if (this.hurtTime <= 0) {
            Entity entity = par1DamageSource.getEntity();

            if (entity != null && entity instanceof EntityPlayer) {
            	ItemStack i = ((EntityPlayer)entity).getCurrentEquippedItem();
            	if (i != null && i.getItem() == YetAnotherMod.cheese) {
            		i.damageItem(1, ((EntityPlayer)entity));
            	}
            }
		}
        return super.attackEntityFrom(par1DamageSource, par2);
    }
	
	public boolean getCanSpawnHere() {
        return rand.nextInt(5) == 0 && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

}
