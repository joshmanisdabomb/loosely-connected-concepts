package com.joshmanisdabomb.lcc.data.capability;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public interface LCCCapabilityHelper {

    ResourceLocation getLocation();

    default void packetRead(PacketBuffer buf) {

    }

    default void packetWrite(PacketBuffer buf) {

    }

    default void packetHandle() {

    }

}
