package com.joshmanisdabomb.lcc.data.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class CryingObsidianCapability {

    public DimensionType dimension = null;
    public BlockPos pos = null;

    public boolean isEmpty() {
        return pos == null || dimension == null;
    }

    public void setEmpty() {
        this.pos = null;
        this.dimension = null;
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
