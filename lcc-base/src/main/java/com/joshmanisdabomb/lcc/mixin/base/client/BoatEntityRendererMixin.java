package com.joshmanisdabomb.lcc.mixin.base.client;

import com.joshmanisdabomb.lcc.adaptation.boat.LCCBoat;
import com.joshmanisdabomb.lcc.adaptation.sign.LCCSign;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntityRenderer.class)
public abstract class BoatEntityRendererMixin {

    @ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 0)
    private Identifier textureOverride(Identifier original, BoatEntity boat) {
        if (boat instanceof LCCBoat) {
            return ((LCCBoat)boat).getLcc_boat().getTexture();
        }
        return original;
    }

}