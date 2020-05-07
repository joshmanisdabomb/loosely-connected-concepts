package com.joshmanisdabomb.lcc.item;

import com.joshmanisdabomb.lcc.LCC;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ComputingItem extends Item {

    public static final ResourceLocation PREDICATE = new ResourceLocation(LCC.MODID, "computing_level");
    public static final double LOG2 = Math.log(2);

    private final int levelMin;
    private final int levelMax;

    public ComputingItem(int levelMin, int levelMax, Properties p) {
        super(p.maxStackSize(1));
        this.addPropertyOverride(PREDICATE, (stack, world, entity) -> (float)(Math.log(stack.getOrCreateChildTag("lcc:computing").getInt("level") / (double)levelMin) / LOG2));
        this.levelMin = levelMin;
        this.levelMax = levelMax;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        CompoundNBT tag = stack.getChildTag("lcc:computing");
        if (tag != null) {
            tooltip.add(new TranslationTextComponent("item.lcc." + this.getRegistryName().getPath() + ".level").applyTextStyle(TextFormatting.GRAY).appendText(" ").appendSibling(new TranslationTextComponent("item.lcc." + this.getRegistryName().getPath() + ".level.value", tag.getInt("level")).applyTextStyle(TextFormatting.DARK_GRAY)));
        }
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack stack = new ItemStack(this);
            CompoundNBT tag = stack.getOrCreateChildTag("lcc:computing");
            tag.putInt("level", this.levelMin);
            items.add(stack.copy());
            if (this.levelMax != this.levelMin) {
                tag.putInt("level", this.levelMax);
                items.add(stack.copy());
            }
        }
    }

}
