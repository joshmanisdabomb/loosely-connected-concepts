package yam.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import yam.explosion.AmplifyExplosion;
import yam.particle.ParticleHandler;

public class EntityAmplifyBomb extends EntityThrowable
{
    private static final String __OBFID = "CL_00001722";

    public EntityAmplifyBomb(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityAmplifyBomb(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    public EntityAmplifyBomb(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition p_70184_1_)
    {
		Explosion explosion = new AmplifyExplosion(this.worldObj, this.getThrower(), this.posX, this.posY, this.posZ, 4.0F);
        explosion.isFlaming = false;
        explosion.isSmoking = false;
        explosion.doExplosionA();
        explosion.doExplosionB(true);

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}