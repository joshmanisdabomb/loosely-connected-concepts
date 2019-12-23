package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.Arrays;
import java.util.List;

public class UseConsoleCommandHandler implements ConsoleCommandHandler.Server {

    @Override
    public void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work) {
        String disk = null, partition = null;
        OperatingSystem.FolderPath path = null;
        if (args.length > 2) {
            partition = String.join(" ", Arrays.copyOfRange(args, 0, args.length - 1));
            disk = args[args.length - 1];
        } else if (args.length <= 0) {
            List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
            StorageInfo.Partition using = cos.using(disks);
            if (using != null) {
                ItemStack d = cos.getPartitionDisk(disks, using);
                cos.write(this.format(pretranslations, "computing.lcc.console.use.success", using.name, StorageInfo.getShortPartitionId(disks, using, true), this.getDiskName(pretranslations, d), StorageInfo.getShortId(disks, d, true)));
            } else {
                cos.write(this.format(pretranslations, "computing.lcc.console.use.none"));
            }
            return;
        } else if (args.length == 2) {
            partition = args[0];
            disk = args[1];
        } else {
            OperatingSystem.SystemPath sp = new OperatingSystem.SystemPath(args[0]);
            if (sp.valid && sp.disk != null && sp.partition != null) {
                disk = sp.disk;
                partition = sp.partition;
                path = sp.folders;
            } else {
                partition = args[0];
            }
        }

        List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
        List<StorageInfo.Partition> partitions = cos.searchPartitions(disks, partition, disk, false, this.getTranslator(pretranslations));
        if (partitions.size() <= 0) {
            cos.write(this.format(pretranslations, "computing.lcc.console.use.no_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), partition, disk));
        } else if (partitions.size() > 1) {
            cos.write(this.format(pretranslations, "computing.lcc.console.use.many_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), partition, disk));
        } else {
            StorageInfo.Partition using = partitions.get(0);
            ItemStack d = cos.getPartitionDisk(disks, using);
            cos.use(using);
            cos.write(this.format(pretranslations, "computing.lcc.console.use.success", using.name, StorageInfo.getShortPartitionId(disks, using, true), this.getDiskName(pretranslations, d), StorageInfo.getShortId(disks, d, true)));
        }
    }

    @Override
    public String[] getPretranslateKeys() {
        return new String[]{
            "computing.lcc.console.use.success",
            "computing.lcc.console.cd.success",
            "computing.lcc.console.use.no_results",
            "computing.lcc.console.use.no_results.disk",
            "computing.lcc.console.use.many_results",
            "computing.lcc.console.use.many_results.disk",
            "computing.lcc.console.use.none"
        };
    }

}
