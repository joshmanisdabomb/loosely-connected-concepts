package yam.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.explosion.Rainsplosion;

public class EntityLollipopper extends EntityCreature {
	
	public EntityLollipopper(World par1World) {
		super(par1World);
		this.setSize(0.5F, 4.0F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
	}
	
	protected boolean isAIEnabled()
    {
        return true;
    }
	
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}
	
	public ItemStack getPickedResult(MovingObjectPosition target)
    {
		return new ItemStack(YetAnotherMod.lollipopperSpawnEgg, 1);
    }
	
	protected String getLivingSound()
    {
        return YetAnotherMod.MODID + ":mob.lollipopper.quotes";
    }

    protected String getHurtSound()
    {
        return YetAnotherMod.MODID + ":mob.lollipopper.tickle";
    }
    
    public boolean isEntityInvulnerable() {
    	return false;
    }
    
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
    	if (this.hurtTime <= 0) {
            Entity entity = par1DamageSource.getEntity();

            if (entity != null && entity instanceof EntityPlayer)
            {
            	this.entityAge = 0;
                this.limbSwingAmount = 9.0F;
                this.hurtResistantTime = this.maxHurtResistantTime;
                this.hurtTime = this.maxHurtTime = 10;
                this.setRevengeTarget((EntityLivingBase)entity);
                this.setBeenAttacked();
                this.worldObj.setEntityState(this, (byte)2);

                double d1 = entity.posX - this.posX;
                double d0;

                for (d0 = entity.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
                {
                    d1 = (Math.random() - Math.random()) * 0.01D;
                }

                this.attackedAtYaw = (float)(Math.atan2(d0, d1) * 180.0D / Math.PI) - this.rotationYaw;
                this.knockBack(entity, par2, d1, d0);

                this.playSound(this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch()); 
            	
                if (!this.worldObj.isRemote) {
	                EntityItem item = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(YetAnotherMod.lollipop, 1));
	                item.delayBeforeCanPickup = 20;
	                worldObj.spawnEntityInWorld(item);
                }

                return true;
            }
        }
        return false;
    }
    
    public void explosion()
    {
    	Rainsplosion explosion = new Rainsplosion(this.worldObj, this, this.posX, this.posY, this.posZ, 10);
    	explosion.exploder = this;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
    }

}
