package com.joshmanisdabomb.aimagg.world;

import com.joshmanisdabomb.aimagg.AimaggDimension;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class AimaggTeleporter extends Teleporter {

	public AimaggTeleporter(WorldServer worldIn) {
		super(worldIn);
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw) {
		double x = 0.0D, y = 0.0D, z = 0.0D;
		
		System.out.println(this.world.provider.getDimensionType().getId());
        switch (AimaggDimension.getDimensionFromID(this.world.provider.getDimensionType().getId())) {
        	case RAINBOW:
        		y = 80.0D;
        		for (int i = -2; i <= 2; i++) {
            		for (int k = -2; k <= 2; k++) {
            			this.world.setBlockState(new BlockPos(x + i, y - 1, z + k), Blocks.OBSIDIAN.getDefaultState());
            		}
        		}
        		break;
        	default:
        		return;
        }
        
        entityIn.setLocationAndAngles(x + 0.5D, y, z + 0.5D, entityIn.rotationYaw, 0.0F);
        entityIn.motionX = 0.0D;
        entityIn.motionY = 0.0D;
        entityIn.motionZ = 0.0D;
    }
	
	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
		return false;
	}
	
	@Override
	public boolean makePortal(Entity entityIn) {
		return false;
	}
	
	@Override
	public void removeStalePortalLocations(long worldTime) {
		return;
	}

}
