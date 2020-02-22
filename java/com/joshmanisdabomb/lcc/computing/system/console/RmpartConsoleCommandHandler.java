package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.Arrays;
import java.util.List;

public class RmpartConsoleCommandHandler implements ConsoleCommandHandler.Server {

    @Override
    public void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work) {
        String disk = null, partition = null;
        if (args.length > 1) {
            partition = String.join(" ", Arrays.copyOfRange(args, 0, args.length - 1));
            disk = args[args.length - 1];
        } else if (args.length == 1) {
            OperatingSystem.SystemPath sp = new OperatingSystem.SystemPath(args[0]);
            if (sp.valid && sp.disk != null && sp.partition != null) {
                disk = sp.disk;
                partition = sp.partition;
            } else {
                partition = args[0];
            }
        }

        List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
        StorageInfo.Partition p;
        if (partition == null) {
            p = cos.using(disks);
            if (p == null) {
                cos.write(this.format(pretranslations, "computing.lcc.console.rmpart.invalid_use"));
                return;
            }
        } else {
            List<StorageInfo.Partition> results = cos.searchPartitions(disks, partition, disk, false, this.getTranslator(pretranslations));
            if (results.size() <= 0) {
                cos.write(this.format(pretranslations, "computing.lcc.console.rmpart.no_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), partition, disk));
                return;
            } else if (results.size() > 1) {
                cos.write(this.format(pretranslations, "computing.lcc.console.rmpart.many_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), partition, disk));
                return;
            }
            p = results.get(0);
        }

        ItemStack d = cos.getPartitionDisk(disks, p);
        cos.line("-");
        cos.scroll();
        cos.print(this.format(pretranslations, "computing.lcc.console.rmpart.prompt", p.name, StorageInfo.getShortPartitionId(disks, p, true), this.getDiskName(pretranslations, d), StorageInfo.getShortId(disks, d, true)));
        cos.line("-");
        CompoundNBT w = new CompoundNBT();
        w.putString("action", "rmpart");
        w.putUniqueId("part", p.id);
        w.putString("success", this.format(pretranslations, "computing.lcc.console.rmpart.success", p.name));
        w.putString("missing", this.format(pretranslations, "computing.lcc.console.rmpart.missing"));
        cos.prompt(w);
    }

    @Override
    public String[] getPretranslateKeys() {
        return new String[]{
            "computing.lcc.console.rmpart.prompt",
            "computing.lcc.console.rmpart.success",
            "computing.lcc.console.rmpart.no_results",
            "computing.lcc.console.rmpart.no_results.disk",
            "computing.lcc.console.rmpart.many_results",
            "computing.lcc.console.rmpart.many_results.disk",
            "computing.lcc.console.rmpart.invalid_use",
            "computing.lcc.console.rmpart.missing"
        };
    }

}
