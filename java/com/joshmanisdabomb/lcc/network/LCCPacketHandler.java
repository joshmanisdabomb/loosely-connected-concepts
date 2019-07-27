package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class LCCPacketHandler {

    public static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(LCC.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = -1;
        INSTANCE.registerMessage(++id, HeartsUpdatePacket.class, HeartsUpdatePacket::encode, HeartsUpdatePacket::decode, HeartsUpdatePacket::handle);
        INSTANCE.registerMessage(++id, LCCEntitySpawnPacket.class, LCCEntitySpawnPacket::encode, LCCEntitySpawnPacket::decode, LCCEntitySpawnPacket::handle);
    }

    public static void send(PacketDistributor.PacketTarget target, LCCPacket packet) {
        INSTANCE.send(target, packet);
    }

}
