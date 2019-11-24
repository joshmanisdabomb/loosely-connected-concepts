package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static com.joshmanisdabomb.lcc.item.ComputingItem.LOG2;

public class StorageItem extends Item implements TintedItem {

    private final int sizeMin;
    private final int sizeMax;
    private final int amountCost;
    private final int typeCost;
    private final int typeLimit;

    public StorageItem(int sizeMin, int sizeMax, int amountCost, int typeCost, int typeLimit, Properties p) {
        super(p.maxStackSize(1));
        this.addPropertyOverride(new ResourceLocation(LCC.MODID, "computing_level"), (stack, world, entity) -> (float)(Math.log(stack.getOrCreateChildTag("lcc:computing").getInt("size") / (double)sizeMin) / LOG2));
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
        CompoundNBT tag = stack.getOrCreateChildTag("lcc:computing");
        ListNBT partitions = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
        int total = 0;
        for (INBT t : partitions) {
            CompoundNBT partition = (CompoundNBT) t;
            total += partition.getInt("size");
        }
        return 1 - (total / (double)tag.getInt("size"));
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return super.getRGBDurabilityForDisplay(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        CompoundNBT tag = stack.getChildTag("lcc:computing");
        if (tag != null) {
            if (Screen.hasShiftDown()) {
                UUID id = tag.getUniqueId("id");
                tooltip.add(new TranslationTextComponent("item.lcc.computing_storage.id").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.id.value", (id.getLeastSignificantBits() == 0 && id.getMostSignificantBits() == 0 ? "not yet set" : id)).applyTextStyle(TextFormatting.DARK_GRAY)));
            }
            tooltip.add(new TranslationTextComponent("item.lcc.computing_storage.size").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.size.value", tag.getInt("size")).applyTextStyle(TextFormatting.DARK_GRAY)));
            ListNBT partitions = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
            if (!partitions.isEmpty()) tooltip.add(new TranslationTextComponent("item.lcc.computing_storage.partitions").applyTextStyle(TextFormatting.GRAY));
            for (INBT t : partitions) {
                CompoundNBT partition = (CompoundNBT)t;
                TextFormatting color;
                switch (partition.getString("type")) {
                    case "os_console":
                        color = TextFormatting.GOLD;
                        break;
                    case "os_graphical":
                        color = TextFormatting.AQUA;
                        break;
                    default:
                        color = TextFormatting.RED;
                        break;
                }
                tooltip.add(new StringTextComponent("  " + partition.getString("name")).applyTextStyle(color));
                tooltip.add(new StringTextComponent("    ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.size").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc.computing_storage.size.value", partition.getInt("size")).applyTextStyle(TextFormatting.DARK_GRAY))));
            }
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack stack = new ItemStack(this);
            CompoundNBT tag = stack.getOrCreateChildTag("lcc:computing");
            tag.putInt("color", 0xCC44CC);
            tag.putInt("size", this.sizeMin);
            items.add(stack.copy());
            if (this.sizeMax != this.sizeMin) {
                tag.putInt("color", 0xDDAADD);
                tag.putInt("size", this.sizeMax);
                items.add(stack.copy());
            }
            if (this == LCCItems.compact_disc) {
                tag.putInt("color", 0xB83D14);
                ListNBT partitions = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
                CompoundNBT partition = new CompoundNBT();
                partition.putString("name", "Console OS");
                partition.putString("type", "os_console");
                partition.putInt("size", 400);
                partition.putInt("start", 0);
                partitions.add(partition);
                tag.put("partitions", partitions);
                items.add(stack.copy());
            } else if (this == LCCItems.memory_card) {
                tag.putInt("color", 0x4BBDF2);
                tag.putInt("size", this.sizeMax);
                ListNBT partitions = tag.getList("partitions", Constants.NBT.TAG_COMPOUND);
                CompoundNBT partition = new CompoundNBT();
                partition.putString("name", "Graphical OS");
                partition.putString("type", "os_graphical");
                partition.putInt("size", 4000);
                partition.putInt("start", 0);
                partitions.add(partition);
                tag.put("partitions", partitions);
                items.add(stack.copy());
            }
        }
    }

    @Override
    public int getItemTintColor(ItemStack stack, int tintIndex) {
        if (tintIndex != 1) return 0xFFFFFF;
        CompoundNBT tag = stack.getOrCreateChildTag("lcc:computing");
        return tag.contains("color", Constants.NBT.TAG_ANY_NUMERIC) ? tag.getInt("color") : DyeColor.WHITE.getColorValue();
    }

}