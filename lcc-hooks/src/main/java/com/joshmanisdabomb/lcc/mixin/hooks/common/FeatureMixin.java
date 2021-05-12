package com.joshmanisdabomb.lcc.mixin.hooks.common;

import com.joshmanisdabomb.lcc.trait.LCCBlockTrait;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public abstract class FeatureMixin {

    @Inject(method = "isSoil", at = @At("HEAD"), cancellable = true)
    private static void soilCheck(BlockState state, CallbackInfoReturnable<Boolean> info) {
        Block block = state.getBlock();
        if (block instanceof LCCBlockTrait) {
            Boolean flag = ((LCCBlockTrait)block).lcc_isSoil(state);
            if (flag == null) return;
            info.setReturnValue(flag);
            info.cancel();
        }
    }

}
