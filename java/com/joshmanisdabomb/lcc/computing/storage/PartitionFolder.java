package com.joshmanisdabomb.lcc.computing.storage;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Map;

public class PartitionFolder {

    private final HashMap<ImmutablePair<Item, CompoundNBT>, Long> items = new HashMap<>();
    private final HashMap<String, PartitionFolder> folders = new HashMap<>();

    private PartitionFolder parent;

    public PartitionFolder addItem(ItemStack stack) {
        this.addItem(stack, stack.getCount());
        return this;
    }

    public PartitionFolder addItem(ItemStack stack, long count) {
        CompoundNBT nbt = new CompoundNBT();
        stack.write(nbt);
        nbt.remove("id");
        nbt.remove("Count");
        this.addItem(stack.getItem(), count, nbt);
        return this;
    }

    public PartitionFolder addItem(IItemProvider item, long count, CompoundNBT extra) {
        ImmutablePair<Item, CompoundNBT> key = ImmutablePair.of(item.asItem(), extra);
        items.merge(key, count, Long::sum);
        return this;
    }

    public PartitionFolder addFolder(String name, PartitionFolder folder) {
        folders.put(name, folder);
        folder.parent = this;
        return folder;
    }

    public CompoundNBT write(CompoundNBT nbt) {
        ListNBT items = new ListNBT();
        for (Map.Entry<ImmutablePair<Item, CompoundNBT>, Long> e : this.items.entrySet()) {
            CompoundNBT n = new CompoundNBT();
            n.putString("id", e.getKey().getLeft().getRegistryName().toString());
            n.putLong("Count", e.getValue());
            n.put("Extra", e.getKey().getRight());
            items.add(n);
        }
        nbt.put("items", items);
        CompoundNBT folders = new CompoundNBT();
        for (Map.Entry<String, PartitionFolder> e : this.folders.entrySet()) {
            CompoundNBT n = new CompoundNBT();
            e.getValue().write(n);
            folders.put(e.getKey(), n);
        }
        nbt.put("folders", folders);
        return nbt;
    }

    public static PartitionFolder read(CompoundNBT nbt) {
        PartitionFolder pf = new PartitionFolder();
        ListNBT items = nbt.getList("items", Constants.NBT.TAG_COMPOUND);
        for (INBT t : items) {
            CompoundNBT n = (CompoundNBT)t;
            ImmutablePair<Item, CompoundNBT> key = ImmutablePair.of(Registry.ITEM.getOrDefault(new ResourceLocation(n.getString("id"))), (CompoundNBT) n.get("Extra"));
            pf.items.merge(key, n.getLong("Count"), Long::sum);
        }
        CompoundNBT folders = nbt.getCompound("folders");
        for (String key : folders.keySet()) {
            CompoundNBT n = folders.getCompound(key);
            PartitionFolder pf2 = PartitionFolder.read(n);
            pf2.parent = pf;
            pf.folders.put(key, pf2);
        }
        return pf;
    }

    /*public void println() {
        for (Map.Entry<ImmutablePair<Item, CompoundNBT>, Long> e : items.entrySet()) {
            System.out.println(e.getKey().left);
            System.out.println(e.getKey().right);
            System.out.println(e.getValue());
            ItemStack s = new ItemStack(e.getKey().left, e.getValue().intValue(), e.getKey().right.contains("ForgeCaps", Constants.NBT.TAG_COMPOUND) ? e.getKey().right.getCompound("ForgeCaps") : null);
            if (e.getKey().right.contains("tag", Constants.NBT.TAG_COMPOUND)) s.setTag(e.getKey().right.getCompound("tag"));
            System.out.println(s);
        }
    }*/

}
