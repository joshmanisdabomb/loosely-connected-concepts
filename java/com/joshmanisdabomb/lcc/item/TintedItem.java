package com.joshmanisdabomb.lcc.item;

import net.minecraft.item.ItemStack;

public interface TintedItem {

    int getItemTintColor(ItemStack stack, int tintIndex);

}
