package yam.entity.ai;

import java.util.Random;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIShelter extends EntityAIBase {

	private EntityCreature theCreature;
	private double shelterX;
    private double shelterY;
    private double shelterZ;
    private double movementSpeed;
    private int range;
    private World theWorld;
    private static final String __OBFID = "CL_00001871";

    public EntityAIShelter(EntityCreature par1EntityCreature, double par2, int par3)
    {
        this.theCreature = par1EntityCreature;
        this.movementSpeed = par2;
        this.range = par3;
        this.theWorld = par1EntityCreature.worldObj;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if ((!this.theWorld.isDaytime() || this.theWorld.isRaining()) && this.theWorld.canBlockSeeTheSky(MathHelper.floor_double(this.theCreature.posX), (int)this.theCreature.boundingBox.minY, MathHelper.floor_double(this.theCreature.posZ)))
        {
        	Vec3 vec3 = this.findPossibleShelter();

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.shelterX = vec3.xCoord;
                this.shelterY = vec3.yCoord;
                this.shelterZ = vec3.zCoord;
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	return !this.theCreature.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }

    private Vec3 findPossibleShelter()
    {
        Random random = this.theCreature.getRNG();

        for (int i = 0; i < range; ++i)
        {
            int j = MathHelper.floor_double(this.theCreature.posX + (double)random.nextInt(range) - range/2);
            int k = MathHelper.floor_double(this.theCreature.boundingBox.minY + (double)random.nextInt(range) - range/2);
            int l = MathHelper.floor_double(this.theCreature.posZ + (double)random.nextInt(range) - range/2);

            if (!this.theWorld.canBlockSeeTheSky(j, k, l) && this.theCreature.getBlockPathWeight(j, k, l) < 0.0F)
            {
                return Vec3.createVectorHelper((double)j, (double)k, (double)l);
            }
        }

        return null;
    }

}
