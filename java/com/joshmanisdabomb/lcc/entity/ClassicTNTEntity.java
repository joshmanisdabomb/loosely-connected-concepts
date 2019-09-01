package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.registry.LCCEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ClassicTNTEntity extends TNTEntity implements LCCEntityHelper {

    protected LivingEntity tntPlacedBy;

    public ClassicTNTEntity(EntityType<? extends TNTEntity> type, World world) {
        super(type, world);
    }

    public ClassicTNTEntity(World world, double x, double y, double z, @Nullable LivingEntity entity) {
        this(LCCEntities.classic_tnt, world);
        this.setPosition(x, y, z);
        double lvt_9_1_ = world.rand.nextDouble() * 6.2831854820251465D;
        this.setMotion(-Math.sin(lvt_9_1_) * 0.02D, 0.20000000298023224D, -Math.cos(lvt_9_1_) * 0.02D);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.tntPlacedBy = entity;
    }

    @Override
    @Nullable
    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return this.traitCreateSpawnPacket();
    }

}
