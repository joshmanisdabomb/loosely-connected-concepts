package com.joshmanisdabomb.lcc.capability;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class CryingObsidianCapability implements LCCCapabilityHelper {

    public static final ResourceLocation LOCATION = new ResourceLocation(LCC.MODID, "crying_obsidian");

    public DimensionType dimension = null;
    public BlockPos pos = null;

    @Override
    public ResourceLocation getLocation() {
        return LOCATION;
    }

    public boolean isEmpty() {
        return pos == null || dimension == null;
    }

    public void setEmpty() {
        this.pos = null;
        this.dimension = null;
    }

    @Override
    public void packetWrite(PacketBuffer buf) {
        if (this.pos != null && this.dimension != null) {
            buf.writeBlockPos(this.pos);
            buf.writeResourceLocation(this.dimension.getRegistryName());
        }
    }

    @Override
    public void packetRead(PacketBuffer buf) {
        if (buf.readableBytes() >= 8) {
            this.pos = buf.readBlockPos();
            this.dimension = DimensionType.byName(buf.readResourceLocation());
        } else {
            this.pos = null;
            this.dimension = null;
        }
    }

    @Override
    public void packetHandle() {
        PlayerEntity player = LCC.proxy.getClientPlayer();
        if (player != null) {
            player.getCapability(Provider.DEFAULT_CAPABILITY).ifPresent(co -> {
                co.pos = this.pos;
                co.dimension = this.dimension;
            });
        }
    }

    public static class Storage implements Capability.IStorage<CryingObsidianCapability> {

        @Override
        public INBT writeNBT(Capability<CryingObsidianCapability> capability, CryingObsidianCapability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            if (instance.dimension != null && instance.pos != null) {
                nbt.putString("dimension", instance.dimension.getRegistryName().toString());
                nbt.put("pos", instance.pos != null ? NBTUtil.writeBlockPos(instance.pos) : new CompoundNBT());
            }
            return nbt;
        }

        @Override
        public void readNBT(Capability<CryingObsidianCapability> capability, CryingObsidianCapability instance, Direction side, INBT nbt) {
            CompoundNBT nbtc = (CompoundNBT) nbt;
            if (!nbtc.isEmpty()) {
                instance.dimension = DimensionType.byName(new ResourceLocation(nbtc.getString("dimension")));
                instance.pos = NBTUtil.readBlockPos(nbtc.getCompound("pos"));
            }
        }

    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {

        @CapabilityInject(CryingObsidianCapability.class)
        public static final Capability<CryingObsidianCapability> DEFAULT_CAPABILITY = null;

        private CryingObsidianCapability instance = DEFAULT_CAPABILITY.getDefaultInstance();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return (CompoundNBT)DEFAULT_CAPABILITY.getStorage().writeNBT(DEFAULT_CAPABILITY, this.instance, null);
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            DEFAULT_CAPABILITY.getStorage().readNBT(DEFAULT_CAPABILITY, this.instance, null, nbt);
        }

    }

}
