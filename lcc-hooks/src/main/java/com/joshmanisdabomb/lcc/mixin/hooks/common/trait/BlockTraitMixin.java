package com.joshmanisdabomb.lcc.mixin.hooks.common.trait;

import com.joshmanisdabomb.lcc.trait.LCCBlockTrait;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockTraitMixin implements LCCBlockTrait {

}