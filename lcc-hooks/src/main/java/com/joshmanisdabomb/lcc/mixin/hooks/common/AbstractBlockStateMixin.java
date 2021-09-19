package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCBlockTrait;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Shadow
    protected abstract BlockState asBlockState();

    @Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
    private void overrideInvisibleSide(BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> info) {
        Block block = stateFrom.getBlock();
        if (block instanceof LCCBlockTrait) {
            Boolean value = ((LCCBlockTrait)block).lcc_otherSideInvisible(stateFrom, asBlockState(), direction);
            if (value != null) info.setReturnValue(value);
        }
    }

}
