package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class LabelConsoleCommandHandler implements ConsoleCommandHandler.Server {

    @Override
    public void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work) {
        String disk = null, item = null, label = null;
        List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
        if (args.length < 1) {
            cos.write(this.format(pretranslations, "computing.lcc.console.few_args", work.getString("interpreter").split(" ", 2)[0]));
            return;
        } else if (args.length > 1) {
            item = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            OperatingSystem.SystemPath sp = new OperatingSystem.SystemPath(item);
            if (sp.valid && sp.disk != null && sp.partition != null) {
                disk = sp.disk;
                item = sp.partition;
            }
        }
        label = args[0];
        ItemStack d = null;
        StorageInfo.Partition p = null;
        if (item == null) {
            p = cos.using(disks);
            if (p == null) {
                cos.write(this.format(pretranslations, "computing.lcc.console.label.no_partition"));
                return;
            }
        } else {
            UnaryOperator<String> translator = this.getTranslator(pretranslations);
            List<StorageInfo.Partition> pSearch = cos.searchPartitions(disks, item, disk, false, translator);
            List<ItemStack> dSearch = disk != null && !disk.isEmpty() ? new ArrayList<>() : cos.searchDisks(disks, item, false, translator);
            if (pSearch.size() + dSearch.size() <= 0) {
                cos.write(this.format(pretranslations, "computing.lcc.console.label.no_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), item, disk));
                return;
            } else if (pSearch.size() + dSearch.size() > 1) {
                cos.write(this.format(pretranslations, "computing.lcc.console.label.many_results" + (disk != null && !disk.isEmpty() ? ".disk" : ""), item, disk));
                return;
            }
            p = !pSearch.isEmpty() ? pSearch.get(0) : null;
            d = !dSearch.isEmpty() ? dSearch.get(0) : null;
        }
        if (p != null) {
            p.name = label;
            StorageInfo inf = new StorageInfo(cos.getPartitionDisk(disks, p)).changePartition(p);
            cos.write(this.format(pretranslations, "computing.lcc.console.label.success.partition", StorageInfo.getShortPartitionId(disks, p, true), label));
        } else {
            StorageInfo inf = new StorageInfo(d).setName(label);
            cos.write(this.format(pretranslations, "computing.lcc.console.label.success.disk", StorageInfo.getShortId(disks, d, true), label));
        }
    }

    @Override
    public String[] getPretranslateKeys() {
        return new String[]{
            "computing.lcc.console.label.success.disk",
            "computing.lcc.console.label.success.partition",
            "computing.lcc.console.few_args",
            "computing.lcc.console.label.no_partition",
            "computing.lcc.console.label.no_results",
            "computing.lcc.console.label.many_results",
            "computing.lcc.console.label.no_results.disk",
            "computing.lcc.console.label.many_results.disk"
        };
    }

}
