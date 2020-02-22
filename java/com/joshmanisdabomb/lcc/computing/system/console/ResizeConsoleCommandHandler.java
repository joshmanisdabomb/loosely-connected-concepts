package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.Arrays;
import java.util.List;

public class ResizeConsoleCommandHandler implements ConsoleCommandHandler.Server {

    @Override
    public void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work) {
        int size = -1;
        if (args.length > 0) {
            try {
                size = Integer.valueOf(args[0]);
                if (size < 0) {
                    cos.write(this.format(pretranslations, "computing.lcc.console.resize.invalid_size"));
                    return;
                }
            } catch (NumberFormatException ignored) {}
        }
        String partition = null, disk = null;
        int noSize = size == -1 ? -1 : 0;
        if (args.length > 1 + noSize) {
            OperatingSystem.SystemPath sp = new OperatingSystem.SystemPath(String.join(" ", Arrays.copyOfRange(args, 1 + noSize, args.length)));
            if (sp.valid && sp.disk != null && sp.partition != null) {
                disk = sp.disk;
                partition = sp.partition;
            } else {
                if (args.length > 2 + noSize) {
                    disk = String.join(" ", Arrays.copyOfRange(args, 1 + noSize, args.length - 1));
                    partition = args[args.length - 1];
                } else {
                    disk = args[1 + noSize];
                }
            }
        }
        List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
        StorageInfo.Partition p;
        if (partition == null && disk == null) {
            p = cos.using(disks);
            if (p == null) {
                cos.write(this.format(pretranslations, "computing.lcc.console.resize.no_partition"));
                return;
            }
        } else {
            List<StorageInfo.Partition> partitions = cos.searchPartitions(disks, partition, disk, false, this.getTranslator(pretranslations));
            if (partitions.size() <= 0) {
                cos.write(this.format(pretranslations, "computing.lcc.console.resize.no_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), partition, disk));
                return;
            } else if (partitions.size() > 1) {
                cos.write(this.format(pretranslations, "computing.lcc.console.resize.many_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), partition, disk));
                return;
            } else {
                p = partitions.get(0);
            }
        }
        if (p.type.isOS()) {
            cos.write(this.format(pretranslations, "computing.lcc.console.resize.os"));
            return;
        }
        StorageInfo inf = new StorageInfo(cos.getPartitionDisk(disks, p));
        int space = inf.getPartitionableSpace();
        if (size <= 0) size = p.size + space;
        int offset = size - p.size;
        if (offset == 0) {
            cos.write(this.format(pretranslations, "computing.lcc.console.resize.no_change"));
            return;
        } else if (offset > 0) {
            if (space <= 0) {
                cos.write(this.format(pretranslations, "computing.lcc.console.resize.no_space"));
                return;
            }
            if (offset > space) {
                cos.write(this.format(pretranslations, "computing.lcc.console.resize.low_space", p.size + space));
                return;
            }
        } else {
            if (p.getUsedSpace() > size) {
                cos.write(this.format(pretranslations, "computing.lcc.console.resize.used_space", p.getUsedSpace()));
                return;
            }
        }
        cos.write(this.format(pretranslations, "computing.lcc.console.resize.success", p.name, StorageInfo.getShortPartitionId(disks, p, true), this.getDiskName(pretranslations, inf.stack), StorageInfo.getShortId(disks, inf.stack, true), p.size, p.size + offset));
        p.size += offset;
        inf.changePartition(p);
    }

    @Override
    public String[] getPretranslateKeys() {
        return new String[]{
            "computing.lcc.console.resize.success",
            "computing.lcc.console.resize.os",
            "computing.lcc.console.resize.used_space",
            "computing.lcc.console.resize.low_space",
            "computing.lcc.console.resize.no_space",
            "computing.lcc.console.resize.no_partition",
            "computing.lcc.console.resize.no_results",
            "computing.lcc.console.resize.many_results",
            "computing.lcc.console.resize.no_results.disk",
            "computing.lcc.console.resize.many_results.disk",
            "computing.lcc.console.resize.invalid_size",
            "computing.lcc.console.resize.no_change"
        };
    }

}
