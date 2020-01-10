package com.joshmanisdabomb.lcc.data.capability;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface LCCCapabilityHelper {

    ResourceLocation getLocation();

    default void packetRead(PacketBuffer buf) {

    }

    default void packetWrite(PacketBuffer buf) {

    }

    default void packetHandle() {

    }

}
