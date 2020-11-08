package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.block.LCCExtendedBlock;
import com.joshmanisdabomb.lcc.fluid.LCCExtendedFluid;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class ExtendedBlockMixin implements LCCExtendedBlock {

}