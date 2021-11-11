package com.joshmanisdabomb.lcc.mixin.data.common;

import net.minecraft.GameVersion;
import net.minecraft.SharedConstants;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedConstants.class)
public abstract class SharedConstantsMixin {

    @Shadow
    private static GameVersion gameVersion;

    @Shadow
    private static void createGameVersion() {
        throw new AssertionError();
    }

    @Inject(method = "getGameVersion", at = @At("HEAD"))
    private static void getOrDefaultVersion(CallbackInfoReturnable<GameVersion> info) {
        if (gameVersion == null) createGameVersion();
    }

}
