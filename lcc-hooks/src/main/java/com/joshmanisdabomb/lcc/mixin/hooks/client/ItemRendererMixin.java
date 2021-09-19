package com.joshmanisdabomb.lcc.mixin.hooks.client;

import com.joshmanisdabomb.lcc.trait.LCCItemTrait;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    private void renderGuiQuad(BufferBuilder buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {}

    @Inject(method = "Lnet/minecraft/client/render/item/ItemRenderer;renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getInstance()Lnet/minecraft/client/MinecraftClient;"))
    private void additionalItemBars(TextRenderer renderer, ItemStack stack, int x, int y, String countLabel, CallbackInfo info) {
        Item item = stack.getItem();
        if (item instanceof LCCItemTrait) {
            int[] indexes = ((LCCItemTrait)item).lcc_getAdditionalItemBarIndexes(stack);
            if (indexes.length <= 0) return;
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableBlend();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            for (int index : indexes) {
                int i = ((LCCItemTrait)item).lcc_getAdditionalItemBarStep(stack, index);
                int j = ((LCCItemTrait)item).lcc_getAdditionalItemBarColor(stack, index);
                int k = 13 - ((LCCItemTrait)item).lcc_getAdditionalItemBarOffset(stack, index);
                this.renderGuiQuad(bufferBuilder, x + 2, y + k, 13, 2, 0, 0, 0, 255);
                this.renderGuiQuad(bufferBuilder, x + 2, y + k, i, 1, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
            }
            RenderSystem.enableBlend();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
        }
    }

}
