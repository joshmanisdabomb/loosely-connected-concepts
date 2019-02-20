package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.LCCBlocks;
import com.joshmanisdabomb.lcc.LCCEntities;
import net.minecraft.block.BlockFire;
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
            for (int i = 0; i <= q; i++) {
                for (int j = 0; j <= q; j++) {
                    for (int k = 0; k <= q; k++) {
                        mb.setPos(center.getX() + i, center.getY() + j, center.getZ() + k);
                        double distance = mb.getDistance(center);
                        if (distance < q && distance >= q - 3) {
                            for (int i2 = ((i != 0) ? -1 : 0); i2 <= ((i != 0) ? 1 : 0); i2 += 2) {
                                for (int j2 = ((j != 0) ? -1 : 0); j2 <= ((j != 0) ? 1 : 0); j2 += 2) {
                                    for (int k2 = ((k != 0) ? -1 : 0); k2 <= ((k != 0) ? 1 : 0); k2 += 2) {
                                        mb.setPos(center.getX() + (i * i2), center.getY() + (j * j2), center.getZ() + (k * k2));
                                        if (!this.world.isAirBlock(mb) && this.world.getBlockState(mb).getBlockHardness(this.world, mb) != -1.0f) {
                                            float f = this.rand.nextFloat();
                                            if (f < 0.035f * progress) {
                                                world.setBlockState(mb, LCCBlocks.nuclear_fire.getDefaultState().with(BlockFire.AGE, rand.nextInt(15)), 3);
                                            } else if (f < 0.3f * progress) {
                                                world.setBlockState(mb, LCCBlocks.nuclear_waste.getDefaultState(), 3);
                                            } else if (f < 0.7f * progress && this.world.getFluidState(mb) == Fluids.EMPTY.getDefaultState()) {
                                                //keep block
                                            } else {
                                                world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                                            }
                                        }
                                    }
                                }
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