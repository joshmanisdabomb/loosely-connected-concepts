package com.joshmanisdabomb.lcc.entity;

import com.joshmanisdabomb.lcc.network.LCCEntitySpawnPacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraftforge.fml.network.PacketDistributor;

public interface LCCEntityHelper {

    default IPacket<?> traitCreateSpawnPacket() {
        Entity e = ((Entity)this);
        LCCPacketHandler.send(PacketDistributor.TRACKING_CHUNK.with(() -> e.world.getChunk(e.chunkCoordX, e.chunkCoordZ)), new LCCEntitySpawnPacket(e));
        return new SSpawnObjectPacket(e);
    }

}
