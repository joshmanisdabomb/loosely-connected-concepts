package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.LCCEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityNuclearExplosion extends Entity {

    private static final int TEMP_LIFETIME = 70;

    public EntityNuclearExplosion(World worldIn) {
        super(LCCEntities.nuclear_explosion, worldIn);
    }

    @Override
    public void tick() {
        if (this.ticksExisted > TEMP_LIFETIME) {
            this.remove();
            super.tick();
            return;
        }
        int q = this.ticksExisted * 2;
        float progress = ((float)this.ticksExisted / TEMP_LIFETIME);
        BlockPos center = this.getPosition();
        BlockPos.MutableBlockPos mb = new BlockPos.MutableBlockPos();
        if (!this.world.isRemote) {
            for (int i = -q; i < q; i++) {
                for (int j = -q; j < q; j++) {
                    for (int k = -q; k < q; k++) {
                        mb.setPos(center.getX() + i, center.getY() + j, center.getZ() + k);
                        double distance = mb.getDistance(center);
                        if (distance < q && distance >= q-4 && !this.world.isAirBlock(mb) && this.world.getBlockState(mb).getBlockHardness(this.world, mb) != -1.0f) {
                            float f = this.rand.nextFloat();
                            if (f < 0.2f * progress) {
                                world.setBlockState(mb, Blocks.FIRE.getDefaultState(), 3);
                            } else if (f < 0.3f * progress) {
                                world.setBlockState(mb, Blocks.BEDROCK.getDefaultState(), 3);
                            } else if (f < 1f * progress && this.world.getFluidState(mb) == Fluids.EMPTY.getDefaultState()) {
                                //keep block
                            } else {
                                world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                            }
                        }
                    }
                }
            }
        }
        super.tick();
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(NBTTagCompound compound) {
        this.ticksExisted = compound.getInt("Age");
    }

    @Override
    protected void writeAdditional(NBTTagCompound compound) {
        compound.setInt("Age", this.ticksExisted);
    }

}