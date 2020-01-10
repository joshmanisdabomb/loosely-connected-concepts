package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCEntities;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.SplittableRandom;

public class NuclearExplosionEntity extends Entity implements LCCEntityHelper {

    private static final DataParameter<Integer> TICK = EntityDataManager.createKey(NuclearExplosionEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LIFETIME = EntityDataManager.createKey(NuclearExplosionEntity.class, DataSerializers.VARINT);

    private static final SplittableRandom FAST_RAND = new SplittableRandom();

    private short tick = 1;
    private short lifetime = 70;

    public NuclearExplosionEntity(EntityType<? extends NuclearExplosionEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public NuclearExplosionEntity(World world, double x, double y, double z, short lifetime) {
        this(LCCEntities.nuclear_explosion, world);
        this.setPosition(x, y, z);
        this.dataManager.set(LIFETIME, (int)(this.lifetime = lifetime));
    }

    @Override
    protected void registerData() {
        this.dataManager.register(TICK, 1);
        this.dataManager.register(LIFETIME, 70);
    }

    public int getTicks() {
        return this.tick;
    }

    @Override
    public void tick() {
        if (!this.world.isRemote() && this.tick > this.lifetime) {
            this.remove();
            super.tick();
            return;
        }
        int c = this.tick * 2;
        int c2 = c * c;
        int c3 = (c - 2) * (c - 2);
        int d = (this.tick - 1) * 2;
        int d2 = d * d;
        float progress = ((float)this.tick / this.lifetime);
        float p1 = progress*progress;
        float p2 = p1*progress;
        float fire = (p1 * 0.1F) - 0.05F;
        float damage = (((p2 * 0.5F) + (p1 * 0.5F)) * 1.1F) - 0.1F;
        BlockPos center = this.getPosition();
        BlockPos.MutableBlockPos mb = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mbu = new BlockPos.MutableBlockPos();
        ArrayList<BlockPos> nuclearWaste = new ArrayList<>();
        if (!this.world.isRemote) {
            for (int i = 0; i <= c; i++) {
                for (int k = 0; k <= c; k++) {
                    nuclearWaste.clear();
                    for (int j = 0; j <= c; j++) {
                        if ((center.getY() + j < 0 || center.getY() + j >= this.world.getHeight()) && (center.getY() - j < 0 || center.getY() - j >= this.world.getHeight())) continue;
                        mb.setPos(center.getX() + i, center.getY() + j, center.getZ() + k);
                        double distance = mb.distanceSq(center);
                        if (distance < c2 && distance >= c3 && distance > d2) {
                            for (int i2 = ((i != 0) ? -1 : 0); i2 <= ((i != 0) ? 1 : 0); i2 += 2) {
                                for (int k2 = ((k != 0) ? -1 : 0); k2 <= ((k != 0) ? 1 : 0); k2 += 2) {
                                    for (int j2 = ((j != 0) ? -1 : 0); j2 <= ((j != 0) ? 1 : 0); j2 += 2) {
                                        mb.setPos(center.getX() + (i * i2), center.getY() + (j * j2), center.getZ() + (k * k2));
                                        mbu.setPos(mb).move(0, 1, 0);
                                        if (mb.getY() < 0 || mb.getY() >= this.world.getHeight()) continue;
                                        if (!this.world.isAirBlock(mb) && this.world.getBlockState(mb).getBlockHardness(this.world, mb) != -1.0f && this.world.getBlockState(mbu) != LCCBlocks.nuclear_waste.getDefaultState()) {
                                            double r = FAST_RAND.nextDouble();
                                            if (this.world.getFluidState(mb) != Fluids.EMPTY.getDefaultState()) {
                                                world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                                            } else if (r < fire) {
                                                if (world.isAirBlock(mbu)) {
                                                    world.setBlockState(mbu, LCCBlocks.nuclear_fire.getDefaultState().with(FireBlock.AGE, FAST_RAND.nextInt(5)), 3);
                                                }
                                            } else if (r < damage) {
                                                if (FAST_RAND.nextDouble() < (1-p1)) {
                                                    nuclearWaste.add(mb.toImmutable());
                                                }
                                            } else {
                                                world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!nuclearWaste.isEmpty()) {
                        nuclearWaste.stream().sorted(Comparator.comparing(Vec3i::getY)).forEachOrdered(bp -> {
                            mb.setPos(bp);
                            world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                            while (FallingBlock.canFallThrough(world.getBlockState(mb.move(0, -1, 0))) && mb.getY() > 0);
                            mb.move(0, 1, 0);
                            world.setBlockState(mb, LCCBlocks.nuclear_waste.getDefaultState(), 3);
                        });
                    }
                }
            }
            this.dataManager.set(TICK, (int)this.tick++);
        }
        super.tick();
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(TICK, (int)(this.tick = compound.getShort("Age")));
        this.dataManager.set(LIFETIME, (int)(this.lifetime = compound.getShort("Lifetime")));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Age", this.tick);
        compound.putShort("Lifetime", this.lifetime);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return this.traitCreateSpawnPacket();
    }

}