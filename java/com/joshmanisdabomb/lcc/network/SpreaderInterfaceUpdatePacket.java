package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.capability.SpreaderCapability;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import java.util.HashMap;
import java.util.UUID;

public class SpreaderInterfaceUpdatePacket implements LCCPacket {

    private final UUID player;
    private final SpreaderCapability newSettings;

    public SpreaderInterfaceUpdatePacket(UUID player, SpreaderCapability newSettings) {
        this.player = player;
        this.newSettings = newSettings;
    }

    public static void encode(SpreaderInterfaceUpdatePacket msg, PacketBuffer buf) {
        buf.writeUniqueId(msg.player);
        msg.newSettings.writeToPacket(buf);
    }

    public static SpreaderInterfaceUpdatePacket decode(PacketBuffer buf) {
        return new SpreaderInterfaceUpdatePacket(buf.readUniqueId(), new SpreaderCapability().readFromPacket(buf));
    }

    @Override
    public void handleLogicalServer() {
        MinecraftServer s = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);

        SpreaderCapability oldSettings = SpreaderCapability.Provider.getGlobal(s).orElse(new SpreaderCapability());
        HashMap<Object, Integer> costs = this.newSettings.calculateCosts(oldSettings, new HashMap<>());

        ServerPlayerEntity player = s.getPlayerList().getPlayerByUUID(this.player);
        if (!player.isCreative()) {
            SpreaderCapability.subtractCosts(player.inventory, costs);
        }

        oldSettings.clone(this.newSettings);
    }

}