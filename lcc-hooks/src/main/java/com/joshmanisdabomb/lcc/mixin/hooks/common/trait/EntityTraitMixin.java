package com.joshmanisdabomb.lcc.mixin.hooks.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCEntityTrait;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
public abstract class EntityTraitMixin implements LCCEntityTrait {

}