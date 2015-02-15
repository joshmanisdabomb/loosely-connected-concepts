package yam.explosion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import yam.CustomDamage;
import yam.CustomPotion;
import yam.YetAnotherMod;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MLGExplosion extends Explosion {
	
    private int field_77289_h = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    /** A list of ChunkPositions of blocks affected by this explosion */
    public List affectedBlockPositions = new ArrayList();
    private Map field_77288_k = new HashMap();
	
	public MLGExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
		super(par1World, par2Entity, par3, par5, par7, par9);
        this.worldObj = par1World;
        this.exploder = par2Entity;
        this.explosionSize = par9;
        this.explosionX = par3;
        this.explosionY = par5;
        this.explosionZ = par7;
    }
	
	public void doExplosionA()
    {
        float f = this.explosionSize;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;

        for (i = 0; i < this.field_77289_h; ++i)
        {
            for (j = 0; j < this.field_77289_h; ++j)
            {
                for (k = 0; k < this.field_77289_h; ++k)
                {
                    if (i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1)
                    {
                        double d0 = (double)((float)i / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d1 = (double)((float)j / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d2 = (double)((float)k / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        d5 = this.explosionX;
                        d6 = this.explosionY;
                        d7 = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
                        {
                            int j1 = MathHelper.floor_double(d5);
                            int k1 = MathHelper.floor_double(d6);
                            int l1 = MathHelper.floor_double(d7);
                            Block block = this.worldObj.getBlock(j1, k1, l1);

                            if (block.getMaterial() != Material.air)
                            {
                                float f3 = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, j1, k1, l1, block) : block.getExplosionResistance(this.exploder, worldObj, j1, k1, l1, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3F) * f2;
                            }

                            if (f1 > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, j1, k1, l1, block, f1)))
                            {
                                hashset.add(new ChunkPosition(j1, k1, l1));
                            }

                            d5 += d0 * (double)f2;
                            d6 += d1 * (double)f2;
                            d7 += d2 * (double)f2;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        this.explosionSize *= 2.0F;
        i = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        j = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        k = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        int i2 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
        int l = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
        int j2 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox((double)i, (double)k, (double)l, (double)j, (double)i2, (double)j2));
        Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            float explosionAoE = f / 3.2F;
            double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / explosionAoE;
            double d8 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / f;
            //int effectDuration = (int)((f - (d8 * f) / 1.2)*100);
            //int effectStrength = (int)((1-d8)*4);             
            
            entity.attackEntityFrom(CustomDamage.causeRektDamage(entity, this.getExplosivePlacedBy()), 999999.0F);
        }

        this.explosionSize = f;
    }
	
	public void doExplosionB(boolean p_77279_1_)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, YetAnotherMod.MODID + ":explode.mlg", 128.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        Block block;

        iterator = this.affectedBlockPositions.iterator();

        while (iterator.hasNext())
        {
            chunkposition = (ChunkPosition)iterator.next();
            i = chunkposition.chunkPosX;
            j = chunkposition.chunkPosY;
            k = chunkposition.chunkPosZ;
            block = this.worldObj.getBlock(i, j, k);

            if (p_77279_1_)
            {
                double d0 = (double)((float)i + this.worldObj.rand.nextFloat());
                double d1 = (double)((float)j + this.worldObj.rand.nextFloat());
                double d2 = (double)((float)k + this.worldObj.rand.nextFloat());
                double d3 = d0 - this.explosionX;
                double d4 = d1 - this.explosionY;
                double d5 = d2 - this.explosionZ;
                double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                d3 /= d6;
                d4 /= d6;
                d5 /= d6;
                double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
                d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                d3 *= d7;
                d4 *= d7;
                d5 *= d7;
                if (this.explosionRNG.nextInt(100) == 0) {ParticleHandler.spawnParticle(this.worldObj, ParticleType.AMPSPLOSION, (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, 0, 0, 0);}
                this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
            }
        }
    }
	
}
