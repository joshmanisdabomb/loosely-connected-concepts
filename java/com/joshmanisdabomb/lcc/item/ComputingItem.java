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
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ComputingItem extends Item {

    public static final ResourceLocation PREDICATE = new ResourceLocation(LCC.MODID, "computing_level");
    public static final double LOG2 = Math.log(2);

    public final int[] levels;

    public ComputingItem(int levelMin, int levelMax, Properties p) {
        this(IntStream.rangeClosed((int)getLogLevel(levelMin, levelMin), (int)getLogLevel(levelMax, levelMin)).map(i -> (int)Math.pow(2, i) * levelMin).toArray(), p);
    }

    public ComputingItem(int[] levels, Properties p) {
        super(p.maxStackSize(1));
        this.levels = levels;
        this.addPropertyOverride(PREDICATE, (stack, world, entity) -> (float)getLogLevel(stack.getOrCreateChildTag("lcc:computing").getInt(this.getLevelTag()), levels[0]));
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
            Arrays.stream(levels).mapToObj(i -> this.fillItemGroupStack(stack, tag, i).copy()).forEach(items::add);
        }
    }

    protected ItemStack fillItemGroupStack(ItemStack is, CompoundNBT tag, int level) {
        tag.putInt(this.getLevelTag(), level);
        return is;
    }

    protected static double getLogLevel(int level, double levelMin) {
        return Math.log(level / levelMin) / LOG2;
    }

    protected String getLevelTag() {
        return "level";
    }

    public int getLevelIndex(ItemStack is) {
        return this.getLevelIndex(is.getOrCreateChildTag("lcc:computing").getInt(this.getLevelTag()));
    }

    public int getLevelIndex(int level) {
        return Arrays.binarySearch(levels, level);
    }

}
