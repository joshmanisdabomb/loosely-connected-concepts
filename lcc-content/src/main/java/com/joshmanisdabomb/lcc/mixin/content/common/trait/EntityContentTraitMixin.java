package com.joshmanisdabomb.lcc.mixin.content.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCContentEntityTrait;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Entity.class)
abstract class EntityContentTraitMixin implements LCCContentEntityTrait {

}