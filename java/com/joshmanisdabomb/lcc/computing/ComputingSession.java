package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import com.joshmanisdabomb.lcc.network.ComputerStateChangePacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ComputingSession {

    public final ComputingModule computer;
    private final HashMap<OperatingSystem.Type, ArrayList<Pair<Integer, Integer>>> osPartitions = new HashMap<>();

    private OperatingSystem os;

    public ComputingSession(ComputingModule module) {
        this.computer = module;
    }

    public void boot() {
        List<ItemStack> disks = computer.getLocalDisks();
        for (ItemStack disk : disks) {
            CompoundNBT tag = disk.getOrCreateChildTag("lcc:computing");
            ListNBT partitions = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
            for (INBT t : partitions) {
                CompoundNBT partition = (CompoundNBT)t;
                String type = partition.getString("type");
                if (type.startsWith("os_")) {
                    this.osProgress(OperatingSystem.Type.from(type.substring(3)), partition.getInt("start"), partition.getInt("size"));
                }
            }
        }
        for (Map.Entry<OperatingSystem.Type, ArrayList<Pair<Integer, Integer>>> e : osPartitions.entrySet()) {
            List<Pair<Integer, Integer>> progressList = e.getValue().stream().sorted(Comparator.comparingInt(Pair::getLeft)).collect(Collectors.toList());
            int seek = 0;
            for (Pair<Integer, Integer> progress : progressList) {
                if (seek < progress.getLeft()) break;
                seek = Math.max(seek, progress.getLeft() + progress.getRight());
                if (seek >= e.getKey().size) this.changeOS(e.getKey().factory.apply(this));
            }
        }
        if (this.os == null) {
            this.changeOS(OperatingSystem.Type.BIOS.factory.apply(this));
            CompoundNBT partitions = new CompoundNBT();
            for (Map.Entry<OperatingSystem.Type, ArrayList<Pair<Integer, Integer>>> e : osPartitions.entrySet()) {
                ListNBT os = partitions.getList(e.getKey().name().toLowerCase(), Constants.NBT.TAG_COMPOUND);
                for (Pair<Integer, Integer> values : e.getValue()) {
                    CompoundNBT partition = new CompoundNBT();
                    partition.putInt("start", values.getLeft());
                    partition.putInt("size", values.getRight());
                    os.add(partition);
                }
                partitions.put(e.getKey().name().toLowerCase(), os);
            }
            computer.state.put("partitions", partitions);
        }
        System.out.println(computer.state);
    }

    public void wake() {
        String type = computer.state.getString("os_type");
        this.os = OperatingSystem.Type.from(type).factory.apply(this);
        System.out.println(computer.state);
    }

    private void osProgress(OperatingSystem.Type os, int start, int size) {
        if (!osPartitions.containsKey(os)) osPartitions.put(os, new ArrayList<>());
        osPartitions.get(os).add(Pair.of(start, size));
    }

    private void changeOS(OperatingSystem os) {
        this.os = os;
        computer.state.putString("os_type", this.os.getType().name().toLowerCase());
    }

    OperatingSystem getOS() {
        return os;
    }

    public void receiveState() {

    }

    public void sendState() {
        if (computer.te.getWorld().isRemote) {
            LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new ComputerStateChangePacket(computer.te.getWorld().getDimension().getType(), computer.te.getPos(), computer.location, computer.state));
        }
    }

}
