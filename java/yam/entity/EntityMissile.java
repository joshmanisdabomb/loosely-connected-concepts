package yam.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMissile extends Entity {
	
    public static final int radius = 4;

	public EntityMissile(World par1World) {
        super(par1World);
		
        this.preventEntitySpawning = true;
        this.setSize(0.5F, 1.5F);
        this.motionX = 0;
        this.motionY = -1.5;
        this.motionZ = 0;
		this.yOffset = this.height;
    }
	
	public EntityMissile(World par1World, double par2, double par4, double par6) {
		super(par1World);

        this.preventEntitySpawning = true;
        this.setSize(0.5F, 1.5F);
        this.motionX = 0;
        this.motionY = -1.5;
        this.motionZ = 0;
		this.yOffset = this.height;
        this.setPosition(par2, par4, par6);
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
	}
	
	@Override
	public void onUpdate() {        
        this.worldObj.spawnParticle("lava", this.posX, this.posY, this.posZ, 0.0D, 0.5D, 0.0D);
        
        super.onUpdate();
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY *= 1.001D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.onGround) {
        	this.setDead();
        	explosion();
        }
	}

	protected void explosion()
    {
    	Explosion explosion = new Explosion(this.worldObj, this, this.posX, this.posY, this.posZ, radius);
        explosion.doExplosionA();
        explosion.doExplosionB(true);
    }
	
	public float getShadowSize()
    {
        return 0.0F;
    }
    
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
