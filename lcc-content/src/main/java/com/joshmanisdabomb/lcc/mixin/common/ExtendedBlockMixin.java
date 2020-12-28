package com.joshmanisdabomb.lcc.mixin.common;

import com.joshmanisdabomb.lcc.block.LCCExtendedBlock;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class ExtendedBlockMixin implements LCCExtendedBlock {

}