package com.joshmanisdabomb.lcc.mixin.hooks.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCFluidTrait;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public abstract class FluidTraitMixin implements LCCFluidTrait {

}