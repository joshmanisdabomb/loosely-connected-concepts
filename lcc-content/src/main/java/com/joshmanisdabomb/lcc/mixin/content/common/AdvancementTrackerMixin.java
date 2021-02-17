package com.joshmanisdabomb.lcc.mixin.content.common;

import com.google.common.io.Files;
import com.google.gson.stream.JsonReader;
import com.joshmanisdabomb.lcc.advancement.RaceCriterion;
import com.joshmanisdabomb.lcc.item.BagItem;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Mixin(PlayerAdvancementTracker.class)
public abstract class AdvancementTrackerMixin {

    @Shadow
    private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/Advancement;getRewards()Lnet/minecraft/advancement/AdvancementRewards;"))
    private void onGrant(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> info) {
        RaceCriterion.Companion.onGrant(owner, advancement);
    }

}