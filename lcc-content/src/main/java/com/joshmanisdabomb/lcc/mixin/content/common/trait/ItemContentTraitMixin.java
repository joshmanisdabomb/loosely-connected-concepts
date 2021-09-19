package com.joshmanisdabomb.lcc.mixin.content.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCContentItemTrait;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
abstract class ItemContentTraitMixin implements LCCContentItemTrait {

}