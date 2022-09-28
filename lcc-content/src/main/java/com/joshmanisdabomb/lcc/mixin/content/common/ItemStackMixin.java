package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.abstracts.ToolEffectivity;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "isSuitableFor", at = @At("RETURN"), cancellable = true)
    private void toolEffectivityCheck(BlockState state, CallbackInfoReturnable<Boolean> cinfo) {
        ItemStack is = (ItemStack)(Object)this;
        boolean req = false;
        for (ToolEffectivity t : ToolEffectivity.values()) {
            if (t.isRequired(state, is)) {
                req = true;
                if (!t.isTool(is, state, cinfo.getReturnValueZ())) {
                    cinfo.setReturnValue(false);
                    return;
                }
            }
        }
        if (req) cinfo.setReturnValue(true);
    }

}
