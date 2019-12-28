package com.joshmanisdabomb.lcc.entity;

import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraftforge.fml.network.NetworkHooks;

public interface LCCEntityHelper {

    default IPacket<?> traitCreateSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket((Entity)this);
    }

}
