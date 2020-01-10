package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.data.capability.LCCCapabilities;
import com.joshmanisdabomb.lcc.data.capability.LCCCapabilityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

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