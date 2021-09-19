package com.joshmanisdabomb.lcc.mixin.hooks.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemTraitMixin implements LCCItemTrait {

}