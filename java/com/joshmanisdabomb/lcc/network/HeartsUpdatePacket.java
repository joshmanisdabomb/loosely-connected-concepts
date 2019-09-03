package com.joshmanisdabomb.lcc.network;

import com.joshmanisdabomb.lcc.data.capability.HeartsCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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

    public HeartsUpdatePacket(HeartsCapability hearts) {
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

    @OnlyIn(Dist.CLIENT)
    public void handleClient() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(HeartsCapability.Provider.DEFAULT_CAPABILITY).ifPresent(hearts -> {
                hearts.setRedMaxHealth(redMax);
                hearts.setIronMaxHealth(ironMax);
                hearts.setIronHealth(iron);
                hearts.setCrystalMaxHealth(crystalMax);
                hearts.setCrystalHealth(crystal);
                hearts.setTemporaryHealth(temporary, Float.MAX_VALUE);
            });
        }
    }

}