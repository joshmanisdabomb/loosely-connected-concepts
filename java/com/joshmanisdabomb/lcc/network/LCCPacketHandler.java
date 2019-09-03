package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class LCCPacketHandler {

    public static final String PROTOCOL_VERSION = "3";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(LCC.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = -1;
        INSTANCE.registerMessage(++id, HeartsUpdatePacket.class, HeartsUpdatePacket::encode, HeartsUpdatePacket::decode, LCCPacketHandler::pass);
        INSTANCE.registerMessage(++id, LCCEntitySpawnPacket.class, LCCEntitySpawnPacket::encode, LCCEntitySpawnPacket::decode, LCCPacketHandler::pass);
        INSTANCE.registerMessage(++id, ParticleSpawnPacket.class, ParticleSpawnPacket::encode, ParticleSpawnPacket::decode, LCCPacketHandler::pass);
        INSTANCE.registerMessage(++id, SpreaderInterfaceUpdatePacket.class, SpreaderInterfaceUpdatePacket::encode, SpreaderInterfaceUpdatePacket::decode, LCCPacketHandler::pass);
        INSTANCE.registerMessage(++id, BouncePadExtensionPacket.class, BouncePadExtensionPacket::encode, BouncePadExtensionPacket::decode, LCCPacketHandler::pass);
        INSTANCE.registerMessage(++id, CryingObsidianUpdatePacket.class, CryingObsidianUpdatePacket::encode, CryingObsidianUpdatePacket::decode, LCCPacketHandler::pass);
        INSTANCE.registerMessage(++id, CryingObsidianSpawnPacket.class, CryingObsidianSpawnPacket::encode, CryingObsidianSpawnPacket::decode, LCCPacketHandler::pass);
    }

    public static void send(PacketDistributor.PacketTarget target, LCCPacket packet) {
        INSTANCE.send(target, packet);
    }

    public static <MSG extends LCCPacket> void pass(final MSG msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LCC.proxy.handlePacket(ctx.get().getDirection().getReceptionSide(), msg);
        });
        ctx.get().setPacketHandled(true);
    }

}
