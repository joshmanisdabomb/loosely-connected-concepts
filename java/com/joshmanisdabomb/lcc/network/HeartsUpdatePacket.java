package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HeartsUpdatePacket implements LCCPacket {

    private final float redMax;
    private final float ironMax;
    private final float iron;
    private final float crystalMax;
    private final float crystal;
    private final float temporary;

    public HeartsUpdatePacket(float redMax, float ironMax, float iron, float crystalMax, float crystal, float temporary) {
        this.redMax = redMax;
        this.ironMax = ironMax;
        this.iron = iron;
        this.crystalMax = crystalMax;
        this.crystal = crystal;
        this.temporary = temporary;
    }

    public HeartsUpdatePacket(HeartsCapability.CIHearts hearts) {
        this(hearts.getRedMaxHealth(), hearts.getIronMaxHealth(), hearts.getIronHealth(), hearts.getCrystalMaxHealth(), hearts.getCrystalHealth(), hearts.getTemporaryHealth());
    }

    public static void encode(HeartsUpdatePacket msg, PacketBuffer buf) {
        buf.writeFloat(msg.redMax);
        buf.writeFloat(msg.ironMax);
        buf.writeFloat(msg.iron);
        buf.writeFloat(msg.crystalMax);
        buf.writeFloat(msg.crystal);
        buf.writeFloat(msg.temporary);
    }

    public static HeartsUpdatePacket decode(PacketBuffer buf) {
        return new HeartsUpdatePacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static void handle(final HeartsUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            LCC.proxy.handleHeartsUpdatePacket(msg.redMax, msg.ironMax, msg.iron, msg.crystalMax, msg.crystal, msg.temporary);
        });
        ctx.get().setPacketHandled(true);
    }

}