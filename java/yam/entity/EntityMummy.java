package yam.entity;

import yam.YetAnotherMod;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent;

public class EntityMummy extends EntityZombie {

	public EntityMummy(World par1World)
    {
        super(par1World);
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setAvoidSun(true);
        this.tasks.taskEntries.clear();
        this.targetTasks.taskEntries.clear();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.setSize(0.6F, 1.8F);
    }
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.7D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(45.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.21D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
        this.getEntityAttribute(field_110186_bp).setBaseValue(this.rand.nextDouble() * ForgeModContainer.zombieSummonBaseChance * 2);
    }
	
	public boolean isVillager()
    {
        return false;
    }
	
	public void setVillager(boolean par1)
    {
		par1 = false;
        this.getDataWatcher().updateObject(13, Byte.valueOf((byte)(par1 ? 1 : 0)));
    }
	
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (!super.attackEntityFrom(par1DamageSource, par2))
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.getAttackTarget();

            if (entitylivingbase == null && this.getEntityToAttack() instanceof EntityLivingBase)
            {
                entitylivingbase = (EntityLivingBase)this.getEntityToAttack();
            }

            if (entitylivingbase == null && par1DamageSource.getEntity() instanceof EntityLivingBase)
            {
                entitylivingbase = (EntityLivingBase)par1DamageSource.getEntity();
            }


            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY);
            int k = MathHelper.floor_double(this.posZ);

            if (entitylivingbase != null && this.worldObj.difficultySetting == EnumDifficulty.HARD && (double)this.rand.nextFloat() < this.getEntityAttribute(field_110186_bp).getAttributeValue())
            {
                EntityMummy entitymummy = new EntityMummy(this.worldObj);

                for (int l = 0; l < 50; ++l)
                {
                    int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    int j1 = j + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                    int k1 = k + MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);

                    if (World.doesBlockHaveSolidTopSurface(this.worldObj, i1, j1 - 1, k1) && this.worldObj.getBlockLightValue(i1, j1, k1) < 10)
                    {
                    	entitymummy.setPosition((double)i1, (double)j1, (double)k1);

                        if (this.worldObj.checkNoEntityCollision(entitymummy.boundingBox) && this.worldObj.getCollidingBoundingBoxes(entitymummy, entitymummy.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(entitymummy.boundingBox))
                        {
                            this.worldObj.spawnEntityInWorld(entitymummy);
                            if (entitylivingbase != null) entitymummy.setAttackTarget(entitylivingbase);
                            entitymummy.onSpawnWithEgg((IEntityLivingData)null);
                            this.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
                            entitymummy.getEntityAttribute(field_110186_bp).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }
	
	protected Item getDropItem()
    {
        return Items.paper;
    }
	
	protected void dropRareDrop(int par1)
    {
        switch (this.rand.nextInt(3))
        {
            case 0:
                this.dropItem(Items.gold_ingot, 1);
                break;
            case 1:
                this.dropItem(Items.bone, 1);
                break;
            case 2:
                this.dropItem(Items.leather, 1);
        }
    }
	
	protected void addRandomArmor()
    {
        super.addRandomArmor();

        if (this.rand.nextFloat() < (this.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.05F : 0.01F))
        {
            int i = this.rand.nextInt(3);

            if (i == 0)
            {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
            }
            else
            {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_shovel));
            }
        }
    }
	
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
    {
        super.onKillEntity(par1EntityLivingBase);

        if ((this.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.worldObj.difficultySetting == EnumDifficulty.HARD) && par1EntityLivingBase instanceof EntityVillager)
        {
            if (this.rand.nextBoolean())
            {
                return;
            }

            EntityMummy entitymummy = new EntityMummy(this.worldObj);
            entitymummy.copyLocationAndAnglesFrom(par1EntityLivingBase);
            this.worldObj.removeEntity(par1EntityLivingBase);
            entitymummy.onSpawnWithEgg((IEntityLivingData)null);
            entitymummy.setVillager(true);

            if (par1EntityLivingBase.isChild())
            {
                entitymummy.setChild(true);
            }

            this.worldObj.spawnEntityInWorld(entitymummy);
            this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        }
    }
	
	public boolean interact(EntityPlayer par1EntityPlayer)
    {
		return false;
    }
	
	public String getLivingSound() {
		return YetAnotherMod.MODID + ":mob.mummy.say";
	}
	
	public String getHurtSound() {
		return YetAnotherMod.MODID + ":mob.mummy.hurt";
	}
	
	public String getDeathSound() {
		return YetAnotherMod.MODID + ":mob.mummy.death";
	}
	
	public ItemStack getPickedResult(MovingObjectPosition target)
    {
		return new ItemStack(YetAnotherMod.mummySpawnEgg, 1);
    }
	
	public boolean isChild()
    {
        return this.getDataWatcher().getWatchableObjectByte(12) == 1;
    }
	
	public void setChild(boolean par1) {}
}
