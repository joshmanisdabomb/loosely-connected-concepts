package com.joshmanisdabomb.lcc.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MultipartHitbox<P extends Entity & MultipartEntity> extends Entity implements LCCEntityHelper {

    private static final DataParameter<Integer> PARENT_ID = EntityDataManager.createKey(MultipartHitbox.class, DataSerializers.VARINT);
    private static final DataParameter<Float> WIDTH = EntityDataManager.createKey(MultipartHitbox.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> HEIGHT = EntityDataManager.createKey(MultipartHitbox.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> INDEX = EntityDataManager.createKey(MultipartHitbox.class, DataSerializers.VARINT);

    private EntitySize newSize = EntitySize.flexible(0.98F, 0.98F);
    private int parentId;
    private int index;

    public MultipartHitbox(EntityType<MultipartHitbox> type, World world) {
        super(type, world);
    }

    public MultipartHitbox(World world, P parent, int index, float width, float height) {
        this(parent.getHitboxType(), world);
        this.setParent(parent);
        this.setSize(width, height);
        this.setIndex(index);
        this.setPosition(parent.getPosX(), parent.getPosY(), parent.getPosZ());
        this.setMotion(parent.getMotion());
    }

    @Override
    public void tick() {
        P parent = this.getParent();
        if (!world.isRemote) {
            if (this.parentId == 0 || parent == null) {
                this.remove();
                return;
            }
        }
        this.move(MoverType.SELF, this.getMotion());
        super.tick();
    }

    protected void registerData() {
        this.dataManager.register(PARENT_ID, 0);
        this.dataManager.register(WIDTH, 0.98F);
        this.dataManager.register(HEIGHT, 0.98F);
        this.dataManager.register(INDEX, 0);
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (PARENT_ID.equals(key)) this.parentId = this.dataManager.get(PARENT_ID);
        if (INDEX.equals(key)) this.index = this.dataManager.get(INDEX);
        if (WIDTH.equals(key) || HEIGHT.equals(key)) {
            this.newSize = EntitySize.flexible(this.dataManager.get(WIDTH), this.dataManager.get(HEIGHT));
            this.recalculateSize();
        }
    }

    protected void readAdditional(CompoundNBT nbt) {
    }

    protected void writeAdditional(CompoundNBT nbt) {
    }

    @Override
    public boolean processInitialInteract(PlayerEntity p_184230_1_, Hand p_184230_2_) {
        return this.getParent().processInitialInteract(p_184230_1_, p_184230_2_);
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    public boolean isEntityEqual(Entity e) {
        return this == e || this.getParent() == e;
    }

    public IPacket<?> createSpawnPacket() {
        return this.traitCreateSpawnPacket();
    }

    public EntitySize getSize(Pose p_213305_1_) {
        return this.newSize;
    }

    public void setParent(P parent) {
        this.dataManager.set(PARENT_ID, this.parentId = parent.getEntityId());
    }

    public P getParent() {
        try {
            return (P)world.getEntityByID(this.parentId);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public void setSize(float width, float height) {
        this.dataManager.set(WIDTH, width);
        this.dataManager.set(HEIGHT, height);
        this.newSize = EntitySize.flexible(width, height);
        this.recalculateSize();
    }

    public void setIndex(int index) {
        this.dataManager.set(INDEX, this.index = index);
    }

    public int getIndex() {
        return this.index;
    }

}
