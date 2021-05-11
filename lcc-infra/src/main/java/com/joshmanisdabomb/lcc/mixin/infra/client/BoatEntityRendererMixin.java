package com.joshmanisdabomb.lcc.mixin.infra.client;

import com.joshmanisdabomb.lcc.adaptation.boat.LCCBoat;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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