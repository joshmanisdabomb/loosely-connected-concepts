package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.item.LCCExtendedItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ExtendedItemMixin implements LCCExtendedItem {

}