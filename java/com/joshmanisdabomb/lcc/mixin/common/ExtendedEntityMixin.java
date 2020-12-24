package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.entity.LCCExtendedEntity;
import com.joshmanisdabomb.lcc.item.LCCExtendedItem;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class ExtendedEntityMixin implements LCCExtendedEntity {

}