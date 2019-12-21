package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import com.joshmanisdabomb.lcc.item.StorageItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class StorageInfo implements ShortenableUniqueIdentifier {

    public static final String TAG = "lcc:computing";

    public final ItemStack stack;
    private final CompoundNBT tag;

    public StorageInfo(ItemStack stack) {
        this.stack = stack;
        this.tag = stack.getOrCreateChildTag(TAG);
    }

    public boolean hasUniqueId() {
        if (!tag.hasUniqueId("id")) return false;
        UUID id = this.getUniqueId();
        return id.getLeastSignificantBits() != 0 && id.getMostSignificantBits() != 0;
    }

    public UUID getUniqueId() {
        return tag.getUniqueId("id");
    }

    public StorageInfo setUniqueId(UUID id) {
        tag.putUniqueId("id", id);
        return this;
    }

    public boolean hasName() {
        return tag.contains("name", Constants.NBT.TAG_STRING);
    }

    public String getName() {
        return tag.getString("name");
    }

    public StorageInfo setName(String name) {
        tag.putString("name", name);
        return this;
    }

    public boolean hasColor() {
        return tag.contains("color", Constants.NBT.TAG_INT);
    }

    public int getColor() {
        return tag.getInt("color");
    }

    public StorageInfo setColor(int color) {
        tag.putInt("color", color);
        return this;
    }

    public boolean hasSize() {
        return tag.contains("size", Constants.NBT.TAG_INT);
    }

    public int getSize() {
        return tag.getInt("size");
    }

    public int getUsedSpace() {
        int total = 0;
        for (Partition p : this.getPartitions()) total += p.getUsedSpace();
        return total;
    }

    public int getFreeSpace() {
        return this.getSize() - this.getUsedSpace();
    }

    public int getPartitionedSpace() {
        int total = 0;
        for (Partition p : this.getPartitions()) total += p.size;
        return total;
    }

    public int getPartitionableSpace() {
        return this.getSize() - this.getPartitionedSpace();
    }

    public StorageInfo setSize(int size) {
        tag.putInt("size", size);
        return this;
    }

    public boolean hasPartitions() {
        return tag.contains("partitions", Constants.NBT.TAG_LIST);
    }

    public ArrayList<Partition> getPartitions() {
        ArrayList<Partition> list = new ArrayList<>();
        ListNBT parts = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
        for (INBT t : parts) {
            CompoundNBT partition = (CompoundNBT)t;
            Partition p = new Partition(partition.getUniqueId("id"), partition.getString("name"), Partition.PartitionType.byName(partition.getString("type")), partition.getInt("size"));
            if (p.type.isOS()) p.start = partition.getInt("start");
            list.add(p);
        }
        return list;
    }

    public StorageInfo setPartitions(List<Partition> partitions) {
        ListNBT parts = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
        parts.clear();
        for (Partition part : partitions) {
            CompoundNBT partition = new CompoundNBT();
            partition.putUniqueId("id", part.id);
            partition.putString("name", part.name);
            partition.putString("type", part.type.getName());
            partition.putInt("size", part.size);
            if (part.type.isOS()) partition.putInt("start", part.start);
            parts.add(partition);
        }
        tag.put("partitions", parts);
        return this;
    }

    public StorageInfo clearPartitions() {
        ListNBT parts = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
        parts.clear();
        tag.put("partitions", parts);
        return this;
    }

    public StorageInfo addPartition(Partition part) {
        ListNBT parts = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);

        CompoundNBT partition = new CompoundNBT();
        partition.putUniqueId("id", part.id);
        partition.putString("name", part.name);
        partition.putString("type", part.type.getName());
        partition.putInt("size", part.size);
        if (part.type.isOS()) partition.putInt("start", part.start);
        parts.add(partition);
        tag.put("partitions", parts);

        return this;
    }

    public static Map<ItemStack, String> getShortIds(List<ItemStack> items, boolean includePartitions) {
        List<StorageInfo> si = items.stream().map(StorageInfo::new).collect(Collectors.toList());
        List<Partition> partitions = si.stream().flatMap(i -> i.getPartitions().stream()).collect(Collectors.toList());
        return ShortenableUniqueIdentifier.getShortIds(si, includePartitions ? partitions : null).entrySet().stream().collect(Collectors.toMap(s -> s.getKey().stack, Map.Entry::getValue));
    }

    public static Map<Partition, String> getShortPartitionIds(List<ItemStack> items, boolean includeDisks) {
        List<StorageInfo> si = items.stream().map(StorageInfo::new).collect(Collectors.toList());
        List<Partition> partitions = si.stream().flatMap(i -> i.getPartitions().stream()).collect(Collectors.toList());
        return ShortenableUniqueIdentifier.getShortIds(partitions, includeDisks ? si : null);
    }

    public static String getShortId(List<ItemStack> items, ItemStack item, boolean includePartitions) {
        return StorageInfo.getShortIds(items, includePartitions).get(item);
    }

    public static String getShortPartitionId(List<ItemStack> items, Partition part, boolean includeDisks) {
        return StorageInfo.getShortPartitionIds(items, includeDisks).get(part);
    }

    public static class Partition implements ShortenableUniqueIdentifier {
        public UUID id;
        public String name;
        public PartitionType type;
        public int size;
        public int start;

        public Partition(UUID id, String name, PartitionType type, int size) {
            this.id = id != null ? id : new UUID(0, 0);
            this.name = name;
            this.type = type;
            this.size = size;
        }

        public int getUsedSpace() {
            return this.type.isOS() ? this.size : 0;
        }

        public int getFreeSpace() {
            return this.size - this.getUsedSpace();
        }

        public boolean hasUniqueId() {
            if (id == null) return false;
            return id.getLeastSignificantBits() != 0 && id.getMostSignificantBits() != 0;
        }

        @Override
        public UUID getUniqueId() {
            return this.id;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Partition && this.hasUniqueId() && ((Partition)obj).hasUniqueId() && this.id.equals(((Partition)obj).id);
        }

        @Override
        public int hashCode() {
            return this.hasUniqueId() ? Objects.hash(this.id.toString()) : super.hashCode();
        }

        public enum PartitionType implements IStringSerializable {
            ITEM(TextFormatting.AQUA),
            OS_CONSOLE(TextFormatting.GOLD, OperatingSystem.Type.CONSOLE),
            OS_GRAPHICAL(TextFormatting.GREEN, OperatingSystem.Type.GRAPHICAL);

            public final TextFormatting color;
            public final OperatingSystem.Type os;

            PartitionType(TextFormatting color) {
                this.os = null;
                this.color = color;
            }

            PartitionType(TextFormatting color, OperatingSystem.Type os) {
                this.os = os;
                this.color = color;
            }

            @Override
            public String getName() {
                return this.name().toLowerCase();
            }

            public boolean isOS() {
                return this.os != null;
            }

            public static PartitionType byName(String name) {
                return Arrays.stream(PartitionType.values()).filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
            }
        }
    }

}
