package com.joshmanisdabomb.lcc.mixin.content.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCContentBlockTrait;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
abstract class BlockContentTraitMixin implements LCCContentBlockTrait {

}