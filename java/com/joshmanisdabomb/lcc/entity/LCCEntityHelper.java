package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.network.LCCEntitySpawnPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.network.PacketDistributor;

public interface LCCEntityHelper {

    default void traitCreateSpawnPacket() {
        Entity e = ((Entity)this);
        LCCPacketHandler.send(PacketDistributor.TRACKING_CHUNK.with(() -> e.world.getChunk(e.chunkCoordX, e.chunkCoordZ)), new LCCEntitySpawnPacket(e));
    }

}
