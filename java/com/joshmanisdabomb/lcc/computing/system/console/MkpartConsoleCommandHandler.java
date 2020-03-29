package com.joshmanisdabomb.lcc.computing.system.console;

import com.joshmanisdabomb.lcc.capability.PartitionCapability;
import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.storage.PartitionFolder;
import com.joshmanisdabomb.lcc.computing.system.ConsoleOperatingSystem;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MkpartConsoleCommandHandler implements ConsoleCommandHandler.Server {

    @Override
    public void handle(ConsoleOperatingSystem cos, String[] args, String[] pretranslations, CompoundNBT work) {
        if (args.length < 2) {
            cos.write(this.format(pretranslations, "computing.lcc.console.few_args", work.getString("interpreter").split(" ", 2)[0]));
            return;
        }
        StorageInfo.Partition.PartitionType type = null;
        int partitionTypeOffset = 1;
        if (args.length > 2) {
            for (partitionTypeOffset = args.length - 2; partitionTypeOffset > 0; partitionTypeOffset--) {
                type = StorageInfo.Partition.PartitionType.byName(args[partitionTypeOffset]);
                if (type != null && !type.isOS()) break;
            }
        } else {
            type = StorageInfo.Partition.PartitionType.byName(args[partitionTypeOffset]);
        }
        if (type == null || type.isOS()) {
            cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.invalid_type", Arrays.stream(StorageInfo.Partition.PartitionType.values()).filter(p -> !p.isOS()).map(StorageInfo.Partition.PartitionType::getName).collect(Collectors.joining(", "))));
            return;
        }
        int size = -1;
        try {
            size = Integer.valueOf(args[args.length - 1]);
            if (size <= 0) {
                cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.invalid_size"));
                return;
            }
        } catch (NumberFormatException ignored) {}
        String partition = String.join(" ", Arrays.copyOfRange(args, 0, partitionTypeOffset));
        String disk = args.length <= (size < 0 ? 2 : 3) ? null : String.join(" ", Arrays.copyOfRange(args, partitionTypeOffset + 1, args.length - (size > 0 ? 1 : 0)));
        if (disk == null) {
            OperatingSystem.SystemPath sp = new OperatingSystem.SystemPath(partition);
            if (sp.valid && sp.disk != null && sp.partition != null) {
                disk = sp.disk;
                partition = sp.partition;
            }
        }
        List<ItemStack> disks = cos.cs.computer.getNetworkDisks();
        ItemStack d;
        if (disk == null) {
            StorageInfo.Partition p = cos.using(disks);
            if (p == null) {
                cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.no_disk"));
                return;
            }
            d = cos.getPartitionDisk(disks, p);
        } else {
            List<ItemStack> results = cos.searchDisks(disks, disk, false, this.getTranslator(pretranslations));
            if (results.size() <= 0) {
                cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.invalid_disk", disk));
                return;
            } else if (results.size() > 1) {
                cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.many_disk", disk));
                return;
            }
            d = results.get(0);
        }
        StorageInfo inf = new StorageInfo(d);
        String finalPartition = partition;
        if (inf.getPartitions().stream().anyMatch(p -> p.name.equalsIgnoreCase(finalPartition))) {
            cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.existing", partition, this.getDiskName(pretranslations, d), StorageInfo.getShortId(disks, d, true)));
            return;
        }
        int space = inf.getPartitionableSpace();
        if (space <= 0) {
            cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.no_space"));
            return;
        }
        if (size <= 0) size = space;
        if (size > space) {
            cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.low_space", space));
            return;
        }
        StorageInfo.Partition newPart = new StorageInfo.Partition(UUID.randomUUID(), partition, type, size);
        inf.addPartition(newPart);
        //TODO temporary test
        PartitionCapability.Provider.getGlobal(cos.cs.computer.te.getWorld().getServer()).ifPresent(pc -> {
            pc.get(newPart).addItem(new ItemStack(Blocks.GRASS_BLOCK, 2));
            pc.get(newPart).addItem(new ItemStack(Blocks.GRASS_BLOCK, 3));
            pc.get(newPart).addItem(new ItemStack(Blocks.GRASS_BLOCK, 4));

            ItemStack s;
            (s = new ItemStack(Items.DIAMOND_SWORD, 3)).getOrCreateTag().putInt("Damage", 900);
            pc.get(newPart).addItem(s, 3);

            CompoundNBT n = new CompoundNBT();
            n.put("tag", new CompoundNBT());
            n.getCompound("tag").putInt("Damage", 900);
            pc.get(newPart).addItem(Items.IRON_SWORD, 7, n);

            pc.get(newPart).addItem(LCCBlocks.atomic_bomb, 7000000000000L, new CompoundNBT());

            pc.get(newPart).addFolder("bigking", new PartitionFolder()).addItem(LCCBlocks.refined_stripped_candy_cane_coating, 69, new CompoundNBT());
        });
        cos.use(newPart);
        cos.write(this.format(pretranslations, "computing.lcc.console.mkpart.success", newPart.type.getName(), newPart.name, StorageInfo.getShortPartitionId(disks, newPart, true), newPart.size, this.getDiskName(pretranslations, d), StorageInfo.getShortId(disks, d, true)));
    }

    @Override
    public String[] getPretranslateKeys() {
        return new String[]{
            "computing.lcc.console.mkpart.success",
            "computing.lcc.console.mkpart.invalid_type",
            "computing.lcc.console.mkpart.invalid_size",
            "computing.lcc.console.mkpart.low_space",
            "computing.lcc.console.mkpart.no_space",
            "computing.lcc.console.few_args",
            "computing.lcc.console.mkpart.no_disk",
            "computing.lcc.console.mkpart.invalid_disk",
            "computing.lcc.console.mkpart.many_disk",
            "computing.lcc.console.mkpart.existing"
        };
    }

}
