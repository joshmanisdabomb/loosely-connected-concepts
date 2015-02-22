package yam.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class EntityMissile extends Entity {
	
	public EntityMissile(World world) {
		super(world);
        
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.motionY = -1.5;
	}

	public EntityMissile(World par1World, double par2, double par4, double par6) {
		this(par1World);
		
        float f = (float)(Math.random() * Math.PI * 2.0D);
        this.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
        this.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
	}
	
	public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 1.001;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
        	this.setDead();
        	explosion();
        }
        
        super.onUpdate();
	}

	public float getShadowSize()
    {
        return 0.0F;
    }
	
	abstract void explosion();
    
    @Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}
	
	protected void entityInit() {}
    
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }
	
}
