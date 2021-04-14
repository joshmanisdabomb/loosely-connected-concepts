package com.joshmanisdabomb.lcc.mixin.base.client;

import com.joshmanisdabomb.lcc.adaptation.sign.LCCSign;
import net.minecraft.block.Block;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SignBlockEntityRenderer.class)
public abstract class SignBlockEntityRendererMixin {

    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 0)
    private SpriteIdentifier textureOverride(SpriteIdentifier original, SignBlockEntity sign) {
        Block block = sign.getCachedState().getBlock();
        if (block instanceof LCCSign) {
            return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((LCCSign)block).getLcc_sign().getTexture());
        }
        return original;
    }

}