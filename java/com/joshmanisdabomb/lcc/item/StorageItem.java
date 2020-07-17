package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class StorageItem extends ComputingItem implements TintedItem {

    private final int amountCost;
    private final int typeCost;
    private final int typeLimit;

    public StorageItem(int[] levels, int amountCost, int typeCost, int typeLimit, Properties p) {
        super(levels, p.maxStackSize(1));
        this.amountCost = amountCost;
        this.typeCost = typeCost;
        this.typeLimit = typeLimit;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return Screen.hasShiftDown();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        StorageInfo i = new StorageInfo(stack);
        return 1 - (i.getPartitionedSpace() / (double)i.getSize());
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return super.getRGBDurabilityForDisplay(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if (stack.getChildTag(StorageInfo.TAG) != null) {
            StorageInfo i = new StorageInfo(stack);
            if (Screen.hasShiftDown()) {
                tooltip.add(new TranslationTextComponent("item.lcc.computing_storage.id").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.id.value", (i.hasUniqueId() ? i.getUniqueId() : "not yet set")).applyTextStyle(TextFormatting.DARK_GRAY)));
            }
            tooltip.add(new TranslationTextComponent("item.lcc.computing_storage.size").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.size.value", i.getSize()).applyTextStyle(TextFormatting.DARK_GRAY)));
            ArrayList<StorageInfo.Partition> partitions = i.getPartitions();
            if (!partitions.isEmpty()) tooltip.add(new TranslationTextComponent("item.lcc.computing_storage.partitions").applyTextStyle(TextFormatting.GRAY));
            for (StorageInfo.Partition p : partitions) {
                tooltip.add(new StringTextComponent("  " + p.name).applyTextStyle(p.type.color));
                if (Screen.hasShiftDown()) {
                    tooltip.add(new StringTextComponent("    ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.id").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.id.value", (p.hasUniqueId() ? p.getUniqueId() : "not yet set")).applyTextStyle(TextFormatting.DARK_GRAY))));
                }
                tooltip.add(new StringTextComponent("    ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.size").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.size.value", p.size).applyTextStyle(TextFormatting.DARK_GRAY))));
            }
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            super.fillItemGroup(group, items);
            ItemStack stack = new ItemStack(this);
            StorageInfo i = new StorageInfo(stack);
            int last = levels[levels.length - 1];
            if (last >= OperatingSystem.Type.CONSOLE.size) {
                i.setSize(last);
                i.setColor(0xB83D14);
                StorageInfo.Partition p = new StorageInfo.Partition(null, "Console OS", StorageInfo.Partition.PartitionType.OS_CONSOLE, OperatingSystem.Type.CONSOLE.size);
                i.addPartition(p);
                items.add(stack.copy());
            }
            if (last >= OperatingSystem.Type.GRAPHICAL.size) {
                i.setSize(last);
                i.setColor(0x4BBDF2);
                StorageInfo.Partition p = new StorageInfo.Partition(null, "Graphical OS", StorageInfo.Partition.PartitionType.OS_GRAPHICAL, OperatingSystem.Type.GRAPHICAL.size);
                i.clearPartitions();
                i.addPartition(p);
                items.add(stack.copy());
            }
        }
    }

    @Override
    public int getItemTintColor(ItemStack stack, int tintIndex) {
        if (tintIndex != 1) return 0xFFFFFF;
        StorageInfo i = new StorageInfo(stack);
        return i.hasColor() ? i.getColor() : DyeColor.WHITE.getColorValue();
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        StorageInfo i = new StorageInfo(stack);
        return i.hasName() ? new StringTextComponent(i.getName()) : super.getDisplayName(stack);
    }

    @Override
    protected String getLevelTag() {
        return "size";
    }

    @Override
    protected ItemStack fillItemGroupStack(ItemStack is, CompoundNBT tag, int level) {
        float percent = (float)this.getLevelIndex(level) / (levels.length - 1);
        tag.putInt("color", 0x323232 + (int)(0x70 * percent));
        return super.fillItemGroupStack(is, tag, level);
    }

}