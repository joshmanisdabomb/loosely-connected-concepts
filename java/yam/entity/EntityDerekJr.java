package yam.entity;

import yam.YetAnotherMod;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityDerekJr extends EntityMob {

	public final float secondHeadRotation = this.getRNG().nextFloat() * ((float)Math.PI * 2);

	public EntityDerekJr(World par1World) {
		super(par1World);
		
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
		this.tasks.addTask(3, new EntityAIWander(this, 0.25F));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 5, true));
	}
	
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(14.0D);
	}
	
	public String getLivingSound() {
		return YetAnotherMod.MODID + ":mob.derek.say";
	}
	
	public String getHurtSound() {
		return YetAnotherMod.MODID + ":mob.derek.say";
	}
	
	public String getDeathSound() {
		return YetAnotherMod.MODID + ":mob.derek.death";
	}
	
	public float getSoundPitch() {
		return 2.0F;
	}
	
	public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
	
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(YetAnotherMod.derekJrSpawnEgg, 1);
    }
	
	protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(4))
        {
            case 0:
                this.dropItem(Item.getItemFromBlock(YetAnotherMod.derekHead), 1);
                break;
            case 1:
                this.dropItem(Item.getItemFromBlock(YetAnotherMod.derekHeart), 1);
                break;
            case 2:
                this.dropItem(Item.getItemFromBlock(YetAnotherMod.derekSoul), 1);
                break;
            case 3:
                this.dropItem(Item.getItemFromBlock(YetAnotherMod.derekSkin), 1);
                break;
        }
    }

}
