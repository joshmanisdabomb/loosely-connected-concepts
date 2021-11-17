package com.joshmanisdabomb.lcc.mixin.content.client;

import com.google.common.collect.ImmutableMap;
import com.joshmanisdabomb.lcc.directory.LCCModelLayers;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityModels.class)
public abstract class EntityModelsMixin {

    @ModifyVariable(method = "getModels", at = @At(value = "STORE"))
    private static ImmutableMap.Builder<EntityModelLayer, TexturedModelData> injectModelData(ImmutableMap.Builder<EntityModelLayer, TexturedModelData> original) {
        LCCModelLayers.INSTANCE.build(original);
        return original;
    }

}
