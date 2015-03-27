package yam.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.entity.ai.EntityAIFleeSunWithoutBurning;

public class EntitySparklingDragon extends EntityMob implements IRangedAttackMob {
	
	public EntitySparklingDragon(World par1World) {
		super(par1World);
		this.setSize(1.0F, 1.0F);

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, new EntityAIFleeSunWithoutBurning(this, 1.0D));
        this.tasks.addTask(3, new EntityAIArrowAttack(this, 1.0D, 1, 1, 4.0F));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}
	
	public void entityInit() {
		super.entityInit();
        this.dataWatcher.addObject(16, 0.0F);
	}
	
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(YetAnotherMod.sparklingDragonSpawnEgg, 1);
    }
	
	protected boolean isAIEnabled() {
        return true;
    }
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(90.0D);
    }
	
	public void onUpdate() {
		super.onUpdate();
		
		this.dataWatcher.updateObject(16, Math.max(this.dataWatcher.getWatchableObjectFloat(16) - 0.2F, 0.0F));
	}

	public void attackEntityWithRangedAttack(EntityLivingBase par1Entity, float par2) {
		double d0 = par1Entity.posX - this.posX;
        double d1 = par1Entity.boundingBox.minY + (double)(par1Entity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
        double d2 = par1Entity.posZ - this.posZ;

        float f1 = 0.1F;
        
        for (int i = 0; i < 7; i++) {
	        EntitySparkle sparkle = new EntitySparkle(this.worldObj, this, d0 + this.rand.nextGaussian() * (double)f1, d1, d2 + this.rand.nextGaussian() * (double)f1);
	        sparkle.posY = this.posY + (double)(this.height / 2.0F);
	        this.worldObj.spawnEntityInWorld(sparkle);
        }

		this.dataWatcher.updateObject(16, Math.min(this.dataWatcher.getWatchableObjectFloat(16) + 0.4F, 1.0F));

        this.worldObj.playSoundAtEntity(this, YetAnotherMod.MODID + ":mob.sparklingdragon.fire", 0.4F, 1.0F);
	}
	
	protected Item getDropItem()
    {
        return Items.gold_nugget;
    }
	
	protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(2))
        {
            case 0:
                this.dropItem(YetAnotherMod.dragonHorn, 1);
                break;
            case 1:
                this.dropItem(YetAnotherMod.fairywand, 1);
                break;
        }
    }

}
