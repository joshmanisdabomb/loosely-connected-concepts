package com.joshmanisdabomb.lcc.computing.system;

import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.computing.ComputingSession;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class BIOSOperatingSystem extends OperatingSystem {

    private final HashMap<OperatingSystem.Type, ArrayList<Pair<Integer, Integer>>> osPartitions = new HashMap<>();

    public BIOSOperatingSystem(TerminalTileEntity t, ComputingSession s) {
        super(t, s);
    }

    @Override
    public Type getType() {
        return Type.BIOS;
    }

    @Override
    public void render(float partialTicks) {

    }

    @Override
    public int getBackgroundColor() {
        return 0xFF171717;
    }

    private void progress(OperatingSystem.Type os, int start, int size) {
        if (!osPartitions.containsKey(os)) osPartitions.put(os, new ArrayList<>());
        osPartitions.get(os).add(Pair.of(start, size));
    }

    public OperatingSystem load() {
        List<ItemStack> disks = this.session.computer.getLocalDisks();
        for (ItemStack disk : disks) {
            CompoundNBT tag = disk.getOrCreateChildTag("lcc:computing");
            ListNBT partitions = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
            for (INBT t : partitions) {
                CompoundNBT partition = (CompoundNBT)t;
                String type = partition.getString("type");
                if (type.startsWith("os_")) {
                    this.progress(OperatingSystem.Type.from(type.substring(3)), partition.getInt("start"), partition.getInt("size"));
                }
            }
        }
        for (Map.Entry<OperatingSystem.Type, ArrayList<Pair<Integer, Integer>>> e : osPartitions.entrySet()) {
            List<Pair<Integer, Integer>> progressList = e.getValue().stream().sorted(Comparator.comparingInt(Pair::getLeft)).collect(Collectors.toList());
            int seek = 0;
            for (Pair<Integer, Integer> progress : progressList) {
                if (seek < progress.getLeft()) break;
                seek = Math.max(seek, progress.getLeft() + progress.getRight());
                if (seek >= e.getKey().size) return e.getKey().factory.apply(this.terminal, this.session);
            }
        }
        return this;
    }

}
