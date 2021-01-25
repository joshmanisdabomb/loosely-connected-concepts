package com.joshmanisdabomb.lcc.mixin.data.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.client.model.When;
import net.minecraft.state.property.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.Map;

@Mixin(When.PropertyCondition.class)
public class PropertyConditionMixin {

    @Shadow
    @Final
    private Map<Property<?>, String> properties;

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    public void getJson(CallbackInfoReturnable<JsonElement> info) {
        JsonObject jsonObject = new JsonObject();
        properties.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().getName())).forEach(entry -> {
            jsonObject.addProperty(entry.getKey().getName(), entry.getValue());
        });
        info.setReturnValue(jsonObject);
        info.cancel();
    }

}