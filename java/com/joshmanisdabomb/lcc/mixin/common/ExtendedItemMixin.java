package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.item.LCCExtendedItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ExtendedItemMixin implements LCCExtendedItem {

}