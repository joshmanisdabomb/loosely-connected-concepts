package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.computing.StorageInfo;
import com.joshmanisdabomb.lcc.computing.system.OperatingSystem;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.joshmanisdabomb.lcc.item.ComputingItem.LOG2;

public class StorageItem extends Item implements TintedItem {

    private final int sizeMin;
    private final int sizeMax;
    private final int amountCost;
    private final int typeCost;
    private final int typeLimit;

    public StorageItem(int sizeMin, int sizeMax, int amountCost, int typeCost, int typeLimit, Properties p) {
        super(p.maxStackSize(1));
        this.addPropertyOverride(ComputingItem.PREDICATE, (stack, world, entity) -> (float)(Math.log(stack.getOrCreateChildTag("lcc:computing").getInt("size") / (double)sizeMin) / LOG2));
        this.sizeMin = sizeMin;
        this.sizeMax = sizeMax;
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
            ItemStack stack = new ItemStack(this);
            StorageInfo i = new StorageInfo(stack);
            i.setColor(0xCC44CC).setSize(this.sizeMin);
            items.add(stack.copy());
            if (this.sizeMax != this.sizeMin) {
                i.setColor(0xDDAADD).setSize(this.sizeMax);
                items.add(stack.copy());
            }
            if (this == LCCItems.compact_disc) {
                i.setColor(0xB83D14);
                StorageInfo.Partition p = new StorageInfo.Partition(null, "Console OS", StorageInfo.Partition.PartitionType.OS_CONSOLE, OperatingSystem.Type.CONSOLE.size);
                i.addPartition(p);
                items.add(stack.copy());
            } else if (this == LCCItems.memory_card) {
                i.setColor(0x4BBDF2).setSize(this.sizeMax);
                StorageInfo.Partition p = new StorageInfo.Partition(null, "Graphical OS", StorageInfo.Partition.PartitionType.OS_GRAPHICAL, OperatingSystem.Type.GRAPHICAL.size);
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

}