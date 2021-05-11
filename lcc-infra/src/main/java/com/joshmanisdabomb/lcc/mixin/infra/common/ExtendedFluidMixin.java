package com.joshmanisdabomb.lcc.mixin.infra.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedFluid;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public abstract class ExtendedFluidMixin implements LCCExtendedFluid {

}