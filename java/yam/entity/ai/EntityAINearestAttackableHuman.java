package yam.entity.ai;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import yam.entity.EntityHalfPlayer;

public class EntityAINearestAttackableHuman extends EntityAINearestAttackableTarget {

	private EntityHalfPlayer targetEntity;
	private Class targetClass;
	private int targetChance;
	private Sorter theNearestAttackableTargetSorter;
	private IEntitySelector targetEntitySelector;
    protected EntityHalfPlayer taskOwner;

	public EntityAINearestAttackableHuman(EntityHalfPlayer par1EntityHuman, int par3, boolean par4) {
		super(par1EntityHuman, EntityHalfPlayer.class, par3, par4);
		this.taskOwner = par1EntityHuman;
		this.targetClass = EntityHalfPlayer.class;
        this.targetChance = par3;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(par1EntityHuman);
        this.setMutexBits(1);
	}
	
	public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        else {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty()) {
                return false;
            } else {
            	if (this.targetEntity instanceof EntityHalfPlayer) {
	            	if (this.targetEntity.lastname != this.taskOwner.lastname) {
	                    this.targetEntity = (EntityHalfPlayer)list.get(0);
	                    return true;
	        		}
            	}
            	return false;
            }
        }
    }
	
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

}
