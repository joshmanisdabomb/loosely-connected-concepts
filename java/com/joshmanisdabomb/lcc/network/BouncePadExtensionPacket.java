package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.tileentity.BouncePadTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BouncePadExtensionPacket implements LCCPacket {

    private final BlockPos pos;
    private final float extension;

    public BouncePadExtensionPacket(BlockPos pos, float extension) {
        this.pos = pos;
        this.extension = extension;
    }

    public static void encode(BouncePadExtensionPacket msg, PacketBuffer buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeFloat(msg.extension);
    }

    public static BouncePadExtensionPacket decode(PacketBuffer buf) {
        return new BouncePadExtensionPacket(buf.readBlockPos(), buf.readFloat());
    }

    public static void handle(final BouncePadExtensionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ((BouncePadTileEntity)Minecraft.getInstance().world.getTileEntity(msg.pos)).extension = msg.extension;
        });
        ctx.get().setPacketHandled(true);
    }

}