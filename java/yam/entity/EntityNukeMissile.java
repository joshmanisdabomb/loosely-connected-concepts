package yam.entity;

import yam.explosion.NuclearExplosion;
import net.minecraft.world.World;

public class EntityNukeMissile extends EntityMissile {
	
    public static final int radius = 100;

	public EntityNukeMissile(World par1World) {
        super(par1World);
    }
	
	public EntityNukeMissile(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}
	
	public void onUpdate()
    {        
        this.worldObj.spawnParticle("lava", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.5D, 0.0D);
        super.onUpdate();
    }

	protected void explosion()
    {
    	NuclearExplosion explosion = new NuclearExplosion(this.worldObj, this, this.posX, this.posY, this.posZ, radius);
        explosion.doExplosionA();
        explosion.doExplosionB(true);
    }

}
