package com.joshmanisdabomb.lcc.mixin.content.client;

import com.google.common.collect.ImmutableMap;
import com.joshmanisdabomb.lcc.directory.LCCModelLayers;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(EntityModels.class)
public abstract class EntityModelsMixin {

    @ModifyVariable(method = "getModels", at = @At(value = "STORE"))
    private static ImmutableMap.Builder<EntityModelLayer, TexturedModelData> injectModelData(ImmutableMap.Builder<EntityModelLayer, TexturedModelData> original) {
        LCCModelLayers.INSTANCE.build(original);
        return original;
    }

}
