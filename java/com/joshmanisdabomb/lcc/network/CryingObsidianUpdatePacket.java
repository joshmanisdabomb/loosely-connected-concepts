package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.data.capability.CryingObsidianCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CryingObsidianUpdatePacket implements LCCPacket {

    private final BlockPos pos;
    private final DimensionType dimension;

    public CryingObsidianUpdatePacket(BlockPos pos, DimensionType dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public CryingObsidianUpdatePacket(CryingObsidianCapability co) {
        this(co.pos, co.dimension);
    }

    public static void encode(CryingObsidianUpdatePacket msg, PacketBuffer buf) {
        if (msg.pos != null && msg.dimension != null) {
            buf.writeBlockPos(msg.pos);
            buf.writeResourceLocation(msg.dimension.getRegistryName());
        }
    }

    public static CryingObsidianUpdatePacket decode(PacketBuffer buf) {
        if (buf.readableBytes() >= 8) {
            return new CryingObsidianUpdatePacket(buf.readBlockPos(), DimensionType.byName(buf.readResourceLocation()));
        } else {
            return new CryingObsidianUpdatePacket(null, null);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleClient() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(CryingObsidianCapability.Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                co.pos = this.pos;
                co.dimension = this.dimension;
            });
        }
    }

}
