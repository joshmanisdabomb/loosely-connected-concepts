package yam.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMissile extends Entity {
	
    public static final int radius = 6;
	private static final double maxSpeed = 0.1D;
    
	private double destX;
	private double destY;
	private double destZ;
	private double startX;
	private double startY;
	private double startZ;
	public double rotationX = 90;
	public double rotationZ = 90;
	private double distanceX;
	private double distanceY;
	private double distanceZ;
	private double velocityX;
	private double velocityY;
	private double velocityZ;

	public EntityMissile(World par1World) {
        super(par1World);
		
        this.preventEntitySpawning = true;
        this.setSize(0.5F, 1.5F);
		this.yOffset = this.height;
    }
	
	public EntityMissile(World par1World, double par2, double par4, double par6, double destX, double destY, double destZ) {
		super(par1World);

        this.preventEntitySpawning = true;
        this.setSize(0.5F, 1.5F);
        this.destX = destX + 0.5D;
        this.destY = destY + 0.5D;
        this.destZ = destZ + 0.5D;
		this.yOffset = this.height;
        this.setPosition(par2, par4, par6);
        this.startX = par2;
        this.startY = par4;
        this.startZ = par6;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
        this.distanceX = this.destX - this.startX;
        this.distanceY = this.destY - this.startY;
        this.distanceZ = this.destZ - this.startZ;
        
        float f2 = MathHelper.sqrt_double(Math.pow(this.distanceX, 2) + Math.pow(this.distanceY, 2) + Math.pow(this.distanceZ, 2));
        this.velocityX /= (double)f2;
        this.velocityY /= (double)f2;
        this.velocityZ /= (double)f2;
    }
	
	protected void entityInit()
    {
    }
	
	@Override
	public void onUpdate() {        
        this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

        this.onEntityUpdate();
        
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        this.moveEntity(this.velocityX, this.velocityY, this.velocityZ);

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
    
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

}
