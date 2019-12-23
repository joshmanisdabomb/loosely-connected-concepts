package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class MapConsoleCommandHandler implements ConsoleCommandHandler.Server {

    @Override
    public void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work) {
        List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
        String search = String.join(" ", args);

        Map<ItemStack, String> shortIds = new HashMap<>();
        Map<StorageInfo.Partition, String> shortPartitionIds = new HashMap<>();

        LinkedHashMap<ItemStack, List<StorageInfo.Partition>> map;
        OperatingSystem.SystemPath sp = new OperatingSystem.SystemPath(search);
        UnaryOperator<String> translator = this.getTranslator(pretranslations);
        if (sp.valid && sp.disk != null && sp.partition != null) {
            List<StorageInfo.Partition> partitions = cos.searchPartitions(disks, sp.partition, sp.disk, false, translator);
            map = cos.getDiskMap(disks, partitions, shortIds, shortPartitionIds, translator);
        } else {
            map = cos.getDiskMap(disks, search, shortIds, shortPartitionIds, true, translator);
        }

        cos.startBuffer();
        for (Map.Entry<ItemStack, List<StorageInfo.Partition>> e : map.entrySet()) {
            StorageInfo i = new StorageInfo(e.getKey());
            cos.alignOrPrint(this.getDiskName(pretranslations, e.getKey()) + " #" + shortIds.get(e.getKey()), i.getUsedSpace() + "/" + i.getSize());
            if (e.getValue().size() < 1) {
                cos.print(" - " + this.format(pretranslations, "computing.lcc.console.map.no_partitions"));
            } else {
                for (int j = 0; j < e.getValue().size(); j++) {
                    StorageInfo.Partition p = e.getValue().get(j);
                    cos.alignOrPrint(" " + (j == e.getValue().size() - 1 ? '\u2514' : '\u251C') + " " + p.name + " #" + shortPartitionIds.get(p), p.type.isOS() ? p.start + ":" + p.size : (p.getUsedSpace() + "/" + p.size));
                }
            }
        }
        cos.displayLargeBuffer();
    }

    @Override
    public String[] getPretranslateKeys() {
        return new String[]{
            "computing.lcc.console.map.no_partitions"
        };
    }

}
