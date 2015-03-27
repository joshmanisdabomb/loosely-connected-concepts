package yam.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class EntityRainbot extends EntityMob {

	public float crystalSpeedX;
	public float crystalSpeedY;
	public float crystalSpeedZ;
	private final int shootTickMax = 9;
	private int shootTick = 0;

	public EntityRainbot(World par1World) {
		super(par1World);
		this.setSize(0.6F, 1.0F);

        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false, false));
        
        this.crystalSpeedX = (rand.nextFloat() / 10.0F) - 0.05F;
        this.crystalSpeedY = (rand.nextFloat() / 10.0F) - 0.05F;
        this.crystalSpeedZ = (rand.nextFloat() / 10.0F) - 0.05F;
	}
	
	protected boolean isAIEnabled() {
        return true;
    }
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(250.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(110.0D);
    }
	
	protected boolean canTriggerWalking() {
        return false;
    }
	
	public void fall() {}
	
	public boolean doesEntityNotTriggerPressurePlate() {return true;}
	
	/**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        return YetAnotherMod.MODID + ":mob.rainbot.idle";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return YetAnotherMod.MODID + ":mob.rainbot.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return YetAnotherMod.MODID + ":mob.rainbot.death";
    }
    
    protected String getKillSound()
    {
        return YetAnotherMod.MODID + ":mob.rainbot.kill";
    }

    protected float getSoundVolume() {
		return 1.0F;
    }
    
    protected float getSoundPitch() {
		return 1.0F;
    }
    
    public void setInWeb() {}
    
    public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(YetAnotherMod.rainbotSpawnEgg, 1);
    }

    public void onLivingUpdate() {
    	EntityLivingBase target = this.getAttackTarget();
    	if (target != null && shootTick-- <= 0 && !this.dead) {
	        this.attackEntityWithRangedAttack(target, 1.0F);
	        shootTick = shootTickMax;
    	}

        super.onLivingUpdate();
    }
    
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
    	boolean flag = super.attackEntityAsMob(p_70652_1_);
    	if (flag && ((EntityLivingBase)p_70652_1_).getHealth() <= 0.0F && ((EntityLivingBase)p_70652_1_).deathTime == 0) {
			this.playKillSound();
    	}
    	return flag;
    }
    
	public void attackEntityWithRangedAttack(EntityLivingBase par1Entity, float par2) {
		double d0 = par1Entity.posX - this.posX;
        double d1 = par1Entity.boundingBox.minY + (double)(par1Entity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
        double d2 = par1Entity.posZ - this.posZ;

        float f1 = MathHelper.sqrt_float(par2) * 0.5F;
        
        EntityBullet entitybullet = new EntityBullet(this.worldObj, this, d0 + this.rand.nextGaussian() * (double)f1, d1, d2 + this.rand.nextGaussian() * (double)f1);
        entitybullet.posY = this.posY + (double)(this.height / 2.0F);
        entitybullet.setDamage(40.0F);
        this.worldObj.spawnEntityInWorld(entitybullet);

        this.worldObj.playSoundAtEntity(this, YetAnotherMod.MODID + ":items.blaster", 0.5F, (rand.nextFloat()*0.2F)+0.9F);
	}

	public void playKillSound() {
		this.worldObj.playSoundAtEntity(this, this.getKillSound(), 3.0F, 1.0F);
	}
	
	protected Item getDropItem()
    {
        return Items.iron_ingot;
    }
	
	protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(2))
        {
            case 0:
                this.dropItem(YetAnotherMod.blaster, 1);
                break;
            case 1:
                this.dropItem(YetAnotherMod.rainbotCrystal, 1);
                break;
        }
    }

}
