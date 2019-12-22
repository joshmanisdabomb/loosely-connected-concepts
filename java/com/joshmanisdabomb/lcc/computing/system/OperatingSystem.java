package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.TerminalSession;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.TriPredicate;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class OperatingSystem {

    protected final ComputingSession cs;

    private static final TriPredicate<StorageInfo, String, Boolean> FILTER_DISK_BY_ID = (inf, filter, includePartitions) -> (inf.hasUniqueId() && inf.getUniqueId().toString().toLowerCase().replace("-", "").startsWith(filter)) || (includePartitions && inf.getPartitions().stream().anyMatch(p -> p.id.toString().toLowerCase().replace("-", "").startsWith(filter)));
    private static final TriPredicate<StorageInfo, String, Boolean> FILTER_DISK_BY_NAME_CONTAINS = (inf, filter, includePartitions) -> inf.stack.getDisplayName().getFormattedText().toLowerCase().contains(filter) || (includePartitions && inf.getPartitions().stream().anyMatch(p -> p.name.toLowerCase().contains(filter)));
    private static final TriPredicate<StorageInfo, String, Boolean> FILTER_DISK_BY_NAME_STARTS = (inf, filter, includePartitions) -> inf.stack.getDisplayName().getFormattedText().toLowerCase().startsWith(filter) || (includePartitions && inf.getPartitions().stream().anyMatch(p -> p.name.toLowerCase().startsWith(filter)));
    private static final BiPredicate<StorageInfo.Partition, String> FILTER_PARTITION_BY_ID = (part, filter) -> part.hasUniqueId() && part.id.toString().toLowerCase().replace("-", "").startsWith(filter);
    private static final BiPredicate<StorageInfo.Partition, String> FILTER_PARTITION_BY_NAME_CONTAINS = (part, filter) -> part.name.toLowerCase().contains(filter);
    private static final BiPredicate<StorageInfo.Partition, String> FILTER_PARTITION_BY_NAME_STARTS = (part, filter) -> part.name.toLowerCase().startsWith(filter);

    private static final Comparator<ItemStack> SORT_DISKS = Comparator.comparing((ItemStack i) -> i.getDisplayName().getFormattedText()).thenComparing(i -> new StorageInfo(i).getUniqueId().toString());
    private static final Comparator<StorageInfo.Partition> SORT_PARTITIONS = Comparator.comparing((StorageInfo.Partition p) -> p.name).thenComparing(p -> p.id.toString());

    public OperatingSystem(ComputingSession cs) {
        this.cs = cs;
    }

    public abstract Type getType();

    /** When the computer is first switched on via power button. **/
    public void boot() {

    }

    /** When the computer is first loaded into memory. Other tile entities may not be loaded in yet. **/
    public void wake() {

    }

    @OnlyIn(Dist.CLIENT)
    public abstract void render(TerminalSession ts, float partialTicks, int x, int y);

    @OnlyIn(Dist.CLIENT)
    public boolean keyPressed(TerminalSession ts, int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean keyReleased(TerminalSession ts, int p_keyReleased_1_, int p_keyReleased_2_, int p_keyReleased_3_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean charTyped(TerminalSession ts, char p_charTyped_1_, int p_charTyped_2_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBackgroundColor(TerminalSession ts) {
        return 0xFF222222;
    }

    @OnlyIn(Dist.CLIENT)
    protected void blit(int p_blit_1_, int p_blit_2_, int p_blit_3_, int p_blit_4_, int p_blit_5_, int p_blit_6_) {
        AbstractGui.blit(p_blit_1_, p_blit_2_, 0, (float)p_blit_3_, (float)p_blit_4_, p_blit_5_, p_blit_6_, 512, 512);
    }

    public abstract void processWork(ListNBT workQueue);

    public void onReceiveState() {

    }

    public void onSendState() {

    }

    protected ItemStack getDisk(List<ItemStack> disks, UUID id) {
        return disks.stream().filter(i -> new StorageInfo(i).getUniqueId().equals(id)).findFirst().orElse(null);
    }

    protected StorageInfo.Partition getPartition(List<ItemStack> disks, UUID id) {
        return disks.stream().flatMap(i -> new StorageInfo(i).getPartitions().stream()).filter(p -> p.id.equals(id)).findFirst().orElse(null);
    }

    protected LinkedHashMap<ItemStack, List<StorageInfo.Partition>> getDiskMap(List<ItemStack> disks, String filter, Map<ItemStack, String> shortIdsOut, Map<StorageInfo.Partition, String> shortPartitionIdsOut, boolean contains) {
        Stream<ItemStack> stream = disks.stream();
        boolean filterID = false;

        Map<ItemStack, String> shortIds = StorageInfo.getShortIds(disks, true);
        Map<StorageInfo.Partition, String> shortPartitionIds = StorageInfo.getShortPartitionIds(disks, true);
        if (shortIdsOut != null) {
            shortIdsOut.clear();
            shortIdsOut.putAll(shortIds);
        }
        if (shortPartitionIdsOut != null) {
            shortPartitionIdsOut.clear();
            shortPartitionIdsOut.putAll(shortPartitionIds);
        }

        if (filter != null && !filter.isEmpty()) {
            if (filter.startsWith("#")) {
                filterID = true;
                String f = filter = filter.toLowerCase().replaceAll("[^0-9a-f]", "");
                stream = stream.filter(i -> FILTER_DISK_BY_ID.test(new StorageInfo(i), f, true));
            } else {
                String f = filter = filter.toLowerCase();
                stream = stream.filter(i -> (contains ? FILTER_DISK_BY_NAME_CONTAINS : FILTER_DISK_BY_NAME_STARTS).test(new StorageInfo(i), f, true));
            }
        }
        LinkedHashMap<ItemStack, List<StorageInfo.Partition>> ret = new LinkedHashMap<>();

        boolean finalFilterID = filterID;
        String f = filter;
        stream.sorted(SORT_DISKS).forEachOrdered(i -> {
            StorageInfo inf = new StorageInfo(i);
            List<StorageInfo.Partition> parts = inf.getPartitions().stream().filter(p -> (f == null || f.isEmpty()) || (finalFilterID ? FILTER_DISK_BY_ID.test(inf, f, false) : FILTER_DISK_BY_NAME_CONTAINS.test(inf, f, false)) || (finalFilterID ? FILTER_PARTITION_BY_ID.test(p, f) : FILTER_PARTITION_BY_NAME_CONTAINS.test(p, f))).sorted(SORT_PARTITIONS).collect(Collectors.toList());
            ret.put(i, parts);
        });

        return ret;
    }

    protected LinkedHashMap<ItemStack, List<StorageInfo.Partition>> getDiskMap(List<ItemStack> disks, List<StorageInfo.Partition> partitions, Map<ItemStack, String> shortIdsOut, Map<StorageInfo.Partition, String> shortPartitionIdsOut) {
        Map<ItemStack, String> shortIds = StorageInfo.getShortIds(disks, true);
        Map<StorageInfo.Partition, String> shortPartitionIds = StorageInfo.getShortPartitionIds(disks, true);
        if (shortIdsOut != null) {
            shortIdsOut.clear();
            shortIdsOut.putAll(shortIds);
        }
        if (shortPartitionIdsOut != null) {
            shortPartitionIdsOut.clear();
            shortPartitionIdsOut.putAll(shortPartitionIds);
        }

        return partitions.stream().sorted(SORT_PARTITIONS)
            .collect(Collectors.toMap(p -> this.getPartitionDisk(disks, p), p -> new ArrayList<>(Collections.singleton(p)), (v1, v2) -> {
                v1.addAll(v2);
                return v1;
            })).entrySet().stream().sorted((o1, o2) -> SORT_DISKS.compare(o1.getKey(), o2.getKey())).collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (x,y) -> {throw new AssertionError();},
            LinkedHashMap::new
        ));
    }

    protected List<ItemStack> searchDisks(List<ItemStack> disks, String filter, boolean contains) {
        Stream<ItemStack> stream = disks.stream();

        if (filter != null && !filter.isEmpty()) {
            if (filter.startsWith("#")) {
                String f = filter.toLowerCase().replaceAll("[^0-9a-f]", "");
                stream = stream.filter(i -> FILTER_DISK_BY_ID.test(new StorageInfo(i), f, false));
            } else {
                String f = filter.toLowerCase();
                stream = stream.filter(i -> (contains ? FILTER_DISK_BY_NAME_CONTAINS : FILTER_DISK_BY_NAME_STARTS).test(new StorageInfo(i), f, false));
            }
        }
        return stream.sorted(SORT_DISKS).collect(Collectors.toList());
    }

    protected List<StorageInfo.Partition> searchPartitions(List<ItemStack> disks, String filter, String diskFilter, boolean contains) {
        disks = this.searchDisks(disks, diskFilter, contains);

        Stream<StorageInfo.Partition> stream = disks.stream().flatMap(d -> new StorageInfo(d).getPartitions().stream());
        if (filter != null && !filter.isEmpty()) {
            if (filter.startsWith("#")) {
                String f = filter.toLowerCase().replaceAll("[^0-9a-f]", "");
                stream = stream.filter(p -> FILTER_PARTITION_BY_ID.test(p, f));
            } else {
                String f = filter.toLowerCase();
                stream = stream.filter(p -> (contains ? FILTER_PARTITION_BY_NAME_CONTAINS : FILTER_PARTITION_BY_NAME_STARTS).test(p, f));
            }
        }
        return stream.sorted(SORT_PARTITIONS).collect(Collectors.toList());
    }

    protected ItemStack getPartitionDisk(List<ItemStack> disks, StorageInfo.Partition p) {
        return disks.stream().filter(i -> new StorageInfo(i).getPartitions().contains(p)).findFirst().orElse(null);
    }

    public enum Type {
        BIOS(-1, BIOSOperatingSystem::new),
        CONSOLE(400, ConsoleOperatingSystem::new),
        GRAPHICAL(4000, GraphicalOperatingSystem::new);

        public final int size;
        public final Function<ComputingSession, OperatingSystem> factory;

        Type(int size, Function<ComputingSession, OperatingSystem> factory) {
            this.size = size;
            this.factory = factory;
        }

        public static Type from(String type) {
            return Arrays.stream(Type.values()).filter(t -> t.name().equalsIgnoreCase(type)).findFirst().orElseThrow(RuntimeException::new);
        }
    }

    public static class FolderPath {

        private final String path;

        public final boolean absolute;
        public final String[] folders;

        public FolderPath(String path) {
            this.path = path.replace('\\', '/');

            this.absolute = path.startsWith("/");
            this.folders = (this.absolute ? path.substring(1) : path).split("/");
        }

    }

    public static class SystemPath {

        private final String path;
        public final boolean valid;

        public final String disk;
        public final String partition;
        public final FolderPath folders;

        public SystemPath(String path) {
            this.path = path.replace('\\', '/');

            String s = this.path;
            String[] s1 = s.split(":", 2);
            if (s1.length > 1) {
                this.disk = s1[0];
                s = s1[1];
            } else {
                this.disk = null;
            }
            String[] s2 = s.split("/", 2);
            if (s2.length > 1) {
                this.partition = s2[0];
                this.folders = new FolderPath("/" + s2[1]);
            } else {
                if (s1.length > 1) {
                    this.partition = s1[1];
                    this.folders = null;
                } else {
                    this.partition = null;
                    this.folders = null;
                    this.valid = false;
                    return;
                }
            }
            this.valid = true;
        }

    }

}
