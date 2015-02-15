package yam.entity.ai;

import java.util.Random;

import yam.entity.EntityHalfPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIGoHome extends EntityAIBase {

	private EntityHalfPlayer human;
    private double movementSpeed;
    
    private double homeX;
    private double homeY;
    private double homeZ;
    
    private double homePadding;
    private int range;
    
    private World theWorld;
    private static final String __OBFID = "CL_00001943";
	
    public EntityAIGoHome(EntityHalfPlayer par1Human, double par2, double homePadding, int par3)
    {
        this.human = par1Human;
        this.movementSpeed = par2;
        this.homePadding = homePadding;
        this.range = par3;
        this.theWorld = par1Human.worldObj;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute()
    {
        if (!this.theWorld.isDaytime() || this.theWorld.isRaining())
        {
        	Vec3 vec3 = this.findPossibleShelter();
        	if (human.homeX != -1) { 
	        	homeX = (human.homeX / 2) + (human.homeX2 / 2);
	        	homeY = human.homeY;
	        	homeZ = (human.homeZ / 2) + (human.homeZ2 / 2);
        	} else {
        		if (vec3 == null) {return false;}
                else
                {
                    this.homeX = vec3.xCoord;
                    this.homeY = vec3.yCoord;
                    this.homeZ = vec3.zCoord;
                    return true;
                }
        	}
        	return true;
        }
        return false;
    }
    
    public boolean continueExecuting()
    {
        return human.homeX == -1 ? !this.human.getNavigator().noPath() : !(human.posX >= human.homeX - homePadding/2 && human.posX <= human.homeX2 + homePadding/2 && human.posY >= human.homeY - homePadding/2 && human.posZ >= human.homeZ - homePadding/2 && human.posZ <= human.homeZ2 + homePadding/2);
    }
    
    public void startExecuting()
    {
        this.human.getNavigator().tryMoveToXYZ(homeX, homeY, homeZ, this.movementSpeed);
    }
    
    private Vec3 findPossibleShelter()
    {
        Random random = this.human.getRNG();

        for (int i = 0; i < range; ++i)
        {
            int j = MathHelper.floor_double(this.human.posX + (double)random.nextInt(range) - range/2);
            int k = MathHelper.floor_double(this.human.boundingBox.minY + (double)random.nextInt(range) - range/2);
            int l = MathHelper.floor_double(this.human.posZ + (double)random.nextInt(range) - range/2);

            if (!this.theWorld.canBlockSeeTheSky(j, k, l) && this.human.getBlockPathWeight(j, k, l) < 0.0F) {
                return Vec3.createVectorHelper((double)j, (double)k, (double)l);
            }
        }

        return null;
    }
}
