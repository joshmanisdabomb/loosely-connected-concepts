package com.joshmanisdabomb.lcc.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class StorageItem extends Item {

    private final int mbMin;
    private final int mbMax;
    private final int mbPerStack;
    private final int mbPerType;
    private final int typeLimit;

    public StorageItem(int mbMin, int mbMax, int mbPerStack, int mbPerType, int typeLimit, Properties p) {
        super(p.maxStackSize(1));
        this.mbMin = mbMin;
        this.mbMax = mbMax;
        this.mbPerStack = mbPerStack;
        this.mbPerType = mbPerType;
        this.typeLimit = typeLimit;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 0.5D;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return super.getRGBDurabilityForDisplay(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> toolTip, ITooltipFlag flag) {

    }

}