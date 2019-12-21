package com.joshmanisdabomb.lcc.computing;

import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import com.joshmanisdabomb.lcc.network.ComputerStateChangePacket;
import com.joshmanisdabomb.lcc.network.LCCPacketHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
        computer.state = new CompoundNBT();
        computer.state.putUniqueId("id", UUID.randomUUID());
        this.osLoad();
        this.os.boot();
    }

    public void wake() {
        String type = computer.state.getString("os_type");
        this.os = OperatingSystem.Type.from(type).factory.apply(this);
        this.os.wake();
    }

    public void osLoad() {
        List<ItemStack> disks = computer.getLocalDisks();
        //Read all disks on local network.
        for (ItemStack disk : disks) {
            StorageInfo i = new StorageInfo(disk);
            for (StorageInfo.Partition p : i.getPartitions()) {
                if (p.type.isOS()) this.osProgress(p.type.os, p.start, p.size);
            }
        }
        //Advance seek from read disk partitions and change the operating system if seek can move past the operating system size.
        HashMap<OperatingSystem.Type, Integer> seek = new HashMap<>();
        for (Map.Entry<OperatingSystem.Type, ArrayList<Pair<Integer, Integer>>> e : osPartitions.entrySet()) {
            List<Pair<Integer, Integer>> progressList = e.getValue().stream().sorted(Comparator.comparingInt(Pair::getLeft)).collect(Collectors.toList());
            int s = 0;
            for (Pair<Integer, Integer> progress : progressList) {
                if (s < progress.getLeft()) break;
                s = Math.max(s, progress.getLeft() + progress.getRight());
                if (s >= e.getKey().size) this.changeOS(e.getKey().factory.apply(this));
            }
            seek.put(e.getKey(), s);
        }
        //Use the seeks added to the hash map to display an error on the BIOS.
        if (this.os == null || this.os.getType() == OperatingSystem.Type.BIOS) {
            if (this.os == null) this.changeOS(OperatingSystem.Type.BIOS.factory.apply(this));
            CompoundNBT seeks = new CompoundNBT();
            for (Map.Entry<OperatingSystem.Type, ArrayList<Pair<Integer, Integer>>> e : osPartitions.entrySet()) {
                seeks.putInt(e.getKey().name().toLowerCase(), seek.get(e.getKey()));
            }
            computer.state.put("os_seeks", seeks);
        }
    }

    private void osProgress(OperatingSystem.Type os, int start, int size) {
        if (!osPartitions.containsKey(os)) osPartitions.put(os, new ArrayList<>());
        Pair<Integer, Integer> progress = Pair.of(start, size);
        if (!osPartitions.get(os).contains(progress)) osPartitions.get(os).add(progress);
    }

    private void changeOS(OperatingSystem os) {
        this.os = os;
        computer.state.putString("os_type", this.os.getType().name().toLowerCase());
    }

    public OperatingSystem getOS() {
        return os;
    }

    public CompoundNBT getState() {
        return computer.state;
    }

    public void receiveState() {
        this.os.onReceiveState();
    }

    public void sendState() {
        this.os.onSendState();
        if (computer.te.getWorld().isRemote) {
            LCCPacketHandler.send(PacketDistributor.SERVER.noArg(), new ComputerStateChangePacket(computer.te.getWorld().getDimension().getType(), computer.te.getPos(), computer.location, computer.state, computer.isReading()));
        }
    }

}
