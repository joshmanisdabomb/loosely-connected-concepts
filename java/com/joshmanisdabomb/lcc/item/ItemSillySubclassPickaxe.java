package com.joshmanisdabomb.lcc.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class ItemSillySubclassPickaxe extends ItemPickaxe {

    public ItemSillySubclassPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

}
