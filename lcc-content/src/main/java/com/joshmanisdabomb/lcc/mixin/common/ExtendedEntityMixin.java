package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.entity.LCCExtendedEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class ExtendedEntityMixin implements LCCExtendedEntity {

}