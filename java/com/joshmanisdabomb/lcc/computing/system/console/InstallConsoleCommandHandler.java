package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class InstallConsoleCommandHandler implements ConsoleCommandHandler.Server {

    @Override
    public void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work) {
        int sector = -1;
        int size = -1;
        if (args.length > 0) {
            String[] properties = args[0].split(":", 2);
            try {
                sector = Integer.valueOf(properties[0]);
                if (sector < 0) {
                    cos.write(this.format(pretranslations, "computing.lcc.console.install.invalid_sector"));
                    return;
                }
                if (sector > cos.getType().size) {
                    cos.write(this.format(pretranslations, "computing.lcc.console.install.max_sector", cos.getType().size));
                    return;
                }
            } catch (NumberFormatException ignored) {}
            if (sector >= 0 && properties.length > 1) {
                try {
                    size = Integer.valueOf(properties[1]);
                    if (size <= 0) {
                        cos.write(this.format(pretranslations, "computing.lcc.console.install.invalid_size"));
                        return;
                    }
                    if (sector + size > cos.getType().size) {
                        cos.write(this.format(pretranslations, "computing.lcc.console.install.max_size", cos.getType().size - sector));
                        return;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        String partition = null, disk = null;
        int noSector = sector == -1 ? -1 : 0;
        if (args.length > 1 + noSector) {
            OperatingSystem.SystemPath sp = new OperatingSystem.SystemPath(String.join(" ", Arrays.copyOfRange(args, 1 + noSector, args.length)));
            if (sp.valid && sp.disk != null && sp.partition != null) {
                disk = sp.disk;
                partition = sp.partition;
            } else {
                if (args.length > 2 + noSector) {
                    disk = String.join(" ", Arrays.copyOfRange(args, 1 + noSector, args.length - 1));
                    partition = args[args.length - 1];
                } else {
                    disk = args[1 + noSector];
                }
            }
        }
        List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
        ItemStack d;
        if (disk == null) {
            StorageInfo.Partition p = cos.using(disks);
            if (p == null) {
                cos.write(this.format(pretranslations, "computing.lcc.console.install.no_disk"));
                return;
            }
            d = cos.getPartitionDisk(disks, p);
        } else {
            List<ItemStack> results = cos.searchDisks(disks, disk, false, this.getTranslator(pretranslations));
            if (results.size() <= 0) {
                cos.write(this.format(pretranslations, "computing.lcc.console.install.invalid_disk", disk));
                return;
            } else if (results.size() > 1) {
                cos.write(this.format(pretranslations, "computing.lcc.console.install.many_disk", disk));
                return;
            }
            d = results.get(0);
        }
        if (sector < 0) sector = 0;
        StorageInfo inf = new StorageInfo(d);
        if (partition == null) {
            partition = "Console OS";
            int i = 1;
            while (true) {
                String finalPartition = partition;
                if (!inf.getPartitions().stream().anyMatch(p -> p.name.equalsIgnoreCase(finalPartition))) break;
                partition = "Console OS " + ++i;
            }
        } else {
            String finalPartition = partition;
            if (inf.getPartitions().stream().anyMatch(p -> p.name.equalsIgnoreCase(finalPartition))) {
                cos.write(this.format(pretranslations, "computing.lcc.console.install.existing", partition, this.getDiskName(pretranslations, d), StorageInfo.getShortId(disks, d, true)));
                return;
            }
        }
        if (inf.getPartitions().stream().anyMatch(p -> p.type.isOS() && p.start <= 0 && p.size >= p.type.os.size)) {
            cos.write(this.format(pretranslations, "computing.lcc.console.install.os_installed"));
            return;
        }
        int space = inf.getPartitionableSpace();
        if (space <= 0) {
            cos.write(this.format(pretranslations, "computing.lcc.console.install.no_space"));
            return;
        }
        if (size <= 0) size = cos.getType().size - sector;
        if (size > space) {
            cos.write(this.format(pretranslations, "computing.lcc.console.install.low_space", space));
            return;
        }
        StorageInfo.Partition newPart = new StorageInfo.Partition(UUID.randomUUID(), partition, StorageInfo.Partition.PartitionType.OS_CONSOLE, size);
        newPart.start = sector;
        inf.addPartition(newPart);
        cos.write(this.format(pretranslations, "computing.lcc.console.install.success" + (sector == 0 && size == cos.getType().size ? "" : ".partial"), newPart.name, StorageInfo.getShortPartitionId(disks, newPart, true), this.getDiskName(pretranslations, d), StorageInfo.getShortId(disks, d, true)));
        if (sector + size < cos.getType().size) {
            cos.scroll();
            cos.write(this.format(pretranslations, "computing.lcc.console.install.success.partial.continue", sector + size, cos.getType().size - (sector + size)));
        }
    }

    @Override
    public String[] getPretranslateKeys() {
        return new String[]{
            "computing.lcc.console.install.success",
            "computing.lcc.console.install.success.partial",
            "computing.lcc.console.install.success.partial.continue",
            "computing.lcc.console.install.invalid_sector",
            "computing.lcc.console.install.invalid_size",
            "computing.lcc.console.install.low_space",
            "computing.lcc.console.install.no_space",
            "computing.lcc.console.install.no_disk",
            "computing.lcc.console.install.invalid_disk",
            "computing.lcc.console.install.many_disk",
            "computing.lcc.console.install.os_installed",
            "computing.lcc.console.install.max_sector",
            "computing.lcc.console.install.max_size",
            "computing.lcc.console.install.existing"
        };
    }

}
