package com.joshmanisdabomb.lcc.mixin.hooks.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCEffectTrait;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StatusEffect.class)
public abstract class EffectTraitMixin implements LCCEffectTrait {

}