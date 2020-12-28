package com.joshmanisdabomb.lcc.mixin.content.common;

import com.joshmanisdabomb.lcc.directory.LCCBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.StonecutterScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StonecutterScreenHandler.class)
public abstract class StonecutterHandlerMixin extends ScreenHandler {

    @Shadow
    @Final
    private ScreenHandlerContext context;

    public StonecutterHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    public void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
        if (ScreenHandler.canUse(this.context, player, LCCBlocks.INSTANCE.getPocket_stonecutter())) {
            info.setReturnValue(true);
            info.cancel();
        }
    }

}
