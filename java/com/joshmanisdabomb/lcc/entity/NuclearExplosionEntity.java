package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class NuclearExplosionEntity extends Entity implements LCCEntityHelper {

    private static final DataParameter<Integer> TICK = EntityDataManager.createKey(NuclearExplosionEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LIFETIME = EntityDataManager.createKey(NuclearExplosionEntity.class, DataSerializers.VARINT);

    public NuclearExplosionEntity(EntityType<? extends NuclearExplosionEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(TICK, 1);
        this.dataManager.register(LIFETIME, 70);
    }

    public int getTicks() {
        return this.dataManager.get(TICK);
    }

    @Override
    public void tick() {
        if (this.dataManager.get(TICK) == 0 && this.dataManager.get(LIFETIME) == 0) {
            this.dataManager.set(TICK, 1);
            this.dataManager.set(LIFETIME, 70);
        }
        if (!this.world.isRemote() && this.dataManager.get(TICK) > this.dataManager.get(LIFETIME)) {
            this.remove();
            super.tick();
            return;
        }
        int q = this.dataManager.get(TICK) * 2;
        float progress = ((float)this.dataManager.get(TICK) / this.dataManager.get(LIFETIME));
        BlockPos center = this.getPosition();
        BlockPos.MutableBlockPos mb = new BlockPos.MutableBlockPos();
        if (!this.world.isRemote) {
            for (int i = 0; i <= q; i++) {
                for (int j = 0; j <= q; j++) {
                    for (int k = 0; k <= q; k++) {
                        mb.setPos(center.getX() + i, center.getY() + j, center.getZ() + k);
                        double distance = MathHelper.sqrt(mb.distanceSq(center));
                        if (distance < q && distance >= q - 3) {
                            for (int i2 = ((i != 0) ? -1 : 0); i2 <= ((i != 0) ? 1 : 0); i2 += 2) {
                                for (int j2 = ((j != 0) ? -1 : 0); j2 <= ((j != 0) ? 1 : 0); j2 += 2) {
                                    for (int k2 = ((k != 0) ? -1 : 0); k2 <= ((k != 0) ? 1 : 0); k2 += 2) {
                                        mb.setPos(center.getX() + (i * i2), center.getY() + (j * j2), center.getZ() + (k * k2));
                                        if (!this.world.isAirBlock(mb) && this.world.getBlockState(mb).getBlockHardness(this.world, mb) != -1.0f) {
                                            float f = this.rand.nextFloat();
                                            if (this.world.getFluidState(mb) != Fluids.EMPTY.getDefaultState()) {
                                                world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                                            } else if (f < 0.01f * (progress * 10)) {
                                                world.setBlockState(mb, LCCBlocks.nuclear_fire.getDefaultState().with(FireBlock.AGE, rand.nextInt(15)), 3);
                                            } else if (f < 0.7f - (0.7F * progress)) {
                                                world.setBlockState(mb, LCCBlocks.nuclear_waste.getDefaultState(), 3);
                                            } else if (f < 0.7f * progress) {
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
            this.dataManager.set(TICK, this.dataManager.get(TICK) + 1);
        }
        super.tick();
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(TICK, compound.getInt("Age"));
        this.dataManager.set(LIFETIME, compound.getInt("Lifetime"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Age", this.dataManager.get(TICK));
        compound.putInt("Lifetime", this.dataManager.get(LIFETIME));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return this.traitCreateSpawnPacket();
    }

}