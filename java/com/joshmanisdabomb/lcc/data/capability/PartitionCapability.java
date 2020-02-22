package com.joshmanisdabomb.lcc.data.capability;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.storage.PartitionFolder;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartitionCapability implements LCCCapabilityHelper {

    public static final ResourceLocation LOCATION = new ResourceLocation(LCC.MODID, "partition_storage");

    private final HashMap<UUID, PartitionFolder> partitions = new HashMap<>();

    @Override
    public ResourceLocation getLocation() {
        return LOCATION;
    }

    public PartitionFolder get(StorageInfo.Partition p) {
        return partitions.computeIfAbsent(p.id, k -> new PartitionFolder());
    }

    public static class Storage implements Capability.IStorage<PartitionCapability> {

        @Override
        public INBT writeNBT(Capability<PartitionCapability> capability, PartitionCapability instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            CompoundNBT partitions = new CompoundNBT();
            for (Map.Entry<UUID, PartitionFolder> e : instance.partitions.entrySet()) {
                CompoundNBT pf = new CompoundNBT();
                e.getValue().write(pf);
                partitions.put(e.getKey().toString(), pf);
            }
            nbt.put("partitions", partitions);
            return nbt;
        }

        @Override
        public void readNBT(Capability<PartitionCapability> capability, PartitionCapability instance, Direction side, INBT nbt) {
            CompoundNBT nbtc = (CompoundNBT)nbt;
            CompoundNBT partitions = nbtc.getCompound("partitions");
            for (String key : partitions.keySet()) {
                CompoundNBT n = partitions.getCompound(key);
                PartitionFolder pf = PartitionFolder.read(n);
                instance.partitions.put(UUID.fromString(key), pf);
            }
        }

    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {

        @CapabilityInject(PartitionCapability.class)
        public static final Capability<PartitionCapability> DEFAULT_CAPABILITY = null;

        private PartitionCapability instance = DEFAULT_CAPABILITY.getDefaultInstance();

        private static final GlobalProvider<PartitionCapability> global = new GlobalProvider<>(() -> DEFAULT_CAPABILITY);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
            return capability == DEFAULT_CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
        }

        public static LazyOptional<PartitionCapability> getGlobal(MinecraftServer server) {
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
