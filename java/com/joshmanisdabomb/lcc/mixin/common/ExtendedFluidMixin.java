package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.fluid.LCCExtendedFluid;
import com.joshmanisdabomb.lcc.item.LCCExtendedItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public abstract class ExtendedFluidMixin implements LCCExtendedFluid {

}