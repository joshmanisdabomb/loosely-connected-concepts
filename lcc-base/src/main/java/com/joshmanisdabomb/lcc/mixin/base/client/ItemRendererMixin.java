package com.joshmanisdabomb.lcc.mixin.base.client;

import com.joshmanisdabomb.lcc.adaptation.LCCExtendedBlock;
import com.joshmanisdabomb.lcc.adaptation.LCCExtendedItem;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    private void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {}

    @Inject(method = "Lnet/minecraft/client/render/item/ItemRenderer;renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getInstance()Lnet/minecraft/client/MinecraftClient;"))
    private void additionalItemBars(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo info) {
        Item item = stack.getItem();
        if (item instanceof LCCExtendedItem) {
            int[] indexes = ((LCCExtendedItem)item).lcc_getAdditionalItemBarIndexes(stack);
            if (indexes.length <= 0) return;
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            for (int index : indexes) {
                int i = ((LCCExtendedItem)item).lcc_getAdditionalItemBarStep(stack, index);
                int j = ((LCCExtendedItem)item).lcc_getAdditionalItemBarColor(stack, index);
                int k = 13 - ((LCCExtendedItem)item).lcc_getAdditionalItemBarOffset(stack, index);
                this.renderGuiQuad(bufferBuilder, x + 2, y + k, 13, 2, 0, 0, 0, 255);
                this.renderGuiQuad(bufferBuilder, x + 2, y + k, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
            }
            RenderSystem.enableBlend();
            RenderSystem.enableAlphaTest();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
        }
    }

}