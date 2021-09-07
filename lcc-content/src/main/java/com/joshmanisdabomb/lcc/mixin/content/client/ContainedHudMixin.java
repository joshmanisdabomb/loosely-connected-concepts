package com.joshmanisdabomb.lcc.mixin.content.client;

import com.joshmanisdabomb.lcc.abstracts.oxygen.ContainedArmor;
import com.joshmanisdabomb.lcc.gui.overlay.ContainedOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class ContainedHudMixin {

    @Shadow
    private int ticks;

    @Inject(method = "renderStatusBars", locals = LocalCapture.CAPTURE_FAILSOFT, at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = "ldc=air"), cancellable = true)
    private void render(MatrixStack matrixStack, CallbackInfo info, PlayerEntity camera) {
        Iterable<ItemStack> pieces = camera.getArmorItems();
        ItemStack piece = pieces.iterator().next();
        Item helmet = piece.getItem();
        if (helmet instanceof ContainedArmor && ((ContainedArmor)helmet).hasFullSuit(piece, pieces)) {
            if (ContainedOverlay.INSTANCE.render(matrixStack, camera, pieces, ticks)) {
                MinecraftClient.getInstance().getProfiler().pop();
                info.cancel();
            }
        }
    }

}
