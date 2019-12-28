package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.registry.LCCEntities;
import com.joshmanisdabomb.lcc.tileentity.AtomicBombTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AtomicBombEntity extends Entity implements LCCEntityHelper {

    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(AtomicBombEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Direction> FACING = EntityDataManager.createKey(AtomicBombEntity.class, DataSerializers.DIRECTION);
    public CompoundNBT tileEntityData;

    protected LivingEntity tntPlacedBy;
    private int fuse = -1;
    public Direction facing = Direction.NORTH;
    private AxisAlignedBB bb = super.getBoundingBox();

    public AtomicBombEntity(EntityType<AtomicBombEntity> type, World world) {
        super(type, world);
    }

    public AtomicBombEntity(World world, double x, double y, double z, Direction facing, AtomicBombTileEntity te) {
        this(LCCEntities.atomic_bomb, world);
        this.setDirection(facing);
        this.setPosition(x, y, z);
        this.setMotion(Vec3d.ZERO);
        this.tileEntityData = te.write(new CompoundNBT());
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    public AtomicBombEntity(World world, double x, double y, double z, Direction facing, AtomicBombTileEntity te, @Nullable LivingEntity entity) {
        this(world, x, y, z, facing, te);
        double lvt_9_1_ = world.rand.nextDouble() * 6.2831854820251465D;
        this.setMotion(-Math.sin(lvt_9_1_) * 0.02D, 0.20000000298023224D, -Math.cos(lvt_9_1_) * 0.02D);
        this.setFuse(1200);
        this.tntPlacedBy = entity;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return this.traitCreateSpawnPacket();
    }

    protected void registerData() {
        this.dataManager.register(FUSE, -1);
        this.dataManager.register(FACING, Direction.NORTH);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        if (this.fuse > 0) {
            --this.fuse;
            if (this.fuse == 0) {
                this.remove();
                if (!this.world.isRemote) {
                    this.explode();
                }
            } else {
                this.handleWaterMovement();
            }
        }
    }

    protected void explode() {
        float f = 4.0F;
        this.world.createExplosion(this, this.posX, this.posY + (double)(this.getHeight() / 16.0F), this.posZ, 4.0F, Explosion.Mode.BREAK);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
        this.setDirection(Direction.byHorizontalIndex(compound.getByte("Facing")));
        if (compound.contains("TileEntityData", 10)) {
            this.tileEntityData = compound.getCompound("TileEntityData");
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
        compound.putByte("Facing", (byte)this.facing.getHorizontalIndex());
        if (this.tileEntityData != null) {
            compound.put("TileEntityData", this.tileEntityData);
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) this.fuse = this.dataManager.get(FUSE);
        if (FACING.equals(key)) {
            this.facing = this.dataManager.get(FACING);
            this.recalculateSize();
        }
    }

    public int getFuse() {
        return this.fuse;
    }

    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void setDirection(Direction facing) {
        this.dataManager.set(FACING, facing);
        this.facing = facing;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.bb;
    }

    @Override
    public void setBoundingBox(AxisAlignedBB b) {
        this.bb = b;
        super.setBoundingBox(b);
    }

    @Override
    public void recalculateSize() {
        super.recalculateSize();
        this.setBoundingBox(expandBoundingBox(this.getBoundingBox()));
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
        this.setBoundingBox(expandBoundingBox(this.getBoundingBox()));
    }

    private AxisAlignedBB expandBoundingBox(AxisAlignedBB b) {
        if (facing != null) return b.grow(Math.abs(facing.getXOffset()), 0, Math.abs(facing.getZOffset()));
        else return b;
    }

}
