package com.joshmanisdabomb.lcc.mixin.content.client;

import com.google.common.collect.ImmutableMap;
import com.joshmanisdabomb.lcc.directory.LCCModelLayers;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(EntityModels.class)
public abstract class EntityModelsMixin {

    @Inject(method = "getModels", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void injectModelData(CallbackInfoReturnable<Map<EntityModelLayer, TexturedModelData>> info, ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder) {
        LCCModelLayers.INSTANCE.build(builder);
    }

}
