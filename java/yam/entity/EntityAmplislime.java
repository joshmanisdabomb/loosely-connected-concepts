package yam.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import yam.CustomPotion;
import yam.YetAnotherMod;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;

public class EntityAmplislime extends EntitySlime {

	private int slimeJumpDelay;

	public EntityAmplislime(World p_i1742_1_) {
		super(p_i1742_1_);
        this.slimeJumpDelay = getJumpDelay();
		this.setSlimeSize(1 << this.rand.nextInt(4));
	}
	
	protected void setSlimeSize(int p_70799_1_) {
        this.dataWatcher.updateObject(16, new Byte((byte)p_70799_1_));
        this.setSize(0.6F * (float)p_70799_1_, 0.6F * (float)p_70799_1_);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)(30 * p_70799_1_));
        this.setHealth(this.getMaxHealth());
        this.experienceValue = p_70799_1_;
    }
	
	public ParticleType getSlimeParticleType()
    {
        return ParticleType.AMPLISLIME;
    }
	
	public String getSlimeParticle()
    {
        return "";
    }
	
	protected boolean makesSoundOnLand()
    {
        return true;
    }
	
	public void onUpdate()
    {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getSlimeSize() > 0)
        {
            this.isDead = true;
        }

        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        boolean flag = this.onGround;
        super.onUpdate();
        int i;

        if (this.onGround && !flag)
        {
            i = this.getSlimeSize();

            if (worldObj.isRemote) {
	            for (int j = 0; j < i * 8; ++j)
	            {
	                float f = this.rand.nextFloat() * (float)Math.PI * 2.0F;
	                float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
	                float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
	                float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
	                ParticleHandler.spawnParticleClient(this.worldObj, this.getSlimeParticleType(), this.posX + (double)f2, this.boundingBox.minY, this.posZ + (double)f3, (rand.nextDouble()-0.5D)*0.2D, rand.nextDouble()*0.2D, (rand.nextDouble()-0.5D)*0.2D);
	            }
            }

            if (this.makesSoundOnLand())
            {
                this.playSound(this.getJumpSound(), 0.01F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }

            this.squishAmount = -1.5F;
        }
        else if (!this.onGround && flag)
        {
            this.squishAmount = 1.0F;
            this.motionY = 0.4D * ((this.getSlimeSize()+1)/3);
        }

        this.alterSquishAmount();

        if (this.worldObj.isRemote)
        {
            i = this.getSlimeSize();
            this.setSize(0.6F * (float)i, 0.6F * (float)i);
        }
    }
	
	protected String getJumpSound()
    {
        return "yam:mob.amplislime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }
	
	protected int getJumpDelay()
    {
        return rand.nextInt(2) + 2;
    }
	
	protected EntityAmplislime createInstance()
    {
        return new EntityAmplislime(this.worldObj);
    }
	
	public void setDead()
    {
        int i = this.getSlimeSize();

        if (!this.worldObj.isRemote && i > 1 && this.getHealth() <= 0.0F)
        {
            int j = 3 + this.rand.nextInt(3);

            for (int k = 0; k < j; ++k)
            {
                float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
                float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
                EntityAmplislime entityamplislime = this.createInstance();
                entityamplislime.setSlimeSize(i / 2);
                entityamplislime.setLocationAndAngles(this.posX + (double)f, this.posY + 0.5D, this.posZ + (double)f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(entityamplislime);
            }
        }

        super.setDead();
    }
	
	public void onCollideWithPlayer(EntityPlayer p_70100_1_)
    {
        int i = this.getSlimeSize();

        if (this.canEntityBeSeen(p_70100_1_) && this.getDistanceSqToEntity(p_70100_1_) < 0.6D * (double)i * 0.6D * (double)i && p_70100_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength()))
        {
        	PotionEffect amplify = p_70100_1_.getActivePotionEffect(CustomPotion.amplify);
        	if (amplify == null) {
        		p_70100_1_.addPotionEffect(new PotionEffect(CustomPotion.amplify.id, 200*(this.getSlimeSize()+1), 0));
            } else {
            	p_70100_1_.removePotionEffect(CustomPotion.amplify.id);
        		p_70100_1_.addPotionEffect(new PotionEffect(CustomPotion.amplify.id, 200*(this.getSlimeSize()+1), amplify.getAmplifier() >= 5 ? 5: amplify.getAmplifier()+1));
        	}
        	
        	this.playSound("yam:mob.amplislime.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        }
    }
	
	protected String getHurtSound()
    {
        return "yam:mob.amplislime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }
	
	protected String getDeathSound()
    {
        return "yam:mob.amplislime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    protected Item getDropItem()
    {
        return this.getSlimeSize() == 1 ? YetAnotherMod.amplifyBomb : Item.getItemById(0);
    }
    
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    protected int getAttackStrength()
    {
        return ((this.getSlimeSize()+1)/2) * 12;
    }
    
    protected boolean canDamagePlayer()
    {
        return true;
    }
    
    public boolean isPotionApplicable(PotionEffect p_70687_1_)
    {
        return p_70687_1_.getPotionID() == CustomPotion.amplify.id ? false : super.isPotionApplicable(p_70687_1_);
    }
    
    public ItemStack getPickedResult(MovingObjectPosition target) {
		return new ItemStack(YetAnotherMod.amplislimeSpawnEgg, 1);
    }
    
    protected void fall(float par1) {}

}
