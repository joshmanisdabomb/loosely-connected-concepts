package com.joshmanisdabomb.lcc.mixin.base.client;

import com.joshmanisdabomb.lcc.adaptation.sign.LCCSign;
import net.minecraft.block.Block;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin extends Screen {

    @Shadow @Final
    private SignBlockEntity sign;

    protected SignEditScreenMixin(Text title) {
        super(title);
    }

    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 0)
    private SpriteIdentifier textureOverride(SpriteIdentifier original) {
        Block block = sign.getCachedState().getBlock();
        if (block instanceof LCCSign) {
            return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((LCCSign)block).getLcc_sign().getTexture());
        }
        return original;
    }

}
