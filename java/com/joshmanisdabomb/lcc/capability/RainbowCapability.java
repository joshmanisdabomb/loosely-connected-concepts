package com.joshmanisdabomb.lcc.capability;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class RainbowCapability implements LCCCapabilityHelper {

    public static final ResourceLocation LOCATION = new ResourceLocation(LCC.MODID, "rainbow");

    public int portalTimer = 0;
    public int portalDelay = 0;

    @Override
    public ResourceLocation getLocation() {
        return LOCATION;
    }

    @Override
    public void packetWrite(PacketBuffer buf) {
        buf.writeInt(this.portalTimer);
        buf.writeInt(this.portalDelay);
    }

    @Override
    public void packetRead(PacketBuffer buf) {
        this.portalTimer = buf.readInt();
        this.portalDelay = buf.readInt();
    }

    @Override
    public void packetHandle() {
        PlayerEntity player = LCC.proxy.getClientPlayer();
        if (player != null) {
            player.getCapability(RainbowCapability.Provider.DEFAULT_CAPABILITY).ifPresent(r -> {
                r.portalTimer = this.portalTimer;
                r.portalDelay = this.portalDelay;
            });
        }
    }

    public static class Storage implements Capability.IStorage<RainbowCapability> {

        @Override
        public INBT writeNBT(Capability<RainbowCapability> capability, RainbowCapability instance, Direction side) {
            return null;
        }

        @Override
        public void readNBT(Capability<RainbowCapability> capability, RainbowCapability instance, Direction side, INBT nbt) {

        }

    }

    public static class Provider implements ICapabilityProvider {

        @CapabilityInject(RainbowCapability.class)
        public static final Capability<RainbowCapability> DEFAULT_CAPABILITY = null;

        private RainbowCapability instance = DEFAULT_CAPABILITY.getDefaultInstance();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

    }

}

