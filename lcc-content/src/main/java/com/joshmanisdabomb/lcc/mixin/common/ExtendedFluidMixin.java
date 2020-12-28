package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.fluid.LCCExtendedFluid;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public abstract class ExtendedFluidMixin implements LCCExtendedFluid {

}