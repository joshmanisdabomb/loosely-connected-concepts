package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.block.AtomicBombBlock;
import com.joshmanisdabomb.lcc.data.capability.NuclearCapability;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCEntities;
import com.joshmanisdabomb.lcc.tileentity.AtomicBombTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class AtomicBombEntity extends Entity implements LCCEntityHelper, IEntityAdditionalSpawnData {

    private static final DataParameter<Boolean> ACTIVE = EntityDataManager.createKey(AtomicBombEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(AtomicBombEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Direction> FACING = EntityDataManager.createKey(AtomicBombEntity.class, DataSerializers.DIRECTION);
    public CompoundNBT tileEntityData;

    protected LivingEntity tntPlacedBy;

    private boolean active = false;
    private int fuse = 1200;
    public Direction facing = Direction.NORTH;

    private AxisAlignedBB bb = super.getBoundingBox();
    private int fallTime = 0;

    public AtomicBombEntity(EntityType<AtomicBombEntity> type, World world) {
        super(type, world);
    }

    public AtomicBombEntity(World world, double x, double y, double z, Direction facing, AtomicBombTileEntity te) {
        this(LCCEntities.atomic_bomb, world);
        this.setDirection(facing);
        this.bb = super.getBoundingBox().offset(x, y, z);
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
        this.setActive(true);
        this.setFuse(NuclearCapability.getFuse(this.getUranium()));
        this.tntPlacedBy = entity;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return this.traitCreateSpawnPacket();
    }

    protected void registerData() {
        this.dataManager.register(FUSE, 1200);
        this.dataManager.register(FACING, Direction.NORTH);
        this.dataManager.register(ACTIVE, false);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    public void tick() {
        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();

        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.08D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.12D, -0.5D, 0.12D));
        }

        if (this.active) {
            --this.fuse;
            if (this.fuse <= 0) {
                this.remove();
                if (!this.world.isRemote) {
                    this.explode();
                }
            } else {
                this.handleWaterMovement();
            }
        } else {
            if (!this.world.isRemote) {
                BlockPos bp = new BlockPos(this);
                if (this.onGround) {
                    boolean movingPiston = false;
                    boolean replaceable = true;
                    boolean setBlockState;
                    for (int i = -1; i <= 1; i++) {
                        BlockPos bp2 = bp.offset(this.facing, i);
                        BlockState state = this.world.getBlockState(bp);
                        movingPiston = movingPiston || state.getBlock() == Blocks.MOVING_PISTON;
                        replaceable = replaceable && state.isReplaceable(new DirectionalPlaceContext(this.world, bp, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                    }
                    if (!movingPiston) {
                        if (replaceable) {
                            BlockState newState = LCCBlocks.atomic_bomb.getDefaultState().with(AtomicBombBlock.FACING, this.facing).with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.MIDDLE);
                            setBlockState = this.world.setBlockState(bp, newState, 3);
                            setBlockState = setBlockState && this.world.setBlockState(bp.offset(this.facing), newState.with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.FRONT), 3);
                            setBlockState = setBlockState && this.world.setBlockState(bp.offset(this.facing.getOpposite()), newState.with(AtomicBombBlock.SEGMENT, AtomicBombBlock.Segment.BACK), 3);
                            if (setBlockState) {
                                if (this.tileEntityData != null) {
                                    TileEntity te = this.world.getTileEntity(bp);
                                    if (te != null) {
                                        CompoundNBT data = te.write(new CompoundNBT()).merge(this.tileEntityData);
                                        data.putInt("x", bp.getX());
                                        data.putInt("y", bp.getY());
                                        data.putInt("z", bp.getZ());
                                        te.read(data);
                                        te.markDirty();
                                    }
                                }
                                this.remove();
                            } else {
                                this.drops();
                                this.remove();
                            }
                        } else {
                            this.drops();
                            this.remove();
                        }
                    }
                } else {
                    if ((this.fallTime > 100 && (bp.getY() < 1 || bp.getY() > 256)) || this.fallTime > 600) {
                        this.drops();
                        this.remove();
                    }
                }
                this.fallTime++;
            }
        }
    }

    private void drops() {
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.entityDropItem(LCCBlocks.atomic_bomb);
            if (this.tileEntityData != null && this.tileEntityData.contains("inventory", Constants.NBT.TAG_COMPOUND)) {
                ItemStackHandler h = new ItemStackHandler();
                h.deserializeNBT(this.tileEntityData.getCompound("inventory"));
                for (int i = 0; i < h.getSlots(); i++) {
                    this.entityDropItem(h.extractItem(i, 64, false));
                }
            }
        }
    }

    protected void explode() {
        world.addEntity(new NuclearExplosionEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), (short)(NuclearCapability.getExplosionLifetime(this.getUranium(), false))));
    }

    private int getUranium() {
        int u = 0;
        if (this.tileEntityData != null && this.tileEntityData.contains("inventory", Constants.NBT.TAG_COMPOUND)) {
            ItemStackHandler h = new ItemStackHandler();
            h.deserializeNBT(this.tileEntityData.getCompound("inventory"));
            for (int i = 0; i < h.getSlots(); i++) {
                ItemStack stack = h.getStackInSlot(i);
                if (stack.getItem() == LCCBlocks.enriched_uranium_storage.asItem()) u += (stack.getCount() * 9);
            }
        }
        return u;
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if (this.active) {
            ItemStack itemstack = player.getHeldItem(hand);
            if (itemstack.getItem() instanceof ShearsItem) {
                this.world.playSound(player, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_SHEEP_SHEAR, this.getSoundCategory(), 1.0F, 1.0F);
                player.swingArm(hand);
                if (!this.world.isRemote) {
                    this.drops();
                    this.remove();
                    itemstack.damageItem(1, player, (p_213625_1_) -> {
                        p_213625_1_.sendBreakAnimation(hand);
                    });
                    return true;
                }
            }
        }
        return super.processInitialInteract(player, hand);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
        this.setActive(compound.getBoolean("Active"));
        this.setDirection(Direction.byHorizontalIndex(compound.getByte("Facing")));
        this.fallTime = compound.getInt("Fall");
        if (compound.contains("TileEntityData", 10)) {
            this.tileEntityData = compound.getCompound("TileEntityData");
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
        compound.putBoolean("Active", this.isActive());
        compound.putByte("Facing", (byte)this.facing.getHorizontalIndex());
        compound.putInt("Fall", this.fallTime);
        if (this.tileEntityData != null) {
            compound.put("TileEntityData", this.tileEntityData);
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) this.fuse = this.dataManager.get(FUSE);
        if (ACTIVE.equals(key)) this.active = this.dataManager.get(ACTIVE);
        if (FACING.equals(key)) {
            this.facing = this.dataManager.get(FACING);
            this.recalculateSize();
        }
    }

    public int getFuse() {
        return this.fuse;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, this.fuse = fuseIn);
    }

    public void setActive(boolean active) {
        this.dataManager.set(ACTIVE, this.active = active);
    }

    public void setDirection(Direction facing) {
        this.dataManager.set(FACING, this.facing = facing);
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
        this.setBoundingBox(this.expandBoundingBox(this.getBoundingBox()));
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
        this.setBoundingBox(this.expandBoundingBox(this.getBoundingBox()));
    }

    private AxisAlignedBB expandBoundingBox(AxisAlignedBB b) {
        if (facing != null) return b.grow(Math.abs(facing.getXOffset()), 0, Math.abs(facing.getZOffset()));
        return b;
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeEnumValue(this.facing);
    }

    @Override
    public void readSpawnData(PacketBuffer buffer) {
        this.bb = super.getBoundingBox().offset(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setDirection(buffer.readEnumValue(Direction.class));
        this.recalculateSize();
    }

}
