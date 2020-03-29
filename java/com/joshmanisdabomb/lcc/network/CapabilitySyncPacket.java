package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.capability.LCCCapabilities;
import com.joshmanisdabomb.lcc.capability.LCCCapabilityHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CapabilitySyncPacket implements LCCPacket {

    private final LCCCapabilityHelper capability;

    public CapabilitySyncPacket(LCCCapabilityHelper capability) {
        this.capability = capability;
    }

    public static void encode(CapabilitySyncPacket msg, PacketBuffer buf) {
        buf.writeResourceLocation(msg.capability.getLocation());
        msg.capability.packetWrite(buf);
    }

    public static CapabilitySyncPacket decode(PacketBuffer buf) {
        LCCCapabilityHelper capability = LCCCapabilities.ch_all.get(buf.readResourceLocation()).get();
        capability.packetRead(buf);
        return new CapabilitySyncPacket(capability);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleClient() {
        capability.packetHandle();
    }

}