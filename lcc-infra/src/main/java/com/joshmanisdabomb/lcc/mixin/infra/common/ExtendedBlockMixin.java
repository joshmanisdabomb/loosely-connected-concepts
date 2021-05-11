package com.joshmanisdabomb.lcc.mixin.infra.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class ExtendedBlockMixin implements LCCExtendedBlock {

}