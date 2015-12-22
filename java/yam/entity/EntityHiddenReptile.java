package yam.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public class EntityHiddenReptile extends EntityMob {
	
	public EntityHiddenReptile(World par1World) {
		super(par1World);
		this.setSize(1.0F, 1.0F);

        this.tasks.addTask(0, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false, false));
	}
	
	public boolean canBePushed() {
		return this.dataWatcher.getWatchableObjectFloat(16) < 1.0F;
	}
	
	public boolean getCanSpawnHere() {
		boolean flag = false;
		flag = this.worldObj.getBlock((int)Math.floor(this.posX) + 1, (int)Math.floor(this.posY), (int)Math.floor(this.posZ)) == YetAnotherMod.crackedMud || 
			   this.worldObj.getBlock((int)Math.floor(this.posX) - 1, (int)Math.floor(this.posY), (int)Math.floor(this.posZ)) == YetAnotherMod.crackedMud || 
			   this.worldObj.getBlock((int)Math.floor(this.posX), (int)Math.floor(this.posY), (int)Math.floor(this.posZ) + 1) == YetAnotherMod.crackedMud || 
			   this.worldObj.getBlock((int)Math.floor(this.posX), (int)Math.floor(this.posY), (int)Math.floor(this.posZ) - 1) == YetAnotherMod.crackedMud;
        return flag && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
	
	public void entityInit() {
		super.entityInit();
        this.dataWatcher.addObject(16, 0.0F);
	}
	
	public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(YetAnotherMod.hiddenReptileSpawnEgg, 1);
    }
	
	public void onUpdate() {
		super.onUpdate();
		
		if (this.isInWater()) {
			this.setInWeb();
			this.attackEntityFrom(DamageSource.drown, 10.0F);
		}
		
		if (this.dataWatcher.getWatchableObjectFloat(16) < 1.0F) {
			this.posX = Math.floor(this.posX) + 0.5D;
			this.posZ = Math.floor(this.posZ) + 0.5D;
			this.motionX = 0.0D;
			this.motionZ = 0.0D;
			this.rotationYaw = 0;
			this.rotationPitch = 0;
			this.rotationYawHead = 0;
		} else {
			if (this.getEntityAttribute(SharedMonsterAttributes.followRange).getAttributeValue() < 8.0F) {
				this.tasks.taskEntries.clear();
				this.tasks.addTask(0, new EntityAILeapAtTarget(this, 0.5F));
				this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0F, false));
				this.tasks.addTask(2, new EntityAIWander(this, 0.7F));
				this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
				this.tasks.addTask(4, new EntityAILookIdle(this));
				this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0F);
			}
		}
		
		if ((this.getAttackTarget() != null || this.getAITarget() != null) || this.dataWatcher.getWatchableObjectFloat(16) > 0.0F) {
			this.dataWatcher.updateObject(16, Math.min(this.dataWatcher.getWatchableObjectFloat(16) + 0.05F, 1.0F));
		}
	}
	
	protected boolean isAIEnabled() {
        return true;
    }
	
	public void wakeUp(EntityLivingBase attack) {
		this.dataWatcher.updateObject(16, Math.min(this.dataWatcher.getWatchableObjectFloat(16) + 0.05F, 1.0F));
		this.setAttackTarget(attack);
		this.setRevengeTarget(attack);
	}
	
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
		if (par1DamageSource.getEntity() instanceof EntityLivingBase) {
			//calling friends
			AxisAlignedBB aabb = this.boundingBox.expand(20.0D, 10.0D, 20.0D);
			int counter = 0;
			for (Object e : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, aabb)) {
				if (e instanceof EntityHiddenReptile && this.getRNG().nextInt(3) == 0 && !this.worldObj.isRemote) {
					counter++;
					((EntityHiddenReptile) e).wakeUp((EntityLivingBase)par1DamageSource.getEntity());
				}
			}
			
			//break from actual blocks if theres 5 of them
			if (counter > 3 && !this.worldObj.isRemote) {
				for (int i = -10; i <= 10; i++) {
					for (int j = -5; j <= 5; j++) {
						for (int k = -10; k <= 10; k++) {
							if (this.worldObj.rand.nextInt(26) == 0 && this.worldObj.getBlock(i+(int)Math.floor(this.posX), j+(int)Math.floor(this.posY), k+(int)Math.floor(this.posZ)) == YetAnotherMod.crackedMud && this.worldObj.canBlockSeeTheSky(i+(int)Math.floor(this.posX), j+(int)Math.floor(this.posY), k+(int)Math.floor(this.posZ))) {
								this.worldObj.func_147480_a(i+(int)Math.floor(this.posX), j+(int)Math.floor(this.posY), k+(int)Math.floor(this.posZ), false);
								EntityHiddenReptile hr = new EntityHiddenReptile(worldObj);
								hr.setPosition(i+(int)Math.floor(this.posX) + 0.5D, j+(int)Math.floor(this.posY) + 0.5D, k+(int)Math.floor(this.posZ) + 0.5D);
								hr.motionY = (rand.nextFloat() * 0.5F) + 0.5F;
								this.worldObj.spawnEntityInWorld(hr);
								hr.wakeUp((EntityLivingBase)par1DamageSource.getEntity());
							}
						}
					}
				}
			}
						
			//pickaxe is better
			if (((EntityLivingBase)(par1DamageSource.getEntity())).getHeldItem() != null && ((EntityLivingBase)(par1DamageSource.getEntity())).getHeldItem().getItem() instanceof ItemPickaxe) {
				return super.attackEntityFrom(par1DamageSource, par2 * 1.5F);
			} else {
				return super.attackEntityFrom(par1DamageSource, par2 * 0.3F);
			}
		}
		return super.attackEntityFrom(par1DamageSource, par2);
    }
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(47.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.32D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(18.0D);
    }
	
	
	
	

}
