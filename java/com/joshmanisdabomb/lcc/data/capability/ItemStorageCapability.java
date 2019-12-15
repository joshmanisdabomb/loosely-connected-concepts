package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class ItemStorageCapability {

    public static final ResourceLocation LOCATION = new ResourceLocation(LCC.MODID, "item_storage");

    public static class Storage implements Capability.IStorage<ItemStorageCapability> {

        @Override
        public INBT writeNBT(Capability<ItemStorageCapability> capability, ItemStorageCapability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            return nbt;
        }

        @Override
        public void readNBT(Capability<ItemStorageCapability> capability, ItemStorageCapability instance, Direction side, INBT nbt) {
            CompoundNBT nbtc = (CompoundNBT)nbt;
        }

    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {

        @CapabilityInject(ItemStorageCapability.class)
        public static final Capability<ItemStorageCapability> DEFAULT_CAPABILITY = null;

        private ItemStorageCapability instance = DEFAULT_CAPABILITY.getDefaultInstance();

        private static final GlobalProvider<ItemStorageCapability> global = new GlobalProvider<>(() -> DEFAULT_CAPABILITY);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

        public static LazyOptional<ItemStorageCapability> getGlobal(MinecraftServer server) {
            return global.get(server);
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
