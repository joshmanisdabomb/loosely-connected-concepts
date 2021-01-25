package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlockContent;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
abstract class ExtendedBlockMixin implements LCCExtendedBlockContent {

}