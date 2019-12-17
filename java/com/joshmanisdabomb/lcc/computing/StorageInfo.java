package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

public class StorageInfo {

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

    public int getPartitionedSpace() {
        int total = 0;
        for (Partition p : this.getPartitions()) {
            total += p.size;
        }
        return total;
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

    public StorageInfo setPartitions(ArrayList<Partition> partitions) {
        ListNBT parts = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
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

    public static HashMap<ItemStack, String> getShortIds(List<ItemStack> items) {
        return StorageInfo.getShortIds(items.stream().collect(Collectors.toMap(i -> i, i -> new StorageInfo(i).getUniqueId())));
    }

    public static <T> HashMap<T, String> getShortIds(Map<T, UUID> map) {
        Map<T, String> stringMap = map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
        HashMap<T, String> ret = new HashMap<>();
        for (Map.Entry<T, String> e : stringMap.entrySet()) {
            for (int i = 1; i <= 32; i++) {
                String search = e.getValue().replace("-", "").substring(0, i);
                if (stringMap.values().stream().filter(id -> id.replace("-", "").startsWith(search)).count() == 1) {
                    ret.put(e.getKey(), search);
                    break;
                }
            }
        }
        return ret;
    }

    public static class Partition {
        public UUID id;
        public String name;
        public PartitionType type;
        public int size;
        public int start;

        public Partition(UUID id, String name, PartitionType type, int size) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.size = size;
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
