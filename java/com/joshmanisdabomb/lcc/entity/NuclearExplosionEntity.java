package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.capability.NuclearCapability;
import com.joshmanisdabomb.lcc.network.CapabilitySyncPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCDamage;
import com.joshmanisdabomb.lcc.registry.LCCEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.SplittableRandom;

public class NuclearExplosionEntity extends Entity implements LCCEntityHelper {

    private static final DataParameter<Integer> TICK = EntityDataManager.createKey(NuclearExplosionEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LIFETIME = EntityDataManager.createKey(NuclearExplosionEntity.class, DataSerializers.VARINT);

    private static final SplittableRandom FAST_RAND = new SplittableRandom();
    private LivingEntity causedBy;

    private short tick = 1;
    private short lifetime = 70;

    @OnlyIn(Dist.CLIENT)
    public float partialTicks = 0;

    public NuclearExplosionEntity(EntityType<? extends NuclearExplosionEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public NuclearExplosionEntity(World world, double x, double y, double z, short lifetime, @Nullable LivingEntity entity) {
        this(LCCEntities.nuclear_explosion, world);
        this.setPosition(x, y, z);
        this.causedBy = entity;
        this.dataManager.set(LIFETIME, (int)(this.lifetime = lifetime));
    }

    @Override
    protected void registerData() {
        this.dataManager.register(TICK, 1);
        this.dataManager.register(LIFETIME, (int)this.lifetime);
    }

    public int getTicks() {
        return this.tick;
    }

    public int getLifetime() {
        return this.lifetime;
    }

    public float getProgress() {
        return (float)this.getTicks() / this.getLifetime();
    }

    public boolean canBeAttackedWithItem() {
        return false;
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
        float progress = this.getProgress();
        float p1 = progress*progress;
        float p2 = p1*progress;
        float fire = (p1 * 0.1F) - 0.05F;
        float damage = (((p2 * 0.5F) + (p1 * 0.5F)) * 1.1F) - 0.1F;
        int e = c+3;
        BlockPos center = this.getPosition();
        BlockPos.Mutable mb = new BlockPos.Mutable();
        BlockPos.Mutable mbu = new BlockPos.Mutable();
        ArrayList<BlockPos> nuclearWaste = new ArrayList<>();
        if (!this.world.isRemote) {
            if (this.tick == 1) {
                this.world.getCapability(NuclearCapability.Provider.DEFAULT_CAPABILITY).ifPresent(n -> {
                    n.nuke(this.world, center, this.lifetime);
                    LCCPacketHandler.send(PacketDistributor.DIMENSION.with(() -> this.world.dimension.getType()), new CapabilitySyncPacket(n));
                });
            }
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
                                        BlockState state = this.world.getBlockState(mb);
                                        BlockState ustate = this.world.getBlockState(mbu);
                                        if (!state.isAir(this.world, mb) && (state.getBlockHardness(this.world, mb) != -1.0f || state == LCCBlocks.nuclear_waste.getDefaultState()) && ustate != LCCBlocks.nuclear_waste.getDefaultState()) {
                                            double r = FAST_RAND.nextDouble();
                                            if (this.world.getFluidState(mb) != Fluids.EMPTY.getDefaultState()) {
                                                world.setBlockState(mb, Blocks.AIR.getDefaultState(), 3);
                                            } else if (r < fire) {
                                                if (ustate.isAir(this.world, mbu)) {
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
            for (Entity entity : world.getEntitiesInAABBexcluding(this, new AxisAlignedBB(this.getPositionVec(), this.getPositionVec()).grow(e), EntityPredicates.NOT_SPECTATING)) {
                double distance = entity.getDistance(entity);
                if (distance < e) {
                    entity.attackEntityFrom(LCCDamage.causeNukeDamage(this), (float)(e - distance));
                    entity.setFireTimer(Integer.MAX_VALUE - 100);
                    entity.hurtResistantTime = 0;
                }
            }
            this.dataManager.set(TICK, (int)this.tick++);
        }
        super.tick();
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (TICK.equals(key)) this.tick = this.dataManager.get(TICK).shortValue();
        if (LIFETIME.equals(key)) this.lifetime = this.dataManager.get(LIFETIME).shortValue();
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

    public LivingEntity getCausedBy() {
        return causedBy;
    }
}